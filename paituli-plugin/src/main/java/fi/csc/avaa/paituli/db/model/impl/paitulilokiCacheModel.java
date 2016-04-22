package fi.csc.avaa.paituli.db.model.impl;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import fi.csc.avaa.paituli.db.model.paituliloki;

/**
 * The cache model class for representing paituliloki in entity cache.
 *
 * @author pj
 * @see paituliloki
 * @generated
 */
public class paitulilokiCacheModel implements CacheModel<paituliloki>,
    Externalizable {
    public int event_id;
    public String saltedhash;
    public String organisaatio;
    public String aineisto;
    public int tiedotojenlkm;
    public long paiva;

    @Override
    public String toString() {
        StringBundler sb = new StringBundler(13);

        sb.append("{event_id=");
        sb.append(event_id);
        sb.append(", saltedhash=");
        sb.append(saltedhash);
        sb.append(", organisaatio=");
        sb.append(organisaatio);
        sb.append(", aineisto=");
        sb.append(aineisto);
        sb.append(", tiedotojenlkm=");
        sb.append(tiedotojenlkm);
        sb.append(", paiva=");
        sb.append(paiva);
        sb.append("}");

        return sb.toString();
    }

    @Override
    public paituliloki toEntityModel() {
        paitulilokiImpl paitulilokiImpl = new paitulilokiImpl();

        paitulilokiImpl.setEvent_id(event_id);

        if (saltedhash == null) {
            paitulilokiImpl.setSaltedhash(StringPool.BLANK);
        } else {
            paitulilokiImpl.setSaltedhash(saltedhash);
        }

        if (organisaatio == null) {
            paitulilokiImpl.setOrganisaatio(StringPool.BLANK);
        } else {
            paitulilokiImpl.setOrganisaatio(organisaatio);
        }

        if (aineisto == null) {
            paitulilokiImpl.setAineisto(StringPool.BLANK);
        } else {
            paitulilokiImpl.setAineisto(aineisto);
        }

        paitulilokiImpl.setTiedotojenlkm(tiedotojenlkm);

        if (paiva == Long.MIN_VALUE) {
            paitulilokiImpl.setPaiva(null);
        } else {
            paitulilokiImpl.setPaiva(new Date(paiva));
        }

        paitulilokiImpl.resetOriginalValues();

        return paitulilokiImpl;
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException {
        event_id = objectInput.readInt();
        saltedhash = objectInput.readUTF();
        organisaatio = objectInput.readUTF();
        aineisto = objectInput.readUTF();
        tiedotojenlkm = objectInput.readInt();
        paiva = objectInput.readLong();
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput)
        throws IOException {
        objectOutput.writeInt(event_id);

        if (saltedhash == null) {
            objectOutput.writeUTF(StringPool.BLANK);
        } else {
            objectOutput.writeUTF(saltedhash);
        }

        if (organisaatio == null) {
            objectOutput.writeUTF(StringPool.BLANK);
        } else {
            objectOutput.writeUTF(organisaatio);
        }

        if (aineisto == null) {
            objectOutput.writeUTF(StringPool.BLANK);
        } else {
            objectOutput.writeUTF(aineisto);
        }

        objectOutput.writeInt(tiedotojenlkm);
        objectOutput.writeLong(paiva);
    }
}
