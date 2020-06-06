package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.database.repository.JobGroupRepository;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@CacheConfig(cacheNames = "JobGroup")
public class JobGroupServiceImpl implements JobGroupService {

    private static final Logger logger = LoggerFactory.getLogger(JobGroupServiceImpl.class);

    private JobGroupRepository jobGroupRepository;

    @Autowired
    public JobGroupServiceImpl(JobGroupRepository jobGroupRepository,
                               JobScheduleService jobScheduleService) {
        this.jobGroupRepository = jobGroupRepository;
    }

    @Override
    @Cacheable
    public Page<JobGroup> allJobGroups(Pageable pageable) {
        return jobGroupRepository.findAll(pageable);
    }

    @Override
    public long countAll() {
        return jobGroupRepository.count();
    }

    /**
     * Insert a new job definition.
     *
     * @param jobGroup job definition entity.
     * @return inserted job definition
     */
    @Override
    @Transactional
    public JobGroup insertJobGroup(JobGroup jobGroup) {
        jobGroup = jobGroupRepository.save(jobGroup);
        return jobGroup;
    }

    @Override
    @Transactional
    @Cacheable
    public JobGroup updateJobGroup(final String jobGroupId, JobGroup jobGroup) throws ResourceNotFoundException {
        Optional<JobGroup> jobGroupOld = jobGroupRepository.findById(jobGroupId);
        if (jobGroupOld.isPresent()) {

            // Update job definition
            JobGroup jobGroupNew = jobGroupOld.get();
            jobGroupNew.update(jobGroup);

            // Save new job definition
            jobGroupNew = jobGroupRepository.save(jobGroupNew);

            return jobGroupNew;
        } else {
            logger.error(format("Job definition not found - id: {0}", jobGroupId));
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @CacheEvict
    public void deleteJobGroup(final String jobGroupId) {
        Optional<JobGroup> jobGroupInfo = jobGroupRepository.findById(jobGroupId);
        jobGroupInfo.ifPresent(jobGroup -> jobGroupRepository.delete(jobGroup));
    }

    @Override
    @Cacheable
    public JobGroup getJobGroup(final String jobGroupId) {
        Optional<JobGroup> jobGroup = jobGroupRepository.findById(jobGroupId);
        return jobGroup.orElse(null);
    }

    @Override
    @Cacheable
    public JobGroup getJobGroupByName(final String jobGroupName) {
        Optional<JobGroup> jobGroup = jobGroupRepository.findByName(jobGroupName);
        return jobGroup.orElse(null);
    }
}
