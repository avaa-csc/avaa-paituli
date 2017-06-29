package fi.csc.avaa.paituli;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.tepi.filtertable.FilterTable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vaadin.data.Item;
import com.vaadin.data.util.DefaultItemSorter;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import fi.csc.avaa.paituli.MetadataBean.PaituliDataAccess;
import fi.csc.avaa.tools.logging.AvaaLogger;
import fi.csc.avaa.tools.vaadin.language.Translator;


/**
 * Class for creating Kotus Murrehaku search result table as a custom Vaadin component.
 * 
 * @author jmlehtin
 *
 */
public class MetadataTable extends CustomComponent {

	private AvaaLogger log = new AvaaLogger(MetadataTable.class.getName());
	private static final long serialVersionUID = 1L;
	// Table with the ability to filter data
	private FilterTable table;
	// Base layout for result table tab
	private VerticalLayout tableLayout;
	private IndexedContainer qResultContainer;
	private static final String ETSIN_METADATA_BASE_URL_FI = "http://etsin.avointiede.fi/fi/dataset/";
	private static final String ETSIN_METADATA_BASE_URL_EN = "http://etsin.avointiede.fi/en/dataset/";
	private static final String PAITULI_BASE_URL = "/web/paituli/latauspalvelu";
	private Translator translator;
	
	public MetadataTable(String caption, Translator translator) {
		tableLayout = new VerticalLayout();
		tableLayout.setSpacing(true);
		tableLayout.setMargin(true);
		tableLayout.addStyleName("metadata-table-container");
		tableLayout.setSizeFull();
		qResultContainer = new IndexedContainer();
		this.translator = translator;
		
		if(caption != null) {
			table = new FilterTable();
		} else {
			table = new FilterTable(caption);
		}
		
		table.addValueChangeListener(event -> {
			String dataId = event.getProperty().getValue().toString();
			String itemAccess = qResultContainer.getItem(dataId).getItemProperty(translator.localize("Table.Column.Property.Access")).toString();
			Page page = getUI().getPage();
			String host = page.getLocation().getHost();
			String redirectUrlWithoutProtocol = "localhost".equals(host) ? "localhost:8080" : host + PAITULI_BASE_URL + "?data_id=" + dataId;
			if(translator.localize("Table.Column.Value.Access.Haka").equals(itemAccess)) {
				page.setLocation("https://" + redirectUrlWithoutProtocol);
			} else if(translator.localize("Table.Column.Value.Access.Open").equals(itemAccess)) {
				page.setLocation("http://" + redirectUrlWithoutProtocol);
			}
		});

		// Set columns for the table
		setTableColumns();
		// Set table datasource
		table.setContainerDataSource(qResultContainer);
		
		// Set decorator to control e.g. timeout for filtering
		table.setFilterDecorator(new ResultTableDecorator(this.translator));
		// Table properties
		table.setMultiSelect(false);
		table.setSelectable(true);
		table.setImmediate(true);
		table.setSizeFull();
		table.setFilterBarVisible(true);
		table.setSortEnabled(true);
		table.setNullSelectionAllowed(false);
		updateResultContainer(getMetadata());
		
		final class SortIgnoreCase implements Comparator<Object> {
			public int compare(Object o1, Object o2) {
				String s1 = (String) o1;
			    String s2 = (String) o2;
			    return s1.toLowerCase().compareTo(s2.toLowerCase());
			}
		}
		Object [] properties={translator.localize("Table.Column.Property.Producer"), translator.localize("Table.Column.Property.Data"), translator.localize("Table.Column.Property.Scale"), translator.localize("Table.Column.Property.Year"), translator.localize("Table.Column.Property.Format"), translator.localize("Table.Column.Property.Coordsys")};
		boolean [] ordering={true, true, true, true, true, true};
		qResultContainer.setItemSorter(new DefaultItemSorter(new SortIgnoreCase()));
		qResultContainer.sort(properties, ordering);
			
		tableLayout.addComponent(table);
		setCompositionRoot(this.tableLayout);
	}
	
	private void setTableColumns() {
		// Add properties (table columns) for the datasource container
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.Producer"), String.class, null);
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.Data"), String.class, null);
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.Scale"), String.class, null);
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.Year"), String.class, null);
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.Format"), String.class, null);
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.Coordsys"), String.class, null);
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.Metadata"), Link.class, null);
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.Licence"), Link.class, null);
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.Access"), String.class, null);
		// Initially sort results based on the following column
		table.setSortContainerPropertyId(Const.TABLE_HEADER_PRODUCER);
	}

