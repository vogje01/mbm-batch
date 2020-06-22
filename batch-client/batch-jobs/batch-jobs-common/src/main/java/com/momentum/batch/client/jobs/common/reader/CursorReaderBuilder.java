package com.momentum.batch.client.jobs.common.reader;

import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.HibernateCursorItemReader;
import org.springframework.batch.item.database.orm.HibernateQueryProvider;

import javax.persistence.EntityManagerFactory;
import java.util.Map;

/**
 * Cursor reader extending the Hibernate cursor reader.
 *
 * <p>
 * The Hibernate cursor item reader uses an own session, which is kept open as long as the cursor is opened. Therefore
 * the reader does not participate in the usual transaction reader/processor/writer transaction. If this is a problem
 * use a PagedReader.
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
    public CursorReaderBuilder<T> entityManagerFactory(EntityManagerFactory entityManagerFactory) {
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
    public CursorReaderBuilder<T> queryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    /**
     * Sets the parameters for the parametrized query string.
     *
     * @param params parameter map.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder<T> parameters(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    /**
     * Sets the fetch size.
     *
     * @param fetchSize fetch size.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder<T> fetchSize(int fetchSize) {
        this.fetchSize = fetchSize;
        return this;
    }

    /**
     * Sets the max item count.
     *
     * @param maxItemCount maximal number of items to process.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder<T> maxItems(int maxItemCount) {
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
    public CursorReaderBuilder<T> currentItem(int currentItem) {
        this.currentItem = currentItem;
        return this;
    }

    /**
     * Sets the query timeout in seconds.
     *
     * @param timeout query timeout in seconds.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder<T> timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * Sets the query hints.
     *
     * @param queryHint query hints.
     * @return CursorReaderBuilder
     */
    public CursorReaderBuilder<T> queryHint(String queryHint) {
        this.queryHint = queryHint;
        return this;
    }

    /**
     * Build the cursor reader.
     *
     * @return item stream cursor reader.
     */
    public ItemStreamReader<T> build() {
        QueryProvider<T> queryProvider = new QueryProvider<>();
        queryProvider.setQueryString(queryString);
        queryProvider.setQueryHint(queryHint);
        queryProvider.setTimeout(timeout);

        // The actual reader
        HibernateCursorItemReader<T> hibernateCursorItemReader = new HibernateCursorItemReader<>();
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