package com.momentum.batch.server.database.converter;

import com.momentum.batch.common.domain.DateTimeFormat;
import com.momentum.batch.common.domain.NumberFormat;
import com.momentum.batch.common.domain.dto.*;
import com.momentum.batch.server.database.domain.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
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
        jobExecutionDto.setJobName(jobExecutionInfo.getJobExecutionInstance().getJobName());
        jobExecutionDto.setJobExecutionParamDtoes(jobExecutionInfo.getJobExecutionParams()
                .stream()
                .map(this::convertJobExecutionParamToDto)
                .collect(toList()));
        return jobExecutionDto;
    }

    public List<JobExecutionDto> convertJobExecutionToDto(List<JobExecutionInfo> jobExecutionList, long totalCount) {
        return jobExecutionList.stream().map(j -> convertJobExecutionToDto(j, totalCount)).collect(toList());
    }

    public JobExecutionDto convertJobExecutionToDto(JobExecutionInfo jobExecutionInfo, long totalCount) {
        JobExecutionDto jobExecutionDto = modelMapper.map(jobExecutionInfo, JobExecutionDto.class);
        jobExecutionDto.setJobName(jobExecutionInfo.getJobExecutionInstance().getJobName());
        jobExecutionDto.setTotalSize(totalCount);
        jobExecutionDto.setJobExecutionContextDto(convertJobExecutionContextToDto(jobExecutionInfo.getJobExecutionContext()));
        jobExecutionDto.setJobInstanceDto(convertJobInstanceToDto(jobExecutionInfo.getJobExecutionInstance()));
        jobExecutionDto.setJobExecutionParamDtoes(jobExecutionInfo.getJobExecutionParams()
                .stream()
                .map(this::convertJobExecutionParamToDto)
                .collect(toList()));
        return jobExecutionDto;
    }

    public JobExecutionParamDto convertJobExecutionParamToDto(JobExecutionParam jobExecutionParam) {
        return modelMapper.map(jobExecutionParam, JobExecutionParamDto.class);
    }

    public JobExecutionParamDto convertJobExecutionParamToDto(JobExecutionParam jobExecutionParam, long totalCount) {
        JobExecutionParamDto jobExecutionParamDto = modelMapper.map(jobExecutionParam, JobExecutionParamDto.class);
        jobExecutionParamDto.setTotalCount(totalCount);
        return jobExecutionParamDto;
    }

    public List<JobExecutionParamDto> convertJobExecutionParamToDto(List<JobExecutionParam> jobExecutionParamList, long totalCount) {
        return jobExecutionParamList.stream().map(j -> convertJobExecutionParamToDto(j, totalCount)).collect(toList());
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
        jobExecutionInfo.setJobExecutionInstance(convertJobInstanceToEntity(jobExecutionDto.getJobInstanceDto()));
        List<JobExecutionParam> jobExecutionParams = jobExecutionDto.getJobExecutionParamDtoes()
                .stream()
                .map(this::convertJobExecutionParamToEntity)
                .collect(toList());
        jobExecutionInfo.setJobExecutionParams(jobExecutionParams);
        return jobExecutionInfo;
    }

    public JobExecutionLogDto convertJobExecutionLogToDto(JobExecutionLog jobExecutionLog, long totalCount) {
        JobExecutionLogDto jobExecutionLogDto = modelMapper.map(jobExecutionLog, JobExecutionLogDto.class);
        if (jobExecutionLog.getTimestamp() != null)
            jobExecutionLogDto.setTimestamp(jobExecutionLog.getTimestamp());
        return jobExecutionLogDto;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job instance
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public JobInstanceDto convertJobInstanceToDto(JobExecutionInstance jobExecutionInstance) {
        JobInstanceDto jobInstanceDto = modelMapper.map(jobExecutionInstance, JobInstanceDto.class);
        jobInstanceDto.setId(null);
        return jobInstanceDto;
    }

    public JobExecutionInstance convertJobInstanceToEntity(JobInstanceDto jobInstanceDto) {
        return modelMapper.map(jobInstanceDto, JobExecutionInstance.class);
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

    public JobExecutionContextDto convertJobExecutionContextToDto(JobExecutionContext jobExecutionContext) {
        if (jobExecutionContext != null) {
            return modelMapper.map(jobExecutionContext, JobExecutionContextDto.class);
        }
        return null;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Step executions
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public StepExecutionDto convertStepExecutionToDto(StepExecutionInfo stepExecutionInfo) {
        StepExecutionDto stepExecutionDto = modelMapper.map(stepExecutionInfo, StepExecutionDto.class);
        stepExecutionDto.setJobName(stepExecutionInfo.getJobExecutionInfo().getJobExecutionInstance().getJobName());
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

    public List<JobScheduleDto> convertJobScheduleToDto(List<JobSchedule> jobScheduleList, long totalCount) {
        return jobScheduleList.stream().map(s -> convertJobScheduleToDto(s, totalCount)).collect(toList());
    }

    public JobScheduleDto convertJobScheduleToDto(JobSchedule jobSchedule, long totalCount) {
        JobScheduleDto jobScheduleDto = modelMapper.map(jobSchedule, JobScheduleDto.class);
        if (jobSchedule.getJobDefinition() != null) {
            jobScheduleDto.setJobDefinitionDto(convertJobDefinitionToDto(jobSchedule.getJobDefinition()));
            jobScheduleDto.setJobDefinitionName(jobSchedule.getJobDefinition().getName());
        }
        jobScheduleDto.setTotalSize(totalCount);
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
        //jobDefinition.getJobGroups().forEach(g -> g.setJobDefinitions(null));
        /*jobDefinitionDto.setJobGroupDtoes(jobDefinition.getJobGroups()
                .stream()
                .map(this::convertJobGroupToDto)
                .collect(toList()));*/

        if (jobDefinition.getJobGroup() != null) {
            jobDefinitionDto.setJobGroupDto(modelMapper.map(jobDefinition.getJobGroup(), JobGroupDto.class));
            jobDefinitionDto.setJobGroupName(jobDefinition.getJobGroup().getName());
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

    public JobDefinitionDto convertJobDefinitionToDto(JobDefinition jobDefinition, long totalCount) {
        JobDefinitionDto jobDefinitionDto = convertJobDefinitionToDto(jobDefinition);
        jobDefinitionDto.setTotalSize(totalCount);
        return jobDefinitionDto;
    }

    public List<JobDefinitionDto> convertJobDefinitionToDto(List<JobDefinition> jobDefinitions, long totalCount) {
        return jobDefinitions.stream().map(j -> convertJobDefinitionToDto(j, totalCount)).collect(toList());
    }

    public JobDefinitionParamDto convertJobDefinitionParamToDto(JobDefinitionParam jobDefinitionParam) {
        return modelMapper.map(jobDefinitionParam, JobDefinitionParamDto.class);
    }

    public JobDefinitionParamDto convertJobDefinitionParamToDto(JobDefinitionParam jobDefinitionParam, long totalCount) {
        JobDefinitionParamDto jobDefinitionParamDto = convertJobDefinitionParamToDto(jobDefinitionParam);
        jobDefinitionParamDto.setTotalSize(totalCount);
        return jobDefinitionParamDto;
    }

    public List<JobDefinitionParamDto> convertJobDefinitionParamToDto(List<JobDefinitionParam> jobDefinitionParams, long totalCount) {
        return jobDefinitionParams.stream().map(j -> convertJobDefinitionParamToDto(j, totalCount)).collect(toList());
    }

    public JobDefinition convertJobDefinitionToEntity(JobDefinitionDto jobDefinitionDto) {
        JobDefinition jobDefinition = modelMapper.map(jobDefinitionDto, JobDefinition.class);
        jobDefinition.setJobGroup(convertJobGroupToEntity(jobDefinitionDto.getJobGroupDto()));
//        jobDefinition.setJobGroups(convertJobGroupToEntity(jobDefinitionDto.getJobGroupDtoes()));

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

    public JobGroupDto convertJobGroupToDto(JobGroup jobGroup, long totalCount) {
        JobGroupDto jobGroupDto = convertJobGroupToDto(jobGroup);
        jobGroupDto.setTotalSize(totalCount);
        return jobGroupDto;
    }

    public List<JobGroupDto> convertJobGroupToDto(List<JobGroup> jobGroups, long totalCount) {
        return jobGroups.stream().map(j -> convertJobGroupToDto(j, totalCount)).collect(toList());
    }

    public JobGroup convertJobGroupToEntity(JobGroupDto jobGroupDto) {
        if (jobGroupDto != null) {
            return modelMapper.map(jobGroupDto, JobGroup.class);
        }
        return null;
    }

    public List<JobGroup> convertJobGroupToEntity(List<JobGroupDto> jobGroupDtoes) {
        return jobGroupDtoes.stream().map(this::convertJobGroupToEntity).collect(toList());
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

    public List<UserDto> convertUserToDto(List<User> userList, long totalCount) {
        return userList.stream().map(a -> convertUserToDto(a, totalCount)).collect(toList());
    }

    public UserDto convertUserToDto(User user, long totalCount) {
        UserDto userDto = convertUserToDto(user);
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

    public List<UserGroupDto> convertUserGroupToDto(List<UserGroup> userGroupList, long totalCount) {
        return userGroupList.stream().map(a -> convertUserGroupToDto(a, totalCount)).collect(toList());
    }

    public UserGroupDto convertUserGroupToDto(UserGroup userGroup, long totalCount) {
        UserGroupDto userGroupDto = convertUserGroupToDto(userGroup);
        return userGroupDto;
    }

    public UserGroup convertUserGroupToEntity(UserGroupDto userGroupDto) {
        return modelMapper.map(userGroupDto, UserGroup.class);
    }
}
