package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.MessageIdOld;
import com.hlag.fis.db.db2.model.MessageOld;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable(cacheNames = "Message")
public interface MessageOldRepository extends CrudRepository<MessageOld, MessageIdOld> {

    @Query("select count(m) from MessageOld m where m.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") long cutOff);
}
