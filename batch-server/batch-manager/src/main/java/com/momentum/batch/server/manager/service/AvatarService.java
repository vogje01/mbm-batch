package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.dto.UserDto;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public interface AvatarService {

    byte[] download(String id) throws ResourceNotFoundException;

    UserDto upload(String userId, MultipartFile file) throws ResourceNotFoundException;
}
