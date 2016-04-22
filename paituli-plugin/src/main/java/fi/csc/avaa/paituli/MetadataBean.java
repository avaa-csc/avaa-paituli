/**
 * 
 */
package fi.csc.avaa.paituli;


/**
 * @author jmlehtin
 *
 */
public class MetadataBean {

	private String dataId;
	private String producer;
	private String data;
	private String scale;
	private String year;
	private String format;
	private String coordsys;
	private String metadata;
	private String licence;
	private PaituliDataAccess access;
	
	protected String getProducer() {
		return producer;
	}
	protected void setProducer(String producer) {
		this.producer = producer;
	}
	protected String getData() {
		return data;
	}
	protected void setData(String data) {
		this.data = data;
	}
	protected String getScale() {
		return scale;
	}
	protected void setScale(String scale) {
		this.scale = scale;
	}
	protected String getYear() {
		return year;
	}
	protected void setYear(String year) {
		this.year = year;
	}
	protected String getFormat() {
		return format;
	}
	protected void setFormat(String format) {
		this.format = format;
	}
	protected String getCoordsys() {
		return coordsys;
	}
	protected void setCoordsys(String coordsys) {
		this.coordsys = coordsys;
	}
	protected String getDataId() {
		return dataId;
	}
	protected void setDataId(String dataId) {
		this.dataId = dataId;
	}
	protected String getLicence() {
		return licence;
	}
	protected void setLicence(String licence) {
		this.licence = licence;
	}
	protected String getMetadata() {
		return metadata;
	}
	protected void setMetadata(String metadata) {
		this.metadata = metadata;
	}
	protected PaituliDataAccess getAccess() {
		return access;
	}
	protected void setAccess(PaituliDataAccess access) {
		this.access = access;
	}

	public enum PaituliDataAccess {
		HAKA(2),
		OPEN(1);
		
		private int value;
		
		private PaituliDataAccess(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
		
		public static PaituliDataAccess fromInteger(int value) {
			for(PaituliDataAccess access : PaituliDataAccess.values()) {
				int accessVal = access.getValue();
				if(accessVal == value) {
					return access;
				}
			}
			return null;
		}
	}
}
