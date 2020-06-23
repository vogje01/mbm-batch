package com.momentum.batch.server.database.converter;

import com.momentum.batch.server.database.domain.*;
import com.momentum.batch.server.database.domain.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Model converter.
 * <p>
 * Converts the entity to data transfer objects. Data transfer objects are normally more flat structures of the
 * corresponding entities.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Component
public class ModelConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public ModelConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job executions
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public JobExecutionDto convertJobExecutionToDto(JobExecutionInfo jobExecutionInfo) {
        JobExecutionDto jobExecutionDto = modelMapper.map(jobExecutionInfo, JobExecutionDto.class);
        jobExecutionDto.setJobExecutionParamDtoes(jobExecutionInfo.getJobExecutionParams()
                .stream()
                .map(this::convertJobExecutionParamToDto)
                .collect(toList()));
        return jobExecutionDto;
    }

    public JobExecutionParamDto convertJobExecutionParamToDto(JobExecutionParam jobExecutionParam) {
        return modelMapper.map(jobExecutionParam, JobExecutionParamDto.class);
    }

    public JobExecutionParam convertJobExecutionParamToEntity(JobExecutionParamDto jobExecutionParamDto) {
        JobExecutionParam jobExecutionParam = modelMapper.map(jobExecutionParamDto, JobExecutionParam.class);
        switch (jobExecutionParamDto.getTypeCd()) {
            case "STRING" -> jobExecutionParam.setStringVal(jobExecutionParamDto.getStringVal());
            case "DATE" -> jobExecutionParam.setDateVal(jobExecutionParamDto.getDateVal());
            case "LONG" -> jobExecutionParam.setLongVal(jobExecutionParamDto.getLongVal());
            case "DOUBLE" -> jobExecutionParam.setDoubleVal(jobExecutionParamDto.getDoubleVal());
        }
        return jobExecutionParam;

    }

    public JobExecutionInfo convertJobExecutionToEntity(JobExecutionDto jobExecutionDto) {
        JobExecutionInfo jobExecutionInfo = modelMapper.map(jobExecutionDto, JobExecutionInfo.class);
        List<JobExecutionParam> jobExecutionParams = jobExecutionDto.getJobExecutionParamDtoes()
                .stream()
                .map(this::convertJobExecutionParamToEntity)
                .collect(toList());
        jobExecutionInfo.setJobExecutionParams(jobExecutionParams);
        return jobExecutionInfo;
    }

    public JobExecutionLogDto convertJobExecutionLogToDto(JobExecutionLog jobExecutionLog) {
        return modelMapper.map(jobExecutionLog, JobExecutionLogDto.class);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job context
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public JobExecutionContext convertJobExecutionContextToEntity(JobExecutionContextDto jobExecutionContextDto) {
        if (jobExecutionContextDto != null) {
            return modelMapper.map(jobExecutionContextDto, JobExecutionContext.class);
        }
        return null;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Step executions
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public StepExecutionDto convertStepExecutionToDto(StepExecutionInfo stepExecutionInfo) {
        StepExecutionDto stepExecutionDto = modelMapper.map(stepExecutionInfo, StepExecutionDto.class);
        stepExecutionDto.setJobName(stepExecutionInfo.getJobExecutionInfo().getJobName());
        return stepExecutionDto;
    }

    public StepExecutionInfo convertStepExecutionToEntity(StepExecutionDto stepExecutionDto) {
        return modelMapper.map(stepExecutionDto, StepExecutionInfo.class);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Step context
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public StepExecutionContext convertStepExecutionContextToEntity(StepExecutionContextDto stepExecutionContextDto) {
        if (stepExecutionContextDto != null) {
            return modelMapper.map(stepExecutionContextDto, StepExecutionContext.class);
        }
        return null;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job schedules
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public JobScheduleDto convertJobScheduleToDto(JobSchedule jobSchedule) {
        JobScheduleDto jobScheduleDto = modelMapper.map(jobSchedule, JobScheduleDto.class);
        if (jobSchedule.getJobDefinition() != null) {
            jobScheduleDto.setJobDefinitionDto(convertJobDefinitionToDto(jobSchedule.getJobDefinition()));
            jobScheduleDto.setJobDefinitionName(jobSchedule.getJobDefinition().getName());
        }
        return jobScheduleDto;
    }

    public JobSchedule convertJobScheduleToEntity(JobScheduleDto jobScheduleDto) {
        JobSchedule jobSchedule = modelMapper.map(jobScheduleDto, JobSchedule.class);
        if (jobScheduleDto.getJobDefinitionDto() != null) {
            jobSchedule.setJobDefinition(convertJobDefinitionToEntity(jobScheduleDto.getJobDefinitionDto()));
        }
        return jobSchedule;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job definitions
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public JobDefinitionDto convertJobDefinitionToDto(JobDefinition jobDefinition) {

        // Convert job definition
        JobDefinitionDto jobDefinitionDto = modelMapper.map(jobDefinition, JobDefinitionDto.class);
        jobDefinitionDto.setJobGroupDtoes(jobDefinition.getJobGroups()
                .stream()
                .map(this::convertJobGroupToDto)
                .collect(toList()));

        // Add main job group
        if (jobDefinition.getJobMainGroup() != null) {
            jobDefinitionDto.setJobGroupName(jobDefinition.getJobMainGroup().getName());
            jobDefinitionDto.setJobMainGroupDto(convertJobGroupToDto(jobDefinition.getJobMainGroup()));
        }

        // Add parameter
        if (!jobDefinitionDto.getJobDefinitionParamDtos().isEmpty()) {
            jobDefinitionDto.setJobDefinitionParamDtos(jobDefinition.getJobDefinitionParams()
                    .stream()
                    .map(this::convertJobDefinitionParamToDto)
                    .collect(toList()));
        }
        return jobDefinitionDto;
    }

    public JobDefinitionParamDto convertJobDefinitionParamToDto(JobDefinitionParam jobDefinitionParam) {
        return modelMapper.map(jobDefinitionParam, JobDefinitionParamDto.class);
    }

    public JobDefinition convertJobDefinitionToEntity(JobDefinitionDto jobDefinitionDto) {
        JobDefinition jobDefinition = modelMapper.map(jobDefinitionDto, JobDefinition.class);
        jobDefinition.setJobGroups(jobDefinitionDto.getJobGroupDtoes()
                .stream()
                .map(this::convertJobGroupToEntity)
                .collect(toList()));

        // Add main job group
        if (jobDefinitionDto.getJobMainGroupDto() != null) {
            jobDefinition.setJobMainGroup(modelMapper.map(jobDefinitionDto.getJobMainGroupDto(), JobGroup.class));
        }

        if (!jobDefinition.getJobDefinitionParams().isEmpty()) {
            jobDefinition.setJobDefinitionParams(jobDefinitionDto.getJobDefinitionParamDtos()
                    .stream()
                    .map(this::convertJobDefinitionParamToEntity)
                    .collect(toList()));
        }
        return jobDefinition;
    }

    public JobDefinitionParam convertJobDefinitionParamToEntity(JobDefinitionParamDto jobDefinitionParamDto) {
        return modelMapper.map(jobDefinitionParamDto, JobDefinitionParam.class);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job groups
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public JobGroupDto convertJobGroupToDto(JobGroup jobGroup) {
        return modelMapper.map(jobGroup, JobGroupDto.class);
    }

    public JobGroup convertJobGroupToEntity(JobGroupDto jobGroupDto) {
        return modelMapper.map(jobGroupDto, JobGroup.class);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Agent
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public AgentDto convertAgentToDto(Agent agent) {
        return modelMapper.map(agent, AgentDto.class);
    }

    public Agent convertAgentToEntity(AgentDto agentDto) {
        return modelMapper.map(agentDto, Agent.class);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Agent groups
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public AgentGroupDto convertAgentGroupToDto(AgentGroup agentGroup) {
        return modelMapper.map(agentGroup, AgentGroupDto.class);
    }

    public AgentGroup convertAgentGroupToEntity(AgentGroupDto agentGroupDto) {
        return modelMapper.map(agentGroupDto, AgentGroup.class);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Batch performance
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<BatchPerformanceDto> convertBatchPerformanceToDto(List<BatchPerformance> batchPerformanceList) {
        return batchPerformanceList.stream().map(this::convertBatchPerformanceToDto).collect(toList());
    }

    public BatchPerformanceDto convertBatchPerformanceToDto(BatchPerformance batchPerformance) {
        return modelMapper.map(batchPerformance, BatchPerformanceDto.class);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // User
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public UserDto convertUserToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);

        // Add user groups
        user.getUserGroups().forEach(g -> g.setUsers(null));
        userDto.setUserGroupDtoes(user.getUserGroups()
                .stream()
                .map(this::convertUserGroupToDto)
                .collect(toList()));
        return userDto;
    }

    public User convertUserToEntity(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setDateTimeFormat(DateTimeFormat.valueOf(userDto.getDateTimeFormat()));
        user.setNumberFormat(NumberFormat.valueOf(userDto.getNumberFormat()));

        // Convert user groups
        List<UserGroup> userGroups = userDto.getUserGroupDtoes()
                .stream()
                .map(this::convertUserGroupToEntity)
                .collect(toList());
        user.setUserGroups(userGroups);

        return user;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // User group
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public UserGroupDto convertUserGroupToDto(UserGroup userGroup) {
        return modelMapper.map(userGroup, UserGroupDto.class);
    }

    public UserGroup convertUserGroupToEntity(UserGroupDto userGroupDto) {
        return modelMapper.map(userGroupDto, UserGroup.class);
    }
}
