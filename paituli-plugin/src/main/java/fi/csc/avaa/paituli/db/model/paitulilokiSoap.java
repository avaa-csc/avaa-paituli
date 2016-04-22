package fi.csc.avaa.paituli.db.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author pj
 * @generated
 */
public class paitulilokiSoap implements Serializable {
    private int _event_id;
    private String _saltedhash;
    private String _organisaatio;
    private String _aineisto;
    private int _tiedotojenlkm;
    private Date _paiva;

    public paitulilokiSoap() {
    }

    public static paitulilokiSoap toSoapModel(paituliloki model) {
        paitulilokiSoap soapModel = new paitulilokiSoap();

        soapModel.setEvent_id(model.getEvent_id());
        soapModel.setSaltedhash(model.getSaltedhash());
        soapModel.setOrganisaatio(model.getOrganisaatio());
        soapModel.setAineisto(model.getAineisto());
        soapModel.setTiedotojenlkm(model.getTiedotojenlkm());
        soapModel.setPaiva(model.getPaiva());

        return soapModel;
    }

    public static paitulilokiSoap[] toSoapModels(paituliloki[] models) {
        paitulilokiSoap[] soapModels = new paitulilokiSoap[models.length];

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModel(models[i]);
        }

        return soapModels;
    }

    public static paitulilokiSoap[][] toSoapModels(paituliloki[][] models) {
        paitulilokiSoap[][] soapModels = null;

        if (models.length > 0) {
            soapModels = new paitulilokiSoap[models.length][models[0].length];
        } else {
            soapModels = new paitulilokiSoap[0][0];
        }

        for (int i = 0; i < models.length; i++) {
            soapModels[i] = toSoapModels(models[i]);
        }

        return soapModels;
    }

    public static paitulilokiSoap[] toSoapModels(List<paituliloki> models) {
        List<paitulilokiSoap> soapModels = new ArrayList<paitulilokiSoap>(models.size());

        for (paituliloki model : models) {
            soapModels.add(toSoapModel(model));
        }

        return soapModels.toArray(new paitulilokiSoap[soapModels.size()]);
    }

    public int getPrimaryKey() {
        return _event_id;
    }

    public void setPrimaryKey(int pk) {
        setEvent_id(pk);
    }

    public int getEvent_id() {
        return _event_id;
    }

    public void setEvent_id(int event_id) {
        _event_id = event_id;
    }

    public String getSaltedhash() {
        return _saltedhash;
    }

    public void setSaltedhash(String saltedhash) {
        _saltedhash = saltedhash;
    }

    public String getOrganisaatio() {
        return _organisaatio;
    }

    public void setOrganisaatio(String organisaatio) {
        _organisaatio = organisaatio;
    }

    public String getAineisto() {
        return _aineisto;
    }

    public void setAineisto(String aineisto) {
        _aineisto = aineisto;
    }

    public int getTiedotojenlkm() {
        return _tiedotojenlkm;
    }

    public void setTiedotojenlkm(int tiedotojenlkm) {
        _tiedotojenlkm = tiedotojenlkm;
    }

    public Date getPaiva() {
        return _paiva;
    }

    public void setPaiva(Date paiva) {
        _paiva = paiva;
    }
}
