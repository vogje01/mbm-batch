package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.UserAuthorization;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
@CacheConfig(cacheNames = "UserAuthorization")
public interface UserAuthorizationRepository extends CrudRepository<UserAuthorization, String> {

	@SuppressWarnings("squid:S00107")
	@Cacheable
	@Query("select u from UserAuthorization u where u.validFrom = :validFrom and u.businessTaskId = :businessTaskId and u.users.userId = :userId and u.users.historyFrom = :historyFrom and " +
			" u.clientRole.client = :clientRoleClient and u.clientRole.relativeNumber = :clientRoleRelativeNumber and u.userRole.environment = :environment and u.userRole.identifier = :identifier")
	UserAuthorization findOldUserAuthorization(@Param("validFrom") LocalDate validFrom, @Param("businessTaskId") Integer businessTaskId, @Param("userId") String userId, @Param("historyFrom") Long historyFrom,
											   @Param("clientRoleClient") String clientRoleClient, @Param("clientRoleRelativeNumber") Short clientRoleRelativeNumber, @Param("environment") String roleEnvironment,
											   @Param("identifier") Short identifier);
}