	private List<MetadataBean> getMetadata() {
		final String METADATA_API_URL = "https://"+UI.getCurrent().getPage().getLocation().getHost()+":443/paituli-portlet/paituliAPI.jsp";
		List<MetadataBean> beans = new ArrayList<>();
		HttpURLConnection conn = null;
		InputStream istream = null;
		InputStreamReader reader = null;
		try {
			URL obj = new URL(METADATA_API_URL + "?lang=" + Translator.getLocaleStr(translator.getDefaultLocale()));
			conn = (HttpURLConnection) obj.openConnection();
			conn.connect();
			JsonParser parser = new JsonParser();
			istream = conn.getInputStream();
			reader = new InputStreamReader(istream);
			JsonArray response = (JsonArray) parser.parse(reader);
			Iterator<JsonElement> it = response.iterator();
			while(it.hasNext()) {
				JsonObject jObj = (JsonObject) it.next();
				MetadataBean bean = new MetadataBean();
				bean.setDataId(jObj.get("data_id").getAsString());
				bean.setProducer(jObj.get("org").getAsString());
				bean.setData(jObj.get("name").getAsString());
				bean.setScale(jObj.get("scale").getAsString());
				bean.setYear(jObj.get("year").getAsString());
				bean.setFormat(jObj.get("format").getAsString());
				bean.setCoordsys(jObj.get("coord_sys").getAsString());
				bean.setMetadata(jObj.get("meta").getAsString());
				bean.setLicence(jObj.get("license_url").getAsString());
				bean.setAccess(PaituliDataAccess.fromInteger(jObj.get("access").getAsInt()));
				beans.add(bean);
			}
		} catch(Exception e) {
			e.printStackTrace();
			log.error("Problem getting metadata from API");
		} finally {
			try {
				if(conn != null) {
					conn.disconnect();
				}
				if(istream != null) {
					istream.close();
				}
				if(reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				log.error("Error closing inputstreams when getting metadata from API");
				e.printStackTrace();
			}
		}
		return beans;
	}
	
	/**
	 * Update table datasource container with new data.
	 * 
	 * 
	 * @param searchResults
	 */
	@SuppressWarnings("unchecked")
	private void updateResultContainer(List<MetadataBean> metadata) {
		int itemAmt = metadata.size();
		// Empty container first
		qResultContainer.removeAllItems();
		if(itemAmt > 0) {
			this.table.setVisible(true);
			this.table.setPageLength(itemAmt);
			this.table.select(null);
			// Then populate the datasource for each MetadataBean, field by field
			for(int i=0; i < metadata.size(); i++) {
				MetadataBean bean = metadata.get(i);
				String dataId = bean.getDataId();
				String producer = bean.getProducer();
				String data = bean.getData();
				String scale = bean.getScale();
				String year = bean.getYear();
				String format = bean.getFormat();
				String coordsys = bean.getCoordsys();
				String etsinMetadata = bean.getMetadata();
				String licence = bean.getLicence();
				PaituliDataAccess access = bean.getAccess();
				
				// Item corresponds to a row in the table. Item ID will be ilmioId, which is used when a row is clicked to know which Ilmio was clicked
				Item dataRow = qResultContainer.addItem(dataId);
				dataRow.getItemProperty(translator.localize("Table.Column.Property.Producer")).setValue(producer);
				dataRow.getItemProperty(translator.localize("Table.Column.Property.Data")).setValue(data);
				dataRow.getItemProperty(translator.localize("Table.Column.Property.Scale")).setValue(scale);
				dataRow.getItemProperty(translator.localize("Table.Column.Property.Year")).setValue(year);
				dataRow.getItemProperty(translator.localize("Table.Column.Property.Format")).setValue(format);
				dataRow.getItemProperty(translator.localize("Table.Column.Property.Coordsys")).setValue(coordsys);
				String metadataBaseUrl = null;
				if("fi_FI".equals(translator.getDefaultLocaleStr())) {
					metadataBaseUrl = ETSIN_METADATA_BASE_URL_FI;
				} else if("en_US".equals(translator.getDefaultLocaleStr())) {
					metadataBaseUrl = ETSIN_METADATA_BASE_URL_EN;
				}
				ExternalResource etsinMetadataRes = new ExternalResource(metadataBaseUrl + etsinMetadata);
				Link etsinMetadataLink = new Link(translator.localize("Table.Column.Property.Metadata"), etsinMetadataRes);
				etsinMetadataLink.setTargetName("_blank");
				dataRow.getItemProperty(translator.localize("Table.Column.Property.Metadata")).setValue(etsinMetadataLink);
				ExternalResource licenceRes = new ExternalResource(licence);
				Link licenceLink = new Link(translator.localize("Table.Column.Property.Licence"), licenceRes);
				licenceLink.setTargetName("_blank");
				dataRow.getItemProperty(translator.localize("Table.Column.Property.Licence")).setValue(licenceLink);
				String accessVal = null;
				switch (access) {
				case HAKA:
					accessVal = translator.localize("Table.Column.Value.Access.Haka");
					break;
				case OPEN:
					accessVal = translator.localize("Table.Column.Value.Access.Open");
					break;
				default:
					break;
				}
				if(accessVal != null) {
					dataRow.getItemProperty(translator.localize("Table.Column.Property.Access")).setValue(accessVal);
				}
			}
		} else {
			this.table.setVisible(false);
		}
	}
	
	public static JsonArray modifyMetadataLanguage(JsonArray json, String language) {
		JsonArray jsonArray = new JsonArray();
		jsonArray.addAll(json);
		Iterator<JsonElement> it = jsonArray.iterator();
		while(it.hasNext()) {
			JsonObject jObj = (JsonObject) it.next();
			String name = null;
			String org = null;
			String format = null;
			if("fi_FI".equals(language)) {
				name = jObj.get("name_fin").getAsString(); 
				org = jObj.get("org_fin").getAsString();
				format = jObj.get("format_fin").getAsString();
			} else if("en_US".equals(language)) {
				name = jObj.get("name_eng").getAsString(); 
				org = jObj.get("org_eng").getAsString();
				format = jObj.get("format_eng").getAsString();
			}
//			jObj.remove("name_fin");
			jObj.remove("org_fin");
			jObj.remove("format_fin");
			jObj.remove("name_eng");
			jObj.remove("org_eng");
			jObj.remove("format_eng");
			jObj.addProperty("name", name);
			jObj.addProperty("org", org);
			jObj.addProperty("format", format);
		}
		return jsonArray;
	}
}
