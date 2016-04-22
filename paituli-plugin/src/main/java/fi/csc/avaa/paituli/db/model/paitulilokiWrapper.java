package fi.csc.avaa.paituli.db.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link paituliloki}.
 * </p>
 *
 * @author pj
 * @see paituliloki
 * @generated
 */
public class paitulilokiWrapper implements paituliloki,
    ModelWrapper<paituliloki> {
    private paituliloki _paituliloki;

    public paitulilokiWrapper(paituliloki paituliloki) {
        _paituliloki = paituliloki;
    }

    @Override
    public Class<?> getModelClass() {
        return paituliloki.class;
    }

    @Override
    public String getModelClassName() {
        return paituliloki.class.getName();
    }

    @Override
    public Map<String, Object> getModelAttributes() {
        Map<String, Object> attributes = new HashMap<String, Object>();

        attributes.put("event_id", getEvent_id());
        attributes.put("saltedhash", getSaltedhash());
        attributes.put("organisaatio", getOrganisaatio());
        attributes.put("aineisto", getAineisto());
        attributes.put("tiedotojenlkm", getTiedotojenlkm());
        attributes.put("paiva", getPaiva());

        return attributes;
    }

    @Override
    public void setModelAttributes(Map<String, Object> attributes) {
        Integer event_id = (Integer) attributes.get("event_id");

        if (event_id != null) {
            setEvent_id(event_id);
        }

        String saltedhash = (String) attributes.get("saltedhash");

        if (saltedhash != null) {
            setSaltedhash(saltedhash);
        }

        String organisaatio = (String) attributes.get("organisaatio");

        if (organisaatio != null) {
            setOrganisaatio(organisaatio);
        }

        String aineisto = (String) attributes.get("aineisto");

        if (aineisto != null) {
            setAineisto(aineisto);
        }

        Integer tiedotojenlkm = (Integer) attributes.get("tiedotojenlkm");

        if (tiedotojenlkm != null) {
            setTiedotojenlkm(tiedotojenlkm);
        }

        Date paiva = (Date) attributes.get("paiva");

        if (paiva != null) {
            setPaiva(paiva);
        }
    }

    /**
    * Returns the primary key of this paituliloki.
    *
    * @return the primary key of this paituliloki
    */
    @Override
    public int getPrimaryKey() {
        return _paituliloki.getPrimaryKey();
    }

    /**
    * Sets the primary key of this paituliloki.
    *
    * @param primaryKey the primary key of this paituliloki
    */
    @Override
    public void setPrimaryKey(int primaryKey) {
        _paituliloki.setPrimaryKey(primaryKey);
    }

    /**
    * Returns the event_id of this paituliloki.
    *
    * @return the event_id of this paituliloki
    */
    @Override
    public int getEvent_id() {
        return _paituliloki.getEvent_id();
    }

    /**
    * Sets the event_id of this paituliloki.
    *
    * @param event_id the event_id of this paituliloki
    */
    @Override
    public void setEvent_id(int event_id) {
        _paituliloki.setEvent_id(event_id);
    }

    /**
    * Returns the saltedhash of this paituliloki.
    *
    * @return the saltedhash of this paituliloki
    */
    @Override
    public java.lang.String getSaltedhash() {
        return _paituliloki.getSaltedhash();
    }

    /**
    * Sets the saltedhash of this paituliloki.
    *
    * @param saltedhash the saltedhash of this paituliloki
    */
    @Override
    public void setSaltedhash(java.lang.String saltedhash) {
        _paituliloki.setSaltedhash(saltedhash);
    }

    /**
    * Returns the organisaatio of this paituliloki.
    *
    * @return the organisaatio of this paituliloki
    */
    @Override
    public java.lang.String getOrganisaatio() {
        return _paituliloki.getOrganisaatio();
    }

    /**
    * Sets the organisaatio of this paituliloki.
    *
    * @param organisaatio the organisaatio of this paituliloki
    */
    @Override
    public void setOrganisaatio(java.lang.String organisaatio) {
        _paituliloki.setOrganisaatio(organisaatio);
    }

    /**
    * Returns the aineisto of this paituliloki.
    *
    * @return the aineisto of this paituliloki
    */
    @Override
    public java.lang.String getAineisto() {
        return _paituliloki.getAineisto();
    }

    /**
    * Sets the aineisto of this paituliloki.
    *
    * @param aineisto the aineisto of this paituliloki
    */
    @Override
    public void setAineisto(java.lang.String aineisto) {
        _paituliloki.setAineisto(aineisto);
    }

    /**
    * Returns the tiedotojenlkm of this paituliloki.
    *
    * @return the tiedotojenlkm of this paituliloki
    */
    @Override
    public int getTiedotojenlkm() {
        return _paituliloki.getTiedotojenlkm();
    }

    /**
    * Sets the tiedotojenlkm of this paituliloki.
    *
    * @param tiedotojenlkm the tiedotojenlkm of this paituliloki
    */
    @Override
    public void setTiedotojenlkm(int tiedotojenlkm) {
        _paituliloki.setTiedotojenlkm(tiedotojenlkm);
    }

    /**
    * Returns the paiva of this paituliloki.
    *
    * @return the paiva of this paituliloki
    */
    @Override
    public java.util.Date getPaiva() {
        return _paituliloki.getPaiva();
    }

    /**
    * Sets the paiva of this paituliloki.
    *
    * @param paiva the paiva of this paituliloki
    */
    @Override
    public void setPaiva(java.util.Date paiva) {
        _paituliloki.setPaiva(paiva);
    }

    @Override
    public boolean isNew() {
        return _paituliloki.isNew();
    }

    @Override
    public void setNew(boolean n) {
        _paituliloki.setNew(n);
    }

    @Override
    public boolean isCachedModel() {
        return _paituliloki.isCachedModel();
    }

    @Override
    public void setCachedModel(boolean cachedModel) {
        _paituliloki.setCachedModel(cachedModel);
    }

    @Override
    public boolean isEscapedModel() {
        return _paituliloki.isEscapedModel();
    }

    @Override
    public java.io.Serializable getPrimaryKeyObj() {
        return _paituliloki.getPrimaryKeyObj();
    }

    @Override
    public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
        _paituliloki.setPrimaryKeyObj(primaryKeyObj);
    }

    @Override
    public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
        return _paituliloki.getExpandoBridge();
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portal.model.BaseModel<?> baseModel) {
        _paituliloki.setExpandoBridgeAttributes(baseModel);
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
        _paituliloki.setExpandoBridgeAttributes(expandoBridge);
    }

    @Override
    public void setExpandoBridgeAttributes(
        com.liferay.portal.service.ServiceContext serviceContext) {
        _paituliloki.setExpandoBridgeAttributes(serviceContext);
    }

    @Override
    public java.lang.Object clone() {
        return new paitulilokiWrapper((paituliloki) _paituliloki.clone());
    }

    @Override
    public int compareTo(fi.csc.avaa.paituli.db.model.paituliloki paituliloki) {
        return _paituliloki.compareTo(paituliloki);
    }

    @Override
    public int hashCode() {
        return _paituliloki.hashCode();
    }

    @Override
    public com.liferay.portal.model.CacheModel<fi.csc.avaa.paituli.db.model.paituliloki> toCacheModel() {
        return _paituliloki.toCacheModel();
    }

    @Override
    public fi.csc.avaa.paituli.db.model.paituliloki toEscapedModel() {
        return new paitulilokiWrapper(_paituliloki.toEscapedModel());
    }

    @Override
    public fi.csc.avaa.paituli.db.model.paituliloki toUnescapedModel() {
        return new paitulilokiWrapper(_paituliloki.toUnescapedModel());
    }

    @Override
    public java.lang.String toString() {
        return _paituliloki.toString();
    }

    @Override
    public java.lang.String toXmlString() {
        return _paituliloki.toXmlString();
    }

    @Override
    public void persist()
        throws com.liferay.portal.kernel.exception.SystemException {
        _paituliloki.persist();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof paitulilokiWrapper)) {
            return false;
        }

        paitulilokiWrapper paitulilokiWrapper = (paitulilokiWrapper) obj;

        if (Validator.equals(_paituliloki, paitulilokiWrapper._paituliloki)) {
            return true;
        }

        return false;
    }

    /**
     * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
     */
    public paituliloki getWrappedpaituliloki() {
        return _paituliloki;
    }

    @Override
    public paituliloki getWrappedModel() {
        return _paituliloki;
    }

    @Override
    public void resetOriginalValues() {
        _paituliloki.resetOriginalValues();
    }
}
