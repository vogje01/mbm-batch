package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.Client;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "Client")
public interface ClientRepository extends CrudRepository<Client, String> {

    Optional<Client> findByIdCode(String idCode);
}
