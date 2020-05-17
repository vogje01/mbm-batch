package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.TransportUnitPoint;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "TransportUnitPoint")
public interface TransportUnitPointRepository extends CrudRepository<TransportUnitPoint, String> {

    @Cacheable
    Optional<TransportUnitPoint> findById(String id);

    @Cacheable
    @Query("select t from TransportUnitPoint t where t.client = :client and t.number = :number and t.relativeNumber = :relativeNumber")
    Optional<TransportUnitPoint> findByClientAndShipmentNumberAndRelativeNumber(@Param("client") String client, @Param("number") Long number, @Param("relativeNumber") Integer relativeNumber);

    @Cacheable
    @Query("select t from TransportUnitPoint t where t.client = :client and t.number = :number")
    List<TransportUnitPoint> findByClientAndShipmentNumber(@Param("client") String client, @Param("number") Long number);
}
