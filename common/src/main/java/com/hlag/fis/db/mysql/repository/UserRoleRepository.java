package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.UserRole;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "UserRole")
public interface UserRoleRepository extends CrudRepository<UserRole, String> {

	/**
	 * Returns a user role by environment and identifier.
	 *
	 * @param environment user role environment.
	 * @param identifier  user role identifier.
	 * @return user role with the specified environment and identifier.
	 */
	@Cacheable
	Optional<UserRole> findByEnvironmentAndIdentifier(String environment, Short identifier);
}
