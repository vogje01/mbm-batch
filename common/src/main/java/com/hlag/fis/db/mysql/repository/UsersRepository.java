package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.Users;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "Users")
public interface UsersRepository extends CrudRepository<Users, String> {

	@Cacheable
	Optional<Users> findByUserIdAndHistoryFrom(String userId, Long historyFrom);

	@Cacheable
	Optional<Users> findByUserId(String userId);
}
