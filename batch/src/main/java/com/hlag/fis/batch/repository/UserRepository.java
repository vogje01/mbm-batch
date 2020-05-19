package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    @Query("select u from User u where u.active = :active")
    Page<User> findAllActive(Pageable pageable, @Param("active") boolean active);

    @Query("select u from User u where u.userId = :userId and u.active = true")
    Optional<User> findByUserId(@Param("userId") String userId);

    @Query("select a from User a where a.userId = :userId and a.password = :password and a.active = true")
    Optional<User> findByUserIdAndPasswordAndActive(@Param("userId") String userId, @Param("password") String password);
}
