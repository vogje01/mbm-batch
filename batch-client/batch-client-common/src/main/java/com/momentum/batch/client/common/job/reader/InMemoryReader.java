package com.momentum.batch.client.common.job.reader;

import org.springframework.batch.item.ItemReader;

import javax.persistence.EntityManagerFactory;
import java.util.List;

public class InMemoryReader<T> implements ItemReader<T> {

    private EntityManagerFactory entityManagerFactory;

    private long maxSize = Long.MAX_VALUE;

    private int nextIndex = 0;

    private List<T> list;

    public InMemoryReader(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public InMemoryReader queryString(String queryString) {
        list = entityManagerFactory
                .createEntityManager()
                .createQuery(queryString)
                .getResultList();
        return this;
    }

    public InMemoryReader maxSize(long maxSize) {
        this.maxSize = maxSize;
        return this;
    }

    public long getSize(long maxSize) {
        return list.size();
    }

    @Override
    public T read() throws Exception {
        T nextItem = null;
        if (nextIndex < maxSize && nextIndex < list.size()) {
            nextItem = (T) list.get(nextIndex);
            nextIndex++;
        }
        return nextItem;
    }
}
