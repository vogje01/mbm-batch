package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.PasswordResetToken;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Repository
public interface PasswordResetTokenRepository extends PagingAndSortingRepository<PasswordResetToken, String> {

    PasswordResetToken findByToken(String token);

}
