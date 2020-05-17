package com.hlag.fis.batch.reader;

import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.query.Query;
import org.springframework.batch.item.database.orm.HibernateQueryProvider;

public class QueryProvider implements HibernateQueryProvider {

    private Session session;

    private StatelessSession statelessSession;

    private String queryString;

    private String queryHint;

    private int timeout = 60;

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public void setQueryHint(String queryHint) {
        this.queryHint = queryHint;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public Query createQuery() {
        Query query;
        if (statelessSession != null) {
            query = statelessSession.createQuery(queryString);
        } else {
            query = session.createQuery(queryString);
        }
        if (queryHint != null && queryHint.isEmpty()) {
            query.addQueryHint(queryHint);
        }
        query.setTimeout(timeout);
        query.setReadOnly(true);
        return query;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void setStatelessSession(StatelessSession statelessSession) {
        this.statelessSession = statelessSession;
    }
}
