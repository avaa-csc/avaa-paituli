/**
 * 
 */
package fi.csc.avaa.paituli;



/**
 * @author jmlehtin
 *
 */
public class ApiBean {

	private String dataId;
	private String layerName;
	private String layerTitle;
	private String scaleLimit;
//	private PaituliDataAccess access;
	
	protected String getDataId() {
		return dataId;
	}
	protected void setDataId(String dataId) {
		this.dataId = dataId;
	}
//	protected PaituliDataAccess getAccess() {
//		return access;
//	}
//	protected void setAccess(PaituliDataAccess access) {
//		this.access = access;
//	}
	public String getLayerName() {
		return layerName;
	}
	public void setLayerName(String layerName) {
		this.layerName = layerName;
	}
	public String getLayerTitle() {
		return layerTitle;
	}
	public void setLayerTitle(String layerTitle) {
		this.layerTitle = layerTitle;
	}
	public String getScaleLimit() {
		return scaleLimit;
	}
	public void setScaleLimit(String scaleLimit) {
		this.scaleLimit = scaleLimit;
	}
	
}
