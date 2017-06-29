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
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import fi.csc.avaa.tools.Tools;
import fi.csc.avaa.tools.logging.AvaaLogger;
import fi.csc.avaa.tools.vaadin.language.Translator;


/**
 * Class for creating Kotus Murrehaku search result table as a custom Vaadin component.
 * 
 * @author jmlehtin
 *
 */
public class ApiTable extends CustomComponent {

	private AvaaLogger log = new AvaaLogger(ApiTable.class.getName());
	private static final long serialVersionUID = 1L;
	// Table with the ability to filter data
	private FilterTable table;
	// Base layout for result table tab
	private VerticalLayout tableLayout;
	private IndexedContainer qResultContainer;
	private Translator translator;
	
	public ApiTable(String caption, Translator translator) {
		tableLayout = new VerticalLayout();
		tableLayout.setSpacing(true);
		tableLayout.setMargin(true);
		tableLayout.addStyleName("api-table-container");
		tableLayout.setSizeFull();
		qResultContainer = new IndexedContainer();
		this.translator = translator;
		
		if(caption != null) {
			table = new FilterTable();
		} else {
			table = new FilterTable(caption);
		}
		
		// Set columns for the table
		setTableColumns();
		// Set table datasource
		table.setContainerDataSource(qResultContainer);
		
		// Set decorator to control e.g. timeout for filtering
		table.setFilterDecorator(new ResultTableDecorator(this.translator));
		// Table properties
		table.setMultiSelect(false);
		table.setSelectable(false);
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
		Object [] properties={translator.localize("Table.Column.Property.LayerName"), translator.localize("Table.Column.Property.LayerTitle"), translator.localize("Table.Column.Property.ScaleLimit")};
		boolean [] ordering={true, true, true};
		qResultContainer.setItemSorter(new DefaultItemSorter(new SortIgnoreCase()));
		qResultContainer.sort(properties, ordering);
			
		tableLayout.addComponent(table);
		setCompositionRoot(this.tableLayout);
	}
	
	private void setTableColumns() {
		// Add properties (table columns) for the datasource container
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.LayerName"), String.class, null);
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.LayerTitle"), String.class, null);
		qResultContainer.addContainerProperty(translator.localize("Table.Column.Property.ScaleLimit"), String.class, null);
	}

	private List<ApiBean> getMetadata() {
		final String METADATA_API_URL = "https://"+UI.getCurrent().getPage().getLocation().getHost()+":443/paituli-portlet/paituliAPI.jsp";
		List<ApiBean> beans = new ArrayList<>();
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
				ApiBean bean = new ApiBean();
				String dataId = Tools.isNull(jObj.get("data_id")) ? null : jObj.get("data_id").getAsString();
				boolean dataUrlIsNull = Tools.isNull(jObj.get("data_url"));
				if(!Tools.isNull(dataId) && !dataUrlIsNull) {
					bean.setDataId(dataId);
					bean.setLayerName(jObj.get("data_url").getAsString());
					bean.setLayerTitle(Tools.isNull(jObj.get("org_abbreviation")) ? Const.EMPTY_STRING : jObj.get("org_abbreviation").getAsString() + ", " + jObj.get("name_fin").getAsString() + ", " + jObj.get("scale").getAsString() + ", " + jObj.get("year").getAsString());
					bean.setScaleLimit(Tools.isNull(jObj.get("data_max_scale")) ? Const.EMPTY_STRING : jObj.get("data_max_scale").getAsString());
					beans.add(bean);
				}
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
	private void updateResultContainer(List<ApiBean> metadata) {
		int itemAmt = metadata.size();
		qResultContainer.removeAllItems();
		if(itemAmt > 0) {
			this.table.setVisible(true);
			this.table.setPageLength(itemAmt);
			this.table.select(null);
			for(int i=0; i < metadata.size(); i++) {
				ApiBean bean = metadata.get(i);
				String dataId = bean.getDataId();
				String layerName = bean.getLayerName();
				String layerTitle = bean.getLayerTitle();
				String scaleLimit = bean.getScaleLimit();
				
				Item dataRow = qResultContainer.addItem(dataId);
				dataRow.getItemProperty(translator.localize("Table.Column.Property.LayerName")).setValue(layerName);
				dataRow.getItemProperty(translator.localize("Table.Column.Property.LayerTitle")).setValue(layerTitle);
				dataRow.getItemProperty(translator.localize("Table.Column.Property.ScaleLimit")).setValue(scaleLimit);
			}
		} else {
			this.table.setVisible(false);
		}
	}
}
