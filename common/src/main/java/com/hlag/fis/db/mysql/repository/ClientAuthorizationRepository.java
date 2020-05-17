package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.ClientAuthorization;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "ClientAuthorization")
public interface ClientAuthorizationRepository extends CrudRepository<ClientAuthorization, String> {

    @Cacheable
    @Query("select c from ClientAuthorization c where c.idCode = :idCode and c.historyFrom = :historyFrom and c.users.userId = :userId and c.users.historyFrom = :userHistoryFrom")
    Optional<ClientAuthorization> findByIdCodeAndHistoryFromAndUserIdAndUserHistoryFrom(@Param("idCode") String idCode, @Param("historyFrom") Long historyFrom, @Param("userId") String userId, @Param("userHistoryFrom") Long userHistoryFrom);

    @Cacheable
    @Query("select c from ClientAuthorization c where c.idCode = :idCode and c.historyFrom = :historyFrom " +
            "and c.users.userId = :userId and c.users.historyFrom = :userHistoryFrom " +
            "and c.securityOrganization.client = :secuOrgClient " +
            "and c.securityOrganization.identifier = :secuOrgIdentifer")
    Optional<ClientAuthorization> findByFullId(@Param("idCode") String idCode,
                                               @Param("historyFrom") Long historyFrom,
                                               @Param("userId") String userId,
                                               @Param("userHistoryFrom") Long userHistoryFrom,
                                               @Param("secuOrgClient") String secuOrgClient,
                                               @Param("secuOrgIdentifer") String secuOrgIdentifer);
}
