package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.Location;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "Location")
public interface LocationRepository extends CrudRepository<Location, String> {

    @Cacheable
    Optional<Location> findByClientAndNumber(String client, Integer number);

	@Cacheable
	Location findByClientAndBusinessLocation(String client, String location);

	@Cacheable
	List<Location> findByClientAndBusinessLocode(String client, String locode);
}
