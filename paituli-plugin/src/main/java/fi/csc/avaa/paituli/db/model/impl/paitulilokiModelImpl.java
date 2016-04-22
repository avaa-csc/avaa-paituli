package fi.csc.avaa.paituli.db.model.impl;

import java.io.Serializable;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import fi.csc.avaa.paituli.db.model.paituliloki;
import fi.csc.avaa.paituli.db.model.paitulilokiModel;

/**
 * The base model implementation for the paituliloki service. Represents a row in the &quot;loki&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link fi.csc.avaa.paituli.db.model.paitulilokiModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link paitulilokiImpl}.
 * </p>
 *
 * @author pj
 * @see paitulilokiImpl
 * @see fi.csc.avaa.paituli.db.model.paituliloki
 * @see fi.csc.avaa.paituli.db.model.paitulilokiModel
 * @generated
 */
public class paitulilokiModelImpl extends BaseModelImpl<paituliloki>
    implements paitulilokiModel {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this class directly. All methods that expect a paituliloki model instance should use the {@link fi.csc.avaa.paituli.db.model.paituliloki} interface instead.
     */
    public static final String TABLE_NAME = "loki";
    public static final Object[][] TABLE_COLUMNS = {
            { "event_id", Types.INTEGER },
            { "saltedhash", Types.VARCHAR },
            { "organisaatio", Types.VARCHAR },
            { "aineisto", Types.VARCHAR },
            { "tiedotojenlkm", Types.INTEGER },
            { "paiva", Types.TIMESTAMP }
        };
    public static final String TABLE_SQL_CREATE = "create table loki (event_id INTEGER not null primary key,saltedhash VARCHAR(75) null,organisaatio VARCHAR(75) null,aineisto VARCHAR(75) null,tiedotojenlkm INTEGER,paiva DATE null)";
    public static final String TABLE_SQL_DROP = "drop table loki";
    public static final String ORDER_BY_JPQL = " ORDER BY paituliloki.event_id ASC";
    public static final String ORDER_BY_SQL = " ORDER BY loki.event_id ASC";
    public static final String DATA_SOURCE = "paituli";
    public static final String SESSION_FACTORY = "paituliSessionFactory";
    public static final String TX_MANAGER = "liferayTransactionManager";
    public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.entity.cache.enabled.fi.csc.avaa.paituli.db.model.paituliloki"),
            true);
    public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.finder.cache.enabled.fi.csc.avaa.paituli.db.model.paituliloki"),
            true);
    public static final boolean COLUMN_BITMASK_ENABLED = false;
    public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
                "lock.expiration.time.fi.csc.avaa.paituli.db.model.paituliloki"));
    private static ClassLoader _classLoader = paituliloki.class.getClassLoader();
    private static Class<?>[] _escapedModelInterfaces = new Class[] {
            paituliloki.class
        };
    private int _event_id;
    private String _saltedhash;
    private String _organisaatio;
    private String _aineisto;
    private int _tiedotojenlkm;
    private Date _paiva;
    private paituliloki _escapedModel;

    public paitulilokiModelImpl() {
    }

    @Override
    public int getPrimaryKey() {
        return _event_id;
    }

    @Override
    public void setPrimaryKey(int primaryKey) {
        setEvent_id(primaryKey);
    }

    @Override
    public Serializable getPrimaryKeyObj() {
        return _event_id;
    }

    @Override
    public void setPrimaryKeyObj(Serializable primaryKeyObj) {
        setPrimaryKey(((Integer) primaryKeyObj).intValue());
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

    @Override
    public int getEvent_id() {
        return _event_id;
    }

    @Override
    public void setEvent_id(int event_id) {
        _event_id = event_id;
    }

    @Override
    public String getSaltedhash() {
        if (_saltedhash == null) {
            return StringPool.BLANK;
        } else {
            return _saltedhash;
        }
    }

    @Override
    public void setSaltedhash(String saltedhash) {
        _saltedhash = saltedhash;
    }

    @Override
    public String getOrganisaatio() {
        if (_organisaatio == null) {
            return StringPool.BLANK;
        } else {
            return _organisaatio;
        }
    }

    @Override
    public void setOrganisaatio(String organisaatio) {
        _organisaatio = organisaatio;
    }

    @Override
    public String getAineisto() {
        if (_aineisto == null) {
            return StringPool.BLANK;
        } else {
            return _aineisto;
        }
    }

    @Override
    public void setAineisto(String aineisto) {
        _aineisto = aineisto;
    }

    @Override
    public int getTiedotojenlkm() {
        return _tiedotojenlkm;
    }

    @Override
    public void setTiedotojenlkm(int tiedotojenlkm) {
        _tiedotojenlkm = tiedotojenlkm;
    }

    @Override
    public Date getPaiva() {
        return _paiva;
    }

    @Override
    public void setPaiva(Date paiva) {
        _paiva = paiva;
    }

    @Override
    public paituliloki toEscapedModel() {
        if (_escapedModel == null) {
            _escapedModel = (paituliloki) ProxyUtil.newProxyInstance(_classLoader,
                    _escapedModelInterfaces, new AutoEscapeBeanHandler(this));
        }

        return _escapedModel;
    }

    @Override
    public Object clone() {
        paitulilokiImpl paitulilokiImpl = new paitulilokiImpl();

        paitulilokiImpl.setEvent_id(getEvent_id());
        paitulilokiImpl.setSaltedhash(getSaltedhash());
        paitulilokiImpl.setOrganisaatio(getOrganisaatio());
        paitulilokiImpl.setAineisto(getAineisto());
        paitulilokiImpl.setTiedotojenlkm(getTiedotojenlkm());
        paitulilokiImpl.setPaiva(getPaiva());

        paitulilokiImpl.resetOriginalValues();

        return paitulilokiImpl;
    }

    @Override
    public int compareTo(paituliloki paituliloki) {
        int primaryKey = paituliloki.getPrimaryKey();

        if (getPrimaryKey() < primaryKey) {
            return -1;
        } else if (getPrimaryKey() > primaryKey) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof paituliloki)) {
            return false;
        }

        paituliloki paituliloki = (paituliloki) obj;

        int primaryKey = paituliloki.getPrimaryKey();

        if (getPrimaryKey() == primaryKey) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getPrimaryKey();
    }

    @Override
    public void resetOriginalValues() {
    }

    @Override
    public CacheModel<paituliloki> toCacheModel() {
        paitulilokiCacheModel paitulilokiCacheModel = new paitulilokiCacheModel();

        paitulilokiCacheModel.event_id = getEvent_id();

        paitulilokiCacheModel.saltedhash = getSaltedhash();

        String saltedhash = paitulilokiCacheModel.saltedhash;

        if ((saltedhash != null) && (saltedhash.length() == 0)) {
            paitulilokiCacheModel.saltedhash = null;
        }

        paitulilokiCacheModel.organisaatio = getOrganisaatio();

        String organisaatio = paitulilokiCacheModel.organisaatio;

        if ((organisaatio != null) && (organisaatio.length() == 0)) {
            paitulilokiCacheModel.organisaatio = null;
        }

        paitulilokiCacheModel.aineisto = getAineisto();

        String aineisto = paitulilokiCacheModel.aineisto;

        if ((aineisto != null) && (aineisto.length() == 0)) {
            paitulilokiCacheModel.aineisto = null;
        }

        paitulilokiCacheModel.tiedotojenlkm = getTiedotojenlkm();

        Date paiva = getPaiva();

        if (paiva != null) {
            paitulilokiCacheModel.paiva = paiva.getTime();
        } else {
            paitulilokiCacheModel.paiva = Long.MIN_VALUE;
        }

        return paitulilokiCacheModel;
    }

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(13);

        sb.append("{event_id=");
        sb.append(getEvent_id());
        sb.append(", saltedhash=");
        sb.append(getSaltedhash());
        sb.append(", organisaatio=");
        sb.append(getOrganisaatio());
        sb.append(", aineisto=");
        sb.append(getAineisto());
        sb.append(", tiedotojenlkm=");
        sb.append(getTiedotojenlkm());
        sb.append(", paiva=");
        sb.append(getPaiva());
        sb.append("}");

        return sb.toString();
    }

    @Override
    public String toXmlString() {
        StringBundler sb = new StringBundler(22);

        sb.append("<model><model-name>");
        sb.append("fi.csc.avaa.paituli.db.model.paituliloki");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>event_id</column-name><column-value><![CDATA[");
        sb.append(getEvent_id());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>saltedhash</column-name><column-value><![CDATA[");
        sb.append(getSaltedhash());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>organisaatio</column-name><column-value><![CDATA[");
        sb.append(getOrganisaatio());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>aineisto</column-name><column-value><![CDATA[");
        sb.append(getAineisto());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>tiedotojenlkm</column-name><column-value><![CDATA[");
        sb.append(getTiedotojenlkm());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>paiva</column-name><column-value><![CDATA[");
        sb.append(getPaiva());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}