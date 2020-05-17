package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.GeoHierarchy;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "GeoHierarchy")
public interface GeoHierarchyRepository extends CrudRepository<GeoHierarchy, String> {

	@Cacheable
	@Query("select g from GeoHierarchy g where g.client = :client and g.geoRegionId = :geoRegionId and g.geoSubRegionId = :geoSubRegionId and g.geoAreaId = :geoAreaId and g.geoSubAreaId = :geoSubAreaId and g.geoDistrictId = :geoDistrictId")
    Optional<GeoHierarchy> findGeoHierarchy(@Param("client") String client, @Param("geoRegionId") String geoRegionId, @Param("geoSubRegionId") String geoSubRegionId, @Param("geoAreaId") String geoAreaId, @Param("geoSubAreaId") String geoSubAreaId, @Param("geoDistrictId") String geoDistrictId);

	@Cacheable
	@Query("select g from GeoHierarchy g where g.client = :client and g.geoRegionId = :geoRegionId and g.geoSubRegionId is null and g.geoAreaId is null and g.geoSubAreaId is null and g.geoDistrictId is null")
	GeoHierarchy findGeoHierarchyByRegion(@Param("client") String client, @Param("geoRegionId") String geoRegionId);

	@Cacheable
	@Query("select g from GeoHierarchy g where g.client = :client and g.geoRegionId = :geoRegionId and g.geoSubRegionId = :geoSubRegionId and g.geoAreaId  is null and g.geoSubAreaId is null and g.geoDistrictId is null")
	GeoHierarchy findGeoHierarchyBySubregion(@Param("client") String client, @Param("geoRegionId") String geoRegionId, @Param("geoSubRegionId") String geoSubRegionId);

	@Cacheable
	@Query("select g from GeoHierarchy g where g.client = :client and g.geoRegionId = :geoRegionId and g.geoSubRegionId = :geoSubRegionId and g.geoAreaId = :geoAreaId and g.geoSubAreaId is null and g.geoDistrictId is null")
	GeoHierarchy findGeoHierarchyByArea(@Param("client") String client, @Param("geoRegionId") String geoRegionId, @Param("geoSubRegionId") String geoSubRegionId, @Param("geoAreaId") String geoAreaId);
}
