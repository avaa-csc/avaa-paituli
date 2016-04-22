package fi.csc.avaa.paituli.db.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link paitulilokiLocalService}.
 *
 * @author pj
 * @see paitulilokiLocalService
 * @generated
 */
public class paitulilokiLocalServiceWrapper implements paitulilokiLocalService,
    ServiceWrapper<paitulilokiLocalService> {
    private paitulilokiLocalService _paitulilokiLocalService;

    public paitulilokiLocalServiceWrapper(
        paitulilokiLocalService paitulilokiLocalService) {
        _paitulilokiLocalService = paitulilokiLocalService;
    }

    /**
    * Adds the paituliloki to the database. Also notifies the appropriate model listeners.
    *
    * @param paituliloki the paituliloki
    * @return the paituliloki that was added
    * @throws SystemException if a system exception occurred
    */
    @Override
    public fi.csc.avaa.paituli.db.model.paituliloki addpaituliloki(
        fi.csc.avaa.paituli.db.model.paituliloki paituliloki)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.addpaituliloki(paituliloki);
    }

    /**
    * Creates a new paituliloki with the primary key. Does not add the paituliloki to the database.
    *
    * @param event_id the primary key for the new paituliloki
    * @return the new paituliloki
    */
    @Override
    public fi.csc.avaa.paituli.db.model.paituliloki createpaituliloki(
        int event_id) {
        return _paitulilokiLocalService.createpaituliloki(event_id);
    }

    /**
    * Deletes the paituliloki with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param event_id the primary key of the paituliloki
    * @return the paituliloki that was removed
    * @throws PortalException if a paituliloki with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    @Override
    public fi.csc.avaa.paituli.db.model.paituliloki deletepaituliloki(
        int event_id)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.deletepaituliloki(event_id);
    }

    /**
    * Deletes the paituliloki from the database. Also notifies the appropriate model listeners.
    *
    * @param paituliloki the paituliloki
    * @return the paituliloki that was removed
    * @throws SystemException if a system exception occurred
    */
    @Override
    public fi.csc.avaa.paituli.db.model.paituliloki deletepaituliloki(
        fi.csc.avaa.paituli.db.model.paituliloki paituliloki)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.deletepaituliloki(paituliloki);
    }

    @Override
    public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
        return _paitulilokiLocalService.dynamicQuery();
    }

    /**
    * Performs a dynamic query on the database and returns the matching rows.
    *
    * @param dynamicQuery the dynamic query
    * @return the matching rows
    * @throws SystemException if a system exception occurred
    */
    @Override
    @SuppressWarnings("rawtypes")
    public java.util.List dynamicQuery(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.dynamicQuery(dynamicQuery);
    }

    /**
    * Performs a dynamic query on the database and returns a range of the matching rows.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link fi.csc.avaa.paituli.db.model.impl.paitulilokiModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
    * </p>
    *
    * @param dynamicQuery the dynamic query
    * @param start the lower bound of the range of model instances
    * @param end the upper bound of the range of model instances (not inclusive)
    * @return the range of matching rows
    * @throws SystemException if a system exception occurred
    */
    @Override
    @SuppressWarnings("rawtypes")
    public java.util.List dynamicQuery(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
        int end) throws com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.dynamicQuery(dynamicQuery, start, end);
    }

    /**
    * Performs a dynamic query on the database and returns an ordered range of the matching rows.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link fi.csc.avaa.paituli.db.model.impl.paitulilokiModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
    * </p>
    *
    * @param dynamicQuery the dynamic query
    * @param start the lower bound of the range of model instances
    * @param end the upper bound of the range of model instances (not inclusive)
    * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
    * @return the ordered range of matching rows
    * @throws SystemException if a system exception occurred
    */
    @Override
    @SuppressWarnings("rawtypes")
    public java.util.List dynamicQuery(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
        int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.dynamicQuery(dynamicQuery, start, end,
            orderByComparator);
    }

    /**
    * Returns the number of rows that match the dynamic query.
    *
    * @param dynamicQuery the dynamic query
    * @return the number of rows that match the dynamic query
    * @throws SystemException if a system exception occurred
    */
    @Override
    public long dynamicQueryCount(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.dynamicQueryCount(dynamicQuery);
    }

    /**
    * Returns the number of rows that match the dynamic query.
    *
    * @param dynamicQuery the dynamic query
    * @param projection the projection to apply to the query
    * @return the number of rows that match the dynamic query
    * @throws SystemException if a system exception occurred
    */
    @Override
    public long dynamicQueryCount(
        com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
        com.liferay.portal.kernel.dao.orm.Projection projection)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.dynamicQueryCount(dynamicQuery,
            projection);
    }

    @Override
    public fi.csc.avaa.paituli.db.model.paituliloki fetchpaituliloki(
        int event_id)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.fetchpaituliloki(event_id);
    }

    /**
    * Returns the paituliloki with the primary key.
    *
    * @param event_id the primary key of the paituliloki
    * @return the paituliloki
    * @throws PortalException if a paituliloki with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    @Override
    public fi.csc.avaa.paituli.db.model.paituliloki getpaituliloki(int event_id)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.getpaituliloki(event_id);
    }

    @Override
    public com.liferay.portal.model.PersistedModel getPersistedModel(
        java.io.Serializable primaryKeyObj)
        throws com.liferay.portal.kernel.exception.PortalException,
            com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.getPersistedModel(primaryKeyObj);
    }

    /**
    * Returns a range of all the paitulilokis.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link fi.csc.avaa.paituli.db.model.impl.paitulilokiModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
    * </p>
    *
    * @param start the lower bound of the range of paitulilokis
    * @param end the upper bound of the range of paitulilokis (not inclusive)
    * @return the range of paitulilokis
    * @throws SystemException if a system exception occurred
    */
    @Override
    public java.util.List<fi.csc.avaa.paituli.db.model.paituliloki> getpaitulilokis(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.getpaitulilokis(start, end);
    }

    /**
    * Returns the number of paitulilokis.
    *
    * @return the number of paitulilokis
    * @throws SystemException if a system exception occurred
    */
    @Override
    public int getpaitulilokisCount()
        throws com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.getpaitulilokisCount();
    }

    /**
    * Updates the paituliloki in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
    *
    * @param paituliloki the paituliloki
    * @return the paituliloki that was updated
    * @throws SystemException if a system exception occurred
    */
    @Override
    public fi.csc.avaa.paituli.db.model.paituliloki updatepaituliloki(
        fi.csc.avaa.paituli.db.model.paituliloki paituliloki)
        throws com.liferay.portal.kernel.exception.SystemException {
        return _paitulilokiLocalService.updatepaituliloki(paituliloki);
    }

    /**
    * Returns the Spring bean ID for this bean.
    *
    * @return the Spring bean ID for this bean
    */
    @Override
    public java.lang.String getBeanIdentifier() {
        return _paitulilokiLocalService.getBeanIdentifier();
    }

    /**
    * Sets the Spring bean ID for this bean.
    *
    * @param beanIdentifier the Spring bean ID for this bean
    */
    @Override
    public void setBeanIdentifier(java.lang.String beanIdentifier) {
        _paitulilokiLocalService.setBeanIdentifier(beanIdentifier);
    }

    @Override
    public java.lang.Object invokeMethod(java.lang.String name,
        java.lang.String[] parameterTypes, java.lang.Object[] arguments)
        throws java.lang.Throwable {
        return _paitulilokiLocalService.invokeMethod(name, parameterTypes,
            arguments);
    }

    /**
     * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
     */
    public paitulilokiLocalService getWrappedpaitulilokiLocalService() {
        return _paitulilokiLocalService;
    }

    /**
     * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
     */
    public void setWrappedpaitulilokiLocalService(
        paitulilokiLocalService paitulilokiLocalService) {
        _paitulilokiLocalService = paitulilokiLocalService;
    }

    @Override
    public paitulilokiLocalService getWrappedService() {
        return _paitulilokiLocalService;
    }

    @Override
    public void setWrappedService(
        paitulilokiLocalService paitulilokiLocalService) {
        _paitulilokiLocalService = paitulilokiLocalService;
    }
}
