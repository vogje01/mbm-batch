package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.RoleFunctionalUnitIdOld;
import com.hlag.fis.db.db2.model.RoleFunctionalUnitOld;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleFunctionalUnitOldRepository extends CrudRepository<RoleFunctionalUnitOld, RoleFunctionalUnitIdOld> {

	@Query("select r from RoleFunctionalUnitOld r where r.id.roleEnvironment = :roleEnvironment and r.id.roleIdentifier = :roleIdentifier")
	List<RoleFunctionalUnitOld> findByEnvironmentAndIdentifier(@Param("roleEnvironment")String roleEnvironment, @Param("roleIdentifier")Short roleIdentifier);
}
