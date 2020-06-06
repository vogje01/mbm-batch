package com.momentum.batch.client.jobs.common.reader;

import org.hibernate.SessionFactory;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.database.HibernatePagingItemReader;

import javax.persistence.EntityManagerFactory;

@SuppressWarnings("squid:MaximumInheritanceDepth")
public class PagingReaderBuilder<T> {

    private int timeout = 60;

    private int maxItems = Integer.MAX_VALUE;

    private int pageSize = Integer.MAX_VALUE;

    private String queryString;

    private String queryHint;

    private SessionFactory sessionFactory;

    private HibernatePagingItemReader<T> hibernatePagingItemReader;

    public PagingReaderBuilder(EntityManagerFactory entityManagerFactory) {
        entityManagerFactory(entityManagerFactory);
    }

    public PagingReaderBuilder entityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        return this;
    }

    public PagingReaderBuilder pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PagingReaderBuilder timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public PagingReaderBuilder queryString(String query) {
        this.queryString = query;
        return this;
    }

    public PagingReaderBuilder queryHint(String queryHint) {
        this.queryHint = queryHint;
        return this;
    }

    public PagingReaderBuilder maxItems(int maxItemCount) {
        this.maxItems = maxItemCount;
        return this;
    }

    public ItemStreamReader build() {
        QueryProvider queryProvider = new QueryProvider();
        queryProvider.setQueryString(queryString);
        queryProvider.setQueryHint(queryHint);
        queryProvider.setTimeout(timeout);
        //queryProvider.setStatelessSession(sessionFactory.openStatelessSession());
        hibernatePagingItemReader = new HibernatePagingItemReader<>();
        hibernatePagingItemReader.setSessionFactory(sessionFactory);
        hibernatePagingItemReader.setPageSize(pageSize);
        hibernatePagingItemReader.setMaxItemCount(maxItems);
        hibernatePagingItemReader.setUseStatelessSession(false);
        hibernatePagingItemReader.setQueryProvider(queryProvider);
        return hibernatePagingItemReader;
    }
}