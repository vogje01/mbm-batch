package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.User;
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

    @Query("select u from User u where u.email = :email and u.active = true")
    Optional<User> findByEmail(@Param("email") String email);

    @Query("select u from User u left join u.userGroups ug where ug.id = :id and u.active = true")
    Page<User> findByUserGroup(@Param("id") String id, Pageable pageable);

    @Query("select count(u) from User u left join u.userGroups ug where ug.id = :id and u.active = true")
    long countByUserGroup(@Param("id") String id);

    @Query("select u from User u where u.userId = :userId and u.password = :password and u.active = true")
    Optional<User> findByUserIdAndPasswordAndActive(@Param("userId") String userId, @Param("password") String password);

    @Query("select u from User u join u.userGroups ug where ug.id <> :userGroupId")
    Page<User> findWithoutUserGroup(@Param("userGroupId") String userGroupId, Pageable pageable);
}
