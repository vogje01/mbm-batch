package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.UserPermission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPermissionRepository extends CrudRepository<UserPermission, String> {

	Optional<UserPermission> findByUserId(String userId);
}
