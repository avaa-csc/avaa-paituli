package fi.csc.avaa.paituli.db.service.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import fi.csc.avaa.paituli.db.NoSuchlokiException;
import fi.csc.avaa.paituli.db.model.paituliloki;
import fi.csc.avaa.paituli.db.model.impl.paitulilokiImpl;
import fi.csc.avaa.paituli.db.model.impl.paitulilokiModelImpl;

/**
 * The persistence implementation for the paituliloki service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author pj
 * @see paitulilokiPersistence
 * @see paitulilokiUtil
 * @generated
 */
public class paitulilokiPersistenceImpl extends BasePersistenceImpl<paituliloki>
    implements paitulilokiPersistence {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never modify or reference this class directly. Always use {@link paitulilokiUtil} to access the paituliloki persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
     */
    public static final String FINDER_CLASS_NAME_ENTITY = paitulilokiImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
        ".List1";
    public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
        ".List2";
    public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(paitulilokiModelImpl.ENTITY_CACHE_ENABLED,
            paitulilokiModelImpl.FINDER_CACHE_ENABLED, paitulilokiImpl.class,
            FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(paitulilokiModelImpl.ENTITY_CACHE_ENABLED,
            paitulilokiModelImpl.FINDER_CACHE_ENABLED, paitulilokiImpl.class,
            FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(paitulilokiModelImpl.ENTITY_CACHE_ENABLED,
            paitulilokiModelImpl.FINDER_CACHE_ENABLED, Long.class,
            FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
    private static final String _SQL_SELECT_PAITULILOKI = "SELECT paituliloki FROM paituliloki paituliloki";
    private static final String _SQL_COUNT_PAITULILOKI = "SELECT COUNT(paituliloki) FROM paituliloki paituliloki";
    private static final String _ORDER_BY_ENTITY_ALIAS = "paituliloki.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No paituliloki exists with the primary key ";
    private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
                PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
    private static Log _log = LogFactoryUtil.getLog(paitulilokiPersistenceImpl.class);
    private static paituliloki _nullpaituliloki = new paitulilokiImpl() {
            @Override
            public Object clone() {
                return this;
            }

            @Override
            public CacheModel<paituliloki> toCacheModel() {
                return _nullpaitulilokiCacheModel;
            }
        };

    private static CacheModel<paituliloki> _nullpaitulilokiCacheModel = new CacheModel<paituliloki>() {
            @Override
            public paituliloki toEntityModel() {
                return _nullpaituliloki;
            }
        };

    public paitulilokiPersistenceImpl() {
        setModelClass(paituliloki.class);
    }

    /**
     * Caches the paituliloki in the entity cache if it is enabled.
     *
     * @param paituliloki the paituliloki
     */
    @Override
    public void cacheResult(paituliloki paituliloki) {
        EntityCacheUtil.putResult(paitulilokiModelImpl.ENTITY_CACHE_ENABLED,
            paitulilokiImpl.class, paituliloki.getPrimaryKey(), paituliloki);

        paituliloki.resetOriginalValues();
    }

    /**
     * Caches the paitulilokis in the entity cache if it is enabled.
     *
     * @param paitulilokis the paitulilokis
     */
    @Override
    public void cacheResult(List<paituliloki> paitulilokis) {
        for (paituliloki paituliloki : paitulilokis) {
            if (EntityCacheUtil.getResult(
                        paitulilokiModelImpl.ENTITY_CACHE_ENABLED,
                        paitulilokiImpl.class, paituliloki.getPrimaryKey()) == null) {
                cacheResult(paituliloki);
            } else {
                paituliloki.resetOriginalValues();
            }
        }
    }

    /**
     * Clears the cache for all paitulilokis.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    @Override
    public void clearCache() {
        if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
            CacheRegistryUtil.clear(paitulilokiImpl.class.getName());
        }

        EntityCacheUtil.clearCache(paitulilokiImpl.class.getName());

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
    }

    /**
     * Clears the cache for the paituliloki.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    @Override
    public void clearCache(paituliloki paituliloki) {
        EntityCacheUtil.removeResult(paitulilokiModelImpl.ENTITY_CACHE_ENABLED,
            paitulilokiImpl.class, paituliloki.getPrimaryKey());

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
    }

    @Override
    public void clearCache(List<paituliloki> paitulilokis) {
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

        for (paituliloki paituliloki : paitulilokis) {
            EntityCacheUtil.removeResult(paitulilokiModelImpl.ENTITY_CACHE_ENABLED,
                paitulilokiImpl.class, paituliloki.getPrimaryKey());
        }
    }

    /**
     * Creates a new paituliloki with the primary key. Does not add the paituliloki to the database.
     *
     * @param event_id the primary key for the new paituliloki
     * @return the new paituliloki
     */
    @Override
    public paituliloki create(int event_id) {
        paituliloki paituliloki = new paitulilokiImpl();

        paituliloki.setNew(true);
        paituliloki.setPrimaryKey(event_id);

        return paituliloki;
    }

    /**
     * Removes the paituliloki with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param event_id the primary key of the paituliloki
     * @return the paituliloki that was removed
     * @throws fi.csc.avaa.paituli.db.NoSuchlokiException if a paituliloki with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public paituliloki remove(int event_id)
        throws NoSuchlokiException, SystemException {
        return remove((Serializable) event_id);
    }

    /**
     * Removes the paituliloki with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the paituliloki
     * @return the paituliloki that was removed
     * @throws fi.csc.avaa.paituli.db.NoSuchlokiException if a paituliloki with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public paituliloki remove(Serializable primaryKey)
        throws NoSuchlokiException, SystemException {
        Session session = null;

        try {
            session = openSession();

            paituliloki paituliloki = (paituliloki) session.get(paitulilokiImpl.class,
                    primaryKey);

            if (paituliloki == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
                }

                throw new NoSuchlokiException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    primaryKey);
            }

            return remove(paituliloki);
        } catch (NoSuchlokiException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    @Override
    protected paituliloki removeImpl(paituliloki paituliloki)
        throws SystemException {
        paituliloki = toUnwrappedModel(paituliloki);

        Session session = null;

        try {
            session = openSession();

            if (!session.contains(paituliloki)) {
                paituliloki = (paituliloki) session.get(paitulilokiImpl.class,
                        paituliloki.getPrimaryKeyObj());
            }

            if (paituliloki != null) {
                session.delete(paituliloki);
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        if (paituliloki != null) {
            clearCache(paituliloki);
        }

        return paituliloki;
    }

    @Override
    public paituliloki updateImpl(
        fi.csc.avaa.paituli.db.model.paituliloki paituliloki)
        throws SystemException {
        paituliloki = toUnwrappedModel(paituliloki);

        boolean isNew = paituliloki.isNew();

        Session session = null;

        try {
            session = openSession();

            if (paituliloki.isNew()) {
                session.save(paituliloki);

                paituliloki.setNew(false);
            } else {
                session.merge(paituliloki);
            }
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

        if (isNew) {
            FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
        }

        EntityCacheUtil.putResult(paitulilokiModelImpl.ENTITY_CACHE_ENABLED,
            paitulilokiImpl.class, paituliloki.getPrimaryKey(), paituliloki);

        return paituliloki;
    }

    protected paituliloki toUnwrappedModel(paituliloki paituliloki) {
        if (paituliloki instanceof paitulilokiImpl) {
            return paituliloki;
        }

        paitulilokiImpl paitulilokiImpl = new paitulilokiImpl();

        paitulilokiImpl.setNew(paituliloki.isNew());
        paitulilokiImpl.setPrimaryKey(paituliloki.getPrimaryKey());

        paitulilokiImpl.setEvent_id(paituliloki.getEvent_id());
        paitulilokiImpl.setSaltedhash(paituliloki.getSaltedhash());
        paitulilokiImpl.setOrganisaatio(paituliloki.getOrganisaatio());
        paitulilokiImpl.setAineisto(paituliloki.getAineisto());
        paitulilokiImpl.setTiedotojenlkm(paituliloki.getTiedotojenlkm());
        paitulilokiImpl.setPaiva(paituliloki.getPaiva());

        return paitulilokiImpl;
    }

    /**
     * Returns the paituliloki with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the paituliloki
     * @return the paituliloki
     * @throws fi.csc.avaa.paituli.db.NoSuchlokiException if a paituliloki with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public paituliloki findByPrimaryKey(Serializable primaryKey)
        throws NoSuchlokiException, SystemException {
        paituliloki paituliloki = fetchByPrimaryKey(primaryKey);

        if (paituliloki == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
            }

            throw new NoSuchlokiException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                primaryKey);
        }

        return paituliloki;
    }

    /**
     * Returns the paituliloki with the primary key or throws a {@link fi.csc.avaa.paituli.db.NoSuchlokiException} if it could not be found.
     *
     * @param event_id the primary key of the paituliloki
     * @return the paituliloki
     * @throws fi.csc.avaa.paituli.db.NoSuchlokiException if a paituliloki with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public paituliloki findByPrimaryKey(int event_id)
        throws NoSuchlokiException, SystemException {
        return findByPrimaryKey((Serializable) event_id);
    }

    /**
     * Returns the paituliloki with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the paituliloki
     * @return the paituliloki, or <code>null</code> if a paituliloki with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public paituliloki fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        paituliloki paituliloki = (paituliloki) EntityCacheUtil.getResult(paitulilokiModelImpl.ENTITY_CACHE_ENABLED,
                paitulilokiImpl.class, primaryKey);

        if (paituliloki == _nullpaituliloki) {
            return null;
        }

        if (paituliloki == null) {
            Session session = null;

            try {
                session = openSession();

                paituliloki = (paituliloki) session.get(paitulilokiImpl.class,
                        primaryKey);

                if (paituliloki != null) {
                    cacheResult(paituliloki);
                } else {
                    EntityCacheUtil.putResult(paitulilokiModelImpl.ENTITY_CACHE_ENABLED,
                        paitulilokiImpl.class, primaryKey, _nullpaituliloki);
                }
            } catch (Exception e) {
                EntityCacheUtil.removeResult(paitulilokiModelImpl.ENTITY_CACHE_ENABLED,
                    paitulilokiImpl.class, primaryKey);

                throw processException(e);
            } finally {
                closeSession(session);
            }
        }

        return paituliloki;
    }

    /**
     * Returns the paituliloki with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param event_id the primary key of the paituliloki
     * @return the paituliloki, or <code>null</code> if a paituliloki with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    @Override
    public paituliloki fetchByPrimaryKey(int event_id)
        throws SystemException {
        return fetchByPrimaryKey((Serializable) event_id);
    }

    /**
     * Returns all the paitulilokis.
     *
     * @return the paitulilokis
     * @throws SystemException if a system exception occurred
     */
    @Override
    public List<paituliloki> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
    public List<paituliloki> findAll(int start, int end)
        throws SystemException {
        return findAll(start, end, null);
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
    @Override
    public List<paituliloki> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        boolean pagination = true;
        FinderPath finderPath = null;
        Object[] finderArgs = null;

        if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
                (orderByComparator == null)) {
            pagination = false;
            finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
            finderArgs = FINDER_ARGS_EMPTY;
        } else {
            finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
            finderArgs = new Object[] { start, end, orderByComparator };
        }

        List<paituliloki> list = (List<paituliloki>) FinderCacheUtil.getResult(finderPath,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_PAITULILOKI);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_PAITULILOKI;

                if (pagination) {
                    sql = sql.concat(paitulilokiModelImpl.ORDER_BY_JPQL);
                }
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (!pagination) {
                    list = (List<paituliloki>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);

                    list = new UnmodifiableList<paituliloki>(list);
                } else {
                    list = (List<paituliloki>) QueryUtil.list(q, getDialect(),
                            start, end);
                }

                cacheResult(list);

                FinderCacheUtil.putResult(finderPath, finderArgs, list);
            } catch (Exception e) {
                FinderCacheUtil.removeResult(finderPath, finderArgs);

                throw processException(e);
            } finally {
                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Removes all the paitulilokis from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    @Override
    public void removeAll() throws SystemException {
        for (paituliloki paituliloki : findAll()) {
            remove(paituliloki);
        }
    }

    /**
     * Returns the number of paitulilokis.
     *
     * @return the number of paitulilokis
     * @throws SystemException if a system exception occurred
     */
    @Override
    public int countAll() throws SystemException {
        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
                FINDER_ARGS_EMPTY, this);

        if (count == null) {
            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(_SQL_COUNT_PAITULILOKI);

                count = (Long) q.uniqueResult();

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
                    FINDER_ARGS_EMPTY, count);
            } catch (Exception e) {
                FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
                    FINDER_ARGS_EMPTY);

                throw processException(e);
            } finally {
                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Initializes the paituliloki persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.fi.csc.avaa.paituli.db.model.paituliloki")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<paituliloki>> listenersList = new ArrayList<ModelListener<paituliloki>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<paituliloki>) InstanceFactory.newInstance(
                            getClassLoader(), listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(paitulilokiImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
    }
}
