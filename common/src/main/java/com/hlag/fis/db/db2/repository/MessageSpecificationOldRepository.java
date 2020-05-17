package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.MessageSpecificationIdOld;
import com.hlag.fis.db.db2.model.MessageSpecificationOld;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable(cacheNames = "MessageSpecification")
public interface MessageSpecificationOldRepository extends CrudRepository<MessageSpecificationOld, MessageSpecificationIdOld> {

    @Query("select count(m) from MessageSpecificationOld m where m.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") long cutOff);
}
