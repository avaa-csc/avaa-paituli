package fi.csc.avaa.paituli.db.service.persistence;

import com.liferay.portal.kernel.dao.orm.BaseActionableDynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;

import fi.csc.avaa.paituli.db.model.paituliloki;
import fi.csc.avaa.paituli.db.service.paitulilokiLocalServiceUtil;

/**
 * @author pj
 * @generated
 */
public abstract class paitulilokiActionableDynamicQuery
    extends BaseActionableDynamicQuery {
    public paitulilokiActionableDynamicQuery() throws SystemException {
        setBaseLocalService(paitulilokiLocalServiceUtil.getService());
        setClass(paituliloki.class);

        setClassLoader(fi.csc.avaa.paituli.db.service.ClpSerializer.class.getClassLoader());

        setPrimaryKeyPropertyName("event_id");
    }
}
