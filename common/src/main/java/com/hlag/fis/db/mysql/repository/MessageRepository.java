package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.Message;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "Message")
public interface MessageRepository extends PagingAndSortingRepository<Message, String> {

    @Cacheable
    Optional<Message> findByClientAndRelativeNumber(String client, Integer relativeNumber);
}
