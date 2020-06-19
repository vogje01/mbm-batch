package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.dto.JobGroupDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.database.repository.JobGroupRepository;
import com.momentum.batch.server.manager.converter.JobGroupModelAssembler;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@Transactional
public class JobGroupServiceImpl implements JobGroupService {

    private static final Logger logger = LoggerFactory.getLogger(JobGroupServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final JobGroupRepository jobGroupRepository;

    private final JobDefinitionRepository jobDefinitionRepository;

    private final PagedResourcesAssembler<JobGroup> jobGroupPagedResourcesAssembler;

    private final JobGroupModelAssembler jobGroupModelAssembler;

    @Autowired
    public JobGroupServiceImpl(JobGroupRepository jobGroupRepository, JobDefinitionRepository jobDefinitionRepository,
                               PagedResourcesAssembler<JobGroup> jobGroupPagedResourcesAssembler, JobGroupModelAssembler jobGroupModelAssembler) {
        this.jobGroupRepository = jobGroupRepository;
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.jobGroupPagedResourcesAssembler = jobGroupPagedResourcesAssembler;
        this.jobGroupModelAssembler = jobGroupModelAssembler;
    }

    /**
     * Returns a paged list of all job groups.
     *
     * @param pageable paging parameters.
     * @return one page of job definitions.
     */
    @Override
    public PagedModel<JobGroupDto> findAll(Pageable pageable) {

        t.restart();

        Page<JobGroup> jobGroups = jobGroupRepository.findAll(pageable);

        PagedModel<JobGroupDto> collectionModel = jobGroupPagedResourcesAssembler.toModel(jobGroups, jobGroupModelAssembler);
        logger.debug(format("Job group list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    /**
     * Returns a paged list of job groups belonging to a job definition.
     *
     * @param jobDefinitionId job definition ID.
     * @param pageable        paging parameters.
     * @return one page og job groups belonging to a job definition.
     */
    @Override
    public PagedModel<JobGroupDto> findByJobDefinition(String jobDefinitionId, Pageable pageable) {

        t.restart();

        Page<JobGroup> jobGroups = jobGroupRepository.findByJobDefinition(jobDefinitionId, pageable);

        PagedModel<JobGroupDto> collectionModel = jobGroupPagedResourcesAssembler.toModel(jobGroups, jobGroupModelAssembler);
        logger.debug(format("Job group list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    public JobGroupDto findById(final String jobGroupId) throws ResourceNotFoundException {
        Optional<JobGroup> jobGroupOptional = jobGroupRepository.findById(jobGroupId);
        if (jobGroupOptional.isPresent()) {
            return jobGroupModelAssembler.toModel(jobGroupOptional.get());
        }
        throw new ResourceNotFoundException("Job group not found");
    }

    @Override
    public JobGroupDto findByName(final String jobGroupName) throws ResourceNotFoundException {
        Optional<JobGroup> jobGroupOptional = jobGroupRepository.findByName(jobGroupName);
        if (jobGroupOptional.isPresent()) {
            return jobGroupModelAssembler.toModel(jobGroupOptional.get());
        }
        throw new ResourceNotFoundException("Job group not found");
    }

    /**
     * Insert a new job definition.
     *
     * @param jobGroupDto job definition entity.
     * @return inserted job definition
     */
    @Override
    public JobGroupDto insertJobGroup(JobGroupDto jobGroupDto) {
        t.restart();

        JobGroup jobGroup = jobGroupModelAssembler.toEntity(jobGroupDto);
        jobGroup = jobGroupRepository.save(jobGroup);
        jobGroupDto = jobGroupModelAssembler.toModel(jobGroup);
        logger.debug(format("Job group update request finished - id: {0} [{1}]", jobGroup.getId(), t.elapsedStr()));

        return jobGroupDto;
    }

    @Override
    public JobGroupDto updateJobGroup(final String jobGroupId, JobGroupDto jobGroupDto) throws ResourceNotFoundException {
        t.restart();

        // Get job group
        JobGroup jobGroup = jobGroupModelAssembler.toEntity(jobGroupDto);

        Optional<JobGroup> jobGroupOld = jobGroupRepository.findById(jobGroupId);
        if (jobGroupOld.isPresent()) {

            // Update job definition
            JobGroup jobGroupNew = jobGroupOld.get();
            jobGroupNew.update(jobGroup);

            // Save new job definition
            jobGroupNew = jobGroupRepository.save(jobGroupNew);
            jobGroupDto = jobGroupModelAssembler.toModel(jobGroupNew);
            logger.debug(format("Job group update request finished - id: {0} [{1}]", jobGroup.getId(), t.elapsedStr()));

            return jobGroupDto;
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public void deleteJobGroup(final String jobGroupId) {
        Optional<JobGroup> jobGroupInfo = jobGroupRepository.findById(jobGroupId);
        jobGroupInfo.ifPresent(jobGroup -> jobGroupRepository.delete(jobGroup));
    }

    /**
     * Adds a jobDefinition to an job group.
     *
     * @param jobGroupId job group ID.
     * @param id         job definition id to add.
     */
    @Override
    @CachePut(cacheNames = "JobGroup", key = "#jobGroupId")
    public JobGroupDto addJobDefinition(String jobGroupId, String id) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(id);
        Optional<JobGroup> jobGroupOptional = jobGroupRepository.findById(jobGroupId);
        if (jobDefinitionOptional.isPresent() && jobGroupOptional.isPresent()) {
            JobDefinition jobDefinition = jobDefinitionOptional.get();
            JobGroup jobGroup = jobGroupOptional.get();
            jobDefinitionRepository.save(jobDefinition);
            return jobGroupModelAssembler.toModel(jobGroup);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Removes a jobDefinition from an jobDefinition group.
     *
     * @param jobGroupId jobDefinition group ID.
     * @param id         jobDefinition ID to remove.
     */
    @Override
    @CachePut(cacheNames = "JobGroup", key = "#jobGroupId")
    public JobGroupDto removeJobDefinition(String jobGroupId, String id) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(id);
        Optional<JobGroup> jobGroupOptional = jobGroupRepository.findById(jobGroupId);
        if (jobDefinitionOptional.isPresent() && jobGroupOptional.isPresent()) {
            JobDefinition jobDefinition = jobDefinitionOptional.get();
            JobGroup jobGroup = jobGroupOptional.get();
            jobDefinitionRepository.save(jobDefinition);
            return jobGroupModelAssembler.toModel(jobGroup);
        }
        throw new ResourceNotFoundException();
    }
}
