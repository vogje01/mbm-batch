package com.momentum.batch.client.common.job.reader;

import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.HibernateCursorItemReader;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

/**
 * Cursor reader extending the Hibernate cursor reader.
 * <p>
 * Because DB2 in HLAG uses uncommitted read as default, we need to use a query provider, which introduces another
 * transaction isolation level. Used is 'Cursor stability' (CS) isolation level.
 * </p>
 * <p>
 * The Hibernate cursor item reader uses an own session, which is kept open as long as the cursor is opened. Therefore
 * the reader does not participate in the usual transaction reader/processor/writer transaction. If this is a problem
 * use the PagedReader.
 * </p>
 *
 * @param <T> entity class.
 */
public class CursorReaderBuilder<T> {

    private String queryString;

    private String queryHint;

    private SessionFactory sessionFactory;

    private int fetchSize = 1000;

    private int maxItems = Integer.MAX_VALUE;

    private int timeout = 60;

    private int currentItem;
    /**
     * Parameters of the query.
     */
    private Map<String, Object> params;
    /**
     * The actual reader
     */
    private HibernateCursorItemReader<T> hibernateCursorItemReader;

    /**
     * Constructor.
     *
     * @param entityManagerFactory entity manager factory
     */
    public CursorReaderBuilder(EntityManagerFactory entityManagerFactory) {
        entityManagerFactory(entityManagerFactory);
    }

    /**
     * Sets the entity manager factory.
     *
     * @param entityManagerFactory entity manager factory
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder entityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        return this;
    }

    /**
     * Sets the query string.
     * <p>
     * Can be parametrized. Use {@link #parameters} to set the parameters.
     * </p>
     *
     * @param queryString query string.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder queryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    /**
     * Sets the parameters for the parametrized query string.
     *
     * @param params paramter map.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder parameters(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    /**
     * Sets the fetch size.
     *
     * @param fetchSize fetch size.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder fetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
        return this;
    }

    /**
     * Sets the max item count.
     *
     * @param maxItemCount maximal number of items to process.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder maxItems(int maxItemCount) {
        this.maxItems = maxItemCount;
        return this;
    }

    /**
     * Sets the current item.
     * <p>
     * Previous items will be skipped.
     * </p>
     *
     * @param currentItem index of the current item.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder currentItem(int currentItem) {
        this.currentItem = currentItem;
        return this;
    }

    /**
     * Sets the query timeout in seconds.
     *
     * @param timeout query timeout in seconds.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * Sets the query hints.
     * <p>
     * If you need to supply query hints, you need to use the
     * {@link com.hlag.fis.db.db2.config.ExtendedDB2Dialect ExtendedDb2Dialect} in your application.
     * </p>
     *
     * @param queryHint query hints.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder queryHint(String queryHint) {
        this.queryHint = queryHint;
        return this;
    }

    /**
     * Build the cursor reader.
     *
     * @return item stream cursor reader.
     */
    public ItemStreamReader build() {
        QueryProvider queryProvider = new QueryProvider();
        queryProvider.setQueryString(queryString);
        queryProvider.setQueryHint(queryHint);
        queryProvider.setTimeout(timeout);
        hibernateCursorItemReader = new HibernateCursorItemReader<>();
        hibernateCursorItemReader.setSessionFactory(sessionFactory);
        hibernateCursorItemReader.setMaxItemCount(maxItems);
        hibernateCursorItemReader.setFetchSize(fetchSize);
        hibernateCursorItemReader.setUseStatelessSession(true);
        hibernateCursorItemReader.setQueryProvider(queryProvider);
        hibernateCursorItemReader.setParameterValues(params);
        hibernateCursorItemReader.setCurrentItemCount(currentItem);
        return hibernateCursorItemReader;
    }
}