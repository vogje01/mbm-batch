package com.momentum.batch.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.retry.RetryContext;
import org.springframework.retry.policy.RetryContextCache;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RetryContextGuavaCache implements RetryContextCache {

    private Cache<Object, RetryContext> cache;

    public RetryContextGuavaCache() {
        cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(60, TimeUnit.SECONDS)
                .build();
    }

    @Override
    public RetryContext get(Object o) {
        return (RetryContext) cache.getIfPresent(o.hashCode());
    }

    @Override
    public void put(Object o, RetryContext retryContext) {
        cache.put(retryContext.hashCode(), retryContext);
    }

    @Override
    public void remove(Object o) {
        cache.invalidate(o);
    }

    @Override
    public boolean containsKey(Object o) {
        return cache.getIfPresent(o) != null;
    }
}
