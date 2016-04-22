package fi.csc.avaa.paituli.db.service.persistence;

import java.util.List;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import fi.csc.avaa.paituli.db.model.paituliloki;

/**
 * The persistence utility for the paituliloki service. This utility wraps {@link paitulilokiPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author pj
 * @see paitulilokiPersistence
 * @see paitulilokiPersistenceImpl
 * @generated
 */
public class paitulilokiUtil {
    private static paitulilokiPersistence _persistence;

    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
     */

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
     */
    public static void clearCache() {
        getPersistence().clearCache();
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
     */
    public static void clearCache(paituliloki paituliloki) {
        getPersistence().clearCache(paituliloki);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
     */
    public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
        throws SystemException {
        return getPersistence().countWithDynamicQuery(dynamicQuery);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
     */
    public static List<paituliloki> findWithDynamicQuery(
        DynamicQuery dynamicQuery) throws SystemException {
        return getPersistence().findWithDynamicQuery(dynamicQuery);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
     */
    public static List<paituliloki> findWithDynamicQuery(
        DynamicQuery dynamicQuery, int start, int end)
        throws SystemException {
        return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
     */
    public static List<paituliloki> findWithDynamicQuery(
        DynamicQuery dynamicQuery, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        return getPersistence()
                   .findWithDynamicQuery(dynamicQuery, start, end,
            orderByComparator);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
     */
    public static paituliloki update(paituliloki paituliloki)
        throws SystemException {
        return getPersistence().update(paituliloki);
    }

    /**
     * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
     */
    public static paituliloki update(paituliloki paituliloki,
        ServiceContext serviceContext) throws SystemException {
        return getPersistence().update(paituliloki, serviceContext);
    }

    /**
    * Caches the paituliloki in the entity cache if it is enabled.
    *
    * @param paituliloki the paituliloki
    */
    public static void cacheResult(
        fi.csc.avaa.paituli.db.model.paituliloki paituliloki) {
        getPersistence().cacheResult(paituliloki);
    }

    /**
    * Caches the paitulilokis in the entity cache if it is enabled.
    *
    * @param paitulilokis the paitulilokis
    */
    public static void cacheResult(
        java.util.List<fi.csc.avaa.paituli.db.model.paituliloki> paitulilokis) {
        getPersistence().cacheResult(paitulilokis);
    }

    /**
    * Creates a new paituliloki with the primary key. Does not add the paituliloki to the database.
    *
    * @param event_id the primary key for the new paituliloki
    * @return the new paituliloki
    */
    public static fi.csc.avaa.paituli.db.model.paituliloki create(int event_id) {
        return getPersistence().create(event_id);
    }

    /**
    * Removes the paituliloki with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param event_id the primary key of the paituliloki
    * @return the paituliloki that was removed
    * @throws fi.csc.avaa.paituli.db.NoSuchlokiException if a paituliloki with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static fi.csc.avaa.paituli.db.model.paituliloki remove(int event_id)
        throws com.liferay.portal.kernel.exception.SystemException,
            fi.csc.avaa.paituli.db.NoSuchlokiException {
        return getPersistence().remove(event_id);
    }

    public static fi.csc.avaa.paituli.db.model.paituliloki updateImpl(
        fi.csc.avaa.paituli.db.model.paituliloki paituliloki)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().updateImpl(paituliloki);
    }

    /**
    * Returns the paituliloki with the primary key or throws a {@link fi.csc.avaa.paituli.db.NoSuchlokiException} if it could not be found.
    *
    * @param event_id the primary key of the paituliloki
    * @return the paituliloki
    * @throws fi.csc.avaa.paituli.db.NoSuchlokiException if a paituliloki with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static fi.csc.avaa.paituli.db.model.paituliloki findByPrimaryKey(
        int event_id)
        throws com.liferay.portal.kernel.exception.SystemException,
            fi.csc.avaa.paituli.db.NoSuchlokiException {
        return getPersistence().findByPrimaryKey(event_id);
    }

    /**
    * Returns the paituliloki with the primary key or returns <code>null</code> if it could not be found.
    *
    * @param event_id the primary key of the paituliloki
    * @return the paituliloki, or <code>null</code> if a paituliloki with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public static fi.csc.avaa.paituli.db.model.paituliloki fetchByPrimaryKey(
        int event_id)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().fetchByPrimaryKey(event_id);
    }

    /**
    * Returns all the paitulilokis.
    *
    * @return the paitulilokis
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<fi.csc.avaa.paituli.db.model.paituliloki> findAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll();
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
    public static java.util.List<fi.csc.avaa.paituli.db.model.paituliloki> findAll(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll(start, end);
    }

    /**
    * Returns an ordered range of all the paitulilokis.
    *
    * <p>
    * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link fi.csc.avaa.paituli.db.model.impl.paitulilokiModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
    * </p>
    *
    * @param start the lower bound of the range of paitulilokis
    * @param end the upper bound of the range of paitulilokis (not inclusive)
    * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
    * @return the ordered range of paitulilokis
    * @throws SystemException if a system exception occurred
    */
    public static java.util.List<fi.csc.avaa.paituli.db.model.paituliloki> findAll(
        int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().findAll(start, end, orderByComparator);
    }

    /**
    * Removes all the paitulilokis from the database.
    *
    * @throws SystemException if a system exception occurred
    */
    public static void removeAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        getPersistence().removeAll();
    }

    /**
    * Returns the number of paitulilokis.
    *
    * @return the number of paitulilokis
    * @throws SystemException if a system exception occurred
    */
    public static int countAll()
        throws com.liferay.portal.kernel.exception.SystemException {
        return getPersistence().countAll();
    }

    public static paitulilokiPersistence getPersistence() {
        if (_persistence == null) {
            _persistence = (paitulilokiPersistence) PortletBeanLocatorUtil.locate(fi.csc.avaa.paituli.db.service.ClpSerializer.getServletContextName(),
                    paitulilokiPersistence.class.getName());

            ReferenceRegistry.registerReference(paitulilokiUtil.class,
                "_persistence");
        }

        return _persistence;
    }

    /**
     * @deprecated As of 6.2.0
     */
    public void setPersistence(paitulilokiPersistence persistence) {
    }
}
