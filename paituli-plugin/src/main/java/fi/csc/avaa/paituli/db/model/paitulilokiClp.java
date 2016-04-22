package fi.csc.avaa.paituli.db.model;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import fi.csc.avaa.paituli.db.service.ClpSerializer;
import fi.csc.avaa.paituli.db.service.paitulilokiLocalServiceUtil;


public class paitulilokiClp extends BaseModelImpl<paituliloki>
    implements paituliloki {
    private int _event_id;
    private String _saltedhash;
    private String _organisaatio;
    private String _aineisto;
    private int _tiedotojenlkm;
    private Date _paiva;
    private BaseModel<?> _paitulilokiRemoteModel;
    private Class<?> _clpSerializerClass = fi.csc.avaa.paituli.db.service.ClpSerializer.class;

    public paitulilokiClp() {
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

        if (_paitulilokiRemoteModel != null) {
            try {
                Class<?> clazz = _paitulilokiRemoteModel.getClass();

                Method method = clazz.getMethod("setEvent_id", int.class);

                method.invoke(_paitulilokiRemoteModel, event_id);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public String getSaltedhash() {
        return _saltedhash;
    }

    @Override
    public void setSaltedhash(String saltedhash) {
        _saltedhash = saltedhash;

        if (_paitulilokiRemoteModel != null) {
            try {
                Class<?> clazz = _paitulilokiRemoteModel.getClass();

                Method method = clazz.getMethod("setSaltedhash", String.class);

                method.invoke(_paitulilokiRemoteModel, saltedhash);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public String getOrganisaatio() {
        return _organisaatio;
    }

    @Override
    public void setOrganisaatio(String organisaatio) {
        _organisaatio = organisaatio;

        if (_paitulilokiRemoteModel != null) {
            try {
                Class<?> clazz = _paitulilokiRemoteModel.getClass();

                Method method = clazz.getMethod("setOrganisaatio", String.class);

                method.invoke(_paitulilokiRemoteModel, organisaatio);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public String getAineisto() {
        return _aineisto;
    }

    @Override
    public void setAineisto(String aineisto) {
        _aineisto = aineisto;

        if (_paitulilokiRemoteModel != null) {
            try {
                Class<?> clazz = _paitulilokiRemoteModel.getClass();

                Method method = clazz.getMethod("setAineisto", String.class);

                method.invoke(_paitulilokiRemoteModel, aineisto);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public int getTiedotojenlkm() {
        return _tiedotojenlkm;
    }

    @Override
    public void setTiedotojenlkm(int tiedotojenlkm) {
        _tiedotojenlkm = tiedotojenlkm;

        if (_paitulilokiRemoteModel != null) {
            try {
                Class<?> clazz = _paitulilokiRemoteModel.getClass();

                Method method = clazz.getMethod("setTiedotojenlkm", int.class);

                method.invoke(_paitulilokiRemoteModel, tiedotojenlkm);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    @Override
    public Date getPaiva() {
        return _paiva;
    }

    @Override
    public void setPaiva(Date paiva) {
        _paiva = paiva;

        if (_paitulilokiRemoteModel != null) {
            try {
                Class<?> clazz = _paitulilokiRemoteModel.getClass();

                Method method = clazz.getMethod("setPaiva", Date.class);

                method.invoke(_paitulilokiRemoteModel, paiva);
            } catch (Exception e) {
                throw new UnsupportedOperationException(e);
            }
        }
    }

    public BaseModel<?> getpaitulilokiRemoteModel() {
        return _paitulilokiRemoteModel;
    }

    public void setpaitulilokiRemoteModel(BaseModel<?> paitulilokiRemoteModel) {
        _paitulilokiRemoteModel = paitulilokiRemoteModel;
    }

    public Object invokeOnRemoteModel(String methodName,
        Class<?>[] parameterTypes, Object[] parameterValues)
        throws Exception {
        Object[] remoteParameterValues = new Object[parameterValues.length];

        for (int i = 0; i < parameterValues.length; i++) {
            if (parameterValues[i] != null) {
                remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
            }
        }

        Class<?> remoteModelClass = _paitulilokiRemoteModel.getClass();

        ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

        Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            if (parameterTypes[i].isPrimitive()) {
                remoteParameterTypes[i] = parameterTypes[i];
            } else {
                String parameterTypeName = parameterTypes[i].getName();

                remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
            }
        }

        Method method = remoteModelClass.getMethod(methodName,
                remoteParameterTypes);

        Object returnValue = method.invoke(_paitulilokiRemoteModel,
                remoteParameterValues);

        if (returnValue != null) {
            returnValue = ClpSerializer.translateOutput(returnValue);
        }

        return returnValue;
    }

    @Override
    public void persist() throws SystemException {
        if (this.isNew()) {
            paitulilokiLocalServiceUtil.addpaituliloki(this);
        } else {
            paitulilokiLocalServiceUtil.updatepaituliloki(this);
        }
    }

    @Override
    public paituliloki toEscapedModel() {
        return (paituliloki) ProxyUtil.newProxyInstance(paituliloki.class.getClassLoader(),
            new Class[] { paituliloki.class }, new AutoEscapeBeanHandler(this));
    }

    @Override
    public Object clone() {
        paitulilokiClp clone = new paitulilokiClp();

        clone.setEvent_id(getEvent_id());
        clone.setSaltedhash(getSaltedhash());
        clone.setOrganisaatio(getOrganisaatio());
        clone.setAineisto(getAineisto());
        clone.setTiedotojenlkm(getTiedotojenlkm());
        clone.setPaiva(getPaiva());

        return clone;
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

        if (!(obj instanceof paitulilokiClp)) {
            return false;
        }

        paitulilokiClp paituliloki = (paitulilokiClp) obj;

        int primaryKey = paituliloki.getPrimaryKey();

        if (getPrimaryKey() == primaryKey) {
            return true;
        } else {
            return false;
        }
    }

    public Class<?> getClpSerializerClass() {
        return _clpSerializerClass;
    }

    @Override
    public int hashCode() {
        return getPrimaryKey();
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
