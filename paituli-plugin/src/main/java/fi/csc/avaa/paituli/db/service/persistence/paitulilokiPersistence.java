package fi.csc.avaa.paituli.db.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import fi.csc.avaa.paituli.db.model.paituliloki;

/**
 * The persistence interface for the paituliloki service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author pj
 * @see paitulilokiPersistenceImpl
 * @see paitulilokiUtil
 * @generated
 */
public interface paitulilokiPersistence extends BasePersistence<paituliloki> {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this interface directly. Always use {@link paitulilokiUtil} to access the paituliloki persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
     */

    /**
    * Caches the paituliloki in the entity cache if it is enabled.
    *
    * @param paituliloki the paituliloki
    */
    public void cacheResult(
        fi.csc.avaa.paituli.db.model.paituliloki paituliloki);

    /**
    * Caches the paitulilokis in the entity cache if it is enabled.
    *
    * @param paitulilokis the paitulilokis
    */
    public void cacheResult(
        java.util.List<fi.csc.avaa.paituli.db.model.paituliloki> paitulilokis);

    /**
    * Creates a new paituliloki with the primary key. Does not add the paituliloki to the database.
    *
    * @param event_id the primary key for the new paituliloki
    * @return the new paituliloki
    */
    public fi.csc.avaa.paituli.db.model.paituliloki create(int event_id);

    /**
    * Removes the paituliloki with the primary key from the database. Also notifies the appropriate model listeners.
    *
    * @param event_id the primary key of the paituliloki
    * @return the paituliloki that was removed
    * @throws fi.csc.avaa.paituli.db.NoSuchlokiException if a paituliloki with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public fi.csc.avaa.paituli.db.model.paituliloki remove(int event_id)
        throws com.liferay.portal.kernel.exception.SystemException,
            fi.csc.avaa.paituli.db.NoSuchlokiException;

    public fi.csc.avaa.paituli.db.model.paituliloki updateImpl(
        fi.csc.avaa.paituli.db.model.paituliloki paituliloki)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the paituliloki with the primary key or throws a {@link fi.csc.avaa.paituli.db.NoSuchlokiException} if it could not be found.
    *
    * @param event_id the primary key of the paituliloki
    * @return the paituliloki
    * @throws fi.csc.avaa.paituli.db.NoSuchlokiException if a paituliloki with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public fi.csc.avaa.paituli.db.model.paituliloki findByPrimaryKey(
        int event_id)
        throws com.liferay.portal.kernel.exception.SystemException,
            fi.csc.avaa.paituli.db.NoSuchlokiException;

    /**
    * Returns the paituliloki with the primary key or returns <code>null</code> if it could not be found.
    *
    * @param event_id the primary key of the paituliloki
    * @return the paituliloki, or <code>null</code> if a paituliloki with the primary key could not be found
    * @throws SystemException if a system exception occurred
    */
    public fi.csc.avaa.paituli.db.model.paituliloki fetchByPrimaryKey(
        int event_id)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns all the paitulilokis.
    *
    * @return the paitulilokis
    * @throws SystemException if a system exception occurred
    */
    public java.util.List<fi.csc.avaa.paituli.db.model.paituliloki> findAll()
        throws com.liferay.portal.kernel.exception.SystemException;

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
    public java.util.List<fi.csc.avaa.paituli.db.model.paituliloki> findAll(
        int start, int end)
        throws com.liferay.portal.kernel.exception.SystemException;

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
    public java.util.List<fi.csc.avaa.paituli.db.model.paituliloki> findAll(
        int start, int end,
        com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Removes all the paitulilokis from the database.
    *
    * @throws SystemException if a system exception occurred
    */
    public void removeAll()
        throws com.liferay.portal.kernel.exception.SystemException;

    /**
    * Returns the number of paitulilokis.
    *
    * @return the number of paitulilokis
    * @throws SystemException if a system exception occurred
    */
    public int countAll()
        throws com.liferay.portal.kernel.exception.SystemException;
}
