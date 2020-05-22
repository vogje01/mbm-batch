package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.JobGroup;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobGroupService {

    Page<JobGroup> allJobGroups(Pageable pageable);

    long countAll();

    JobGroup getJobGroup(final String id);

    JobGroup insertJobGroup(JobGroup jobGroup);

    JobGroup updateJobGroup(final String jobGroupId,
                            JobGroup jobGroup) throws ResourceNotFoundException;

    @CacheEvict
    void deleteJobGroup(final String id);
}
