package com.hlag.fis.batch.util;

import com.hlag.fis.batch.domain.*;
import com.hlag.fis.batch.domain.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class ModelConverter {

    private SimpleDateFormat parameterDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private ModelMapper modelMapper;

    @Autowired
    public ModelConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job executions
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<JobExecutionDto> convertJobExecutionToDto(List<JobExecutionInfo> jobExecutionList) {
        return jobExecutionList.stream().map(this::convertJobExecutionToDto).collect(toList());
    }

    public JobExecutionDto convertJobExecutionToDto(JobExecutionInfo jobExecutionInfo) {
        JobExecutionDto jobExecutionDto = modelMapper.map(jobExecutionInfo, JobExecutionDto.class);
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
        jobExecutionDto.setJobName(jobExecutionInfo.getJobInstanceInfo().getJobName());
        jobExecutionDto.setTotalSize(totalCount);
        jobExecutionDto.setJobInstanceDto(convertJobInstanceToDto(jobExecutionInfo.getJobInstanceInfo()));
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
            case "STRING":
                jobExecutionParam.setStringVal(jobExecutionParamDto.getStringVal());
                break;
            case "DATE":
                jobExecutionParam.setDateVal(jobExecutionParamDto.getDateVal());
                break;
            case "LONG":
                jobExecutionParam.setLongVal(jobExecutionParamDto.getLongVal());
                break;
            case "DOUBLE":
                jobExecutionParam.setDoubleVal(jobExecutionParamDto.getDoubleVal());
                break;
        }
        return jobExecutionParam;

    }

    public JobExecutionParamDto convertJobParameterToDto(Map.Entry<String, JobParameter> entry) {
        JobExecutionParamDto jobExecutionParamDto = modelMapper.map(entry.getValue(), JobExecutionParamDto.class);
        jobExecutionParamDto.setIdentifying(entry.getValue().isIdentifying());
        jobExecutionParamDto.setKeyName(entry.getKey());
        jobExecutionParamDto.setTypeCd(entry.getValue().getType().name());
        switch (entry.getValue().getType()) {
            case STRING:
                jobExecutionParamDto.setStringVal((String) entry.getValue().getValue());
                break;
            case DATE:
                jobExecutionParamDto.setDateVal((Date) entry.getValue().getValue());
                break;
            case LONG:
                jobExecutionParamDto.setLongVal((Long) entry.getValue().getValue());
                break;
            case DOUBLE:
                jobExecutionParamDto.setDoubleVal((Double) entry.getValue().getValue());
                break;
        }
        return jobExecutionParamDto;
    }

    public JobExecutionDto convertJobExecutionToDto(JobExecution jobExecution) {
        JobExecutionDto jobExecutionDto = modelMapper.map(jobExecution, JobExecutionDto.class);
        jobExecutionDto.setJobName(jobExecution.getJobInstance().getJobName());
        jobExecutionDto.setJobInstanceDto(convertJobInstanceToDto(jobExecution.getJobInstance()));
        List<JobExecutionParamDto> jobExecutionParamDtoes = jobExecution.getJobParameters()
            .getParameters()
            .entrySet()
            .stream()
            .map(this::convertJobParameterToDto).collect(toList());
        jobExecutionDto.setJobExecutionParamDtoes(jobExecutionParamDtoes);
        return jobExecutionDto;
    }

    public JobExecutionInfo convertJobExecutionToEntity(JobExecutionDto jobExecutionDto) {
        JobExecutionInfo jobExecutionInfo = modelMapper.map(jobExecutionDto, JobExecutionInfo.class);
        jobExecutionInfo.setJobInstanceInfo(convertJobInstanceToEntity(jobExecutionDto.getJobInstanceDto()));
        List<JobExecutionParam> jobExecutionParams = jobExecutionDto.getJobExecutionParamDtoes()
            .stream()
            .map(this::convertJobExecutionParamToEntity)
            .collect(toList());
        jobExecutionInfo.setJobExecutionParams(jobExecutionParams);
        return jobExecutionInfo;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job instance
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public JobInstanceDto convertJobInstanceToDto(JobInstanceInfo jobInstanceInfo) {
        JobInstanceDto jobInstanceDto = modelMapper.map(jobInstanceInfo, JobInstanceDto.class);
        jobInstanceDto.setId(null);
        return jobInstanceDto;
    }

    public JobInstanceInfo convertJobInstanceToEntity(JobInstanceDto jobInstanceDto) {
        return modelMapper.map(jobInstanceDto, JobInstanceInfo.class);
    }

    public JobInstanceDto convertJobInstanceToDto(JobInstance jobInstance) {
        JobInstanceDto jobInstanceDto = modelMapper.map(jobInstance, JobInstanceDto.class);
        jobInstanceDto.setId(null);
        return jobInstanceDto;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Step executions
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<StepExecutionDto> convertStepExecutionToDto(List<StepExecutionInfo> stepExecutionList) {
        return stepExecutionList.stream().map(this::convertStepExecutionToDto).collect(toList());
    }

    public StepExecutionDto convertStepExecutionToDto(StepExecutionInfo stepExecutionInfo) {
        StepExecutionDto stepExecutionDto = modelMapper.map(stepExecutionInfo, StepExecutionDto.class);
        stepExecutionDto.setJobNameConverted(stepExecutionInfo.getJobExecutionInfo());
        stepExecutionDto.setJobIdConverted(stepExecutionInfo.getJobExecutionInfo());
        stepExecutionDto.setJobExecutionDto(convertJobExecutionToDto(stepExecutionInfo.getJobExecutionInfo()));
        return stepExecutionDto;
    }

    public List<StepExecutionDto> convertStepExecutionToDto(List<StepExecutionInfo> stepExecutionList,
                                                            long totalCount) {
        return stepExecutionList.stream().map(s -> convertStepExecutionToDto(s, totalCount)).collect(toList());
    }

    public StepExecutionDto convertStepExecutionToDto(StepExecutionInfo stepExecutionInfo, long totalCount) {
        StepExecutionDto stepExecutionDto = convertStepExecutionToDto(stepExecutionInfo);
        stepExecutionDto.setTotalSize(totalCount);
        return stepExecutionDto;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job schedules
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<JobScheduleDto> convertJobScheduleToDto(List<JobSchedule> jobScheduleList) {
        return jobScheduleList.stream().map(this::convertJobScheduleToDto).collect(toList());
    }

    public JobScheduleDto convertJobScheduleToDto(JobSchedule jobSchedule) {
        JobScheduleDto jobScheduleDto = modelMapper.map(jobSchedule, JobScheduleDto.class);
        jobScheduleDto.setJobDefinitionDto(convertJobDefinitionToDto(jobSchedule.getJobDefinition()));
        return jobScheduleDto;
    }

    public List<JobScheduleDto> convertJobScheduleToDto(List<JobSchedule> jobScheduleList, long totalCount) {
        return jobScheduleList.stream().map(s -> convertJobScheduleToDto(s, totalCount)).collect(toList());
    }

    public JobScheduleDto convertJobScheduleToDto(JobSchedule jobSchedule, long totalCount) {
        JobScheduleDto jobScheduleDto = modelMapper.map(jobSchedule, JobScheduleDto.class);
        jobScheduleDto.setJobDefinitionDto(convertJobDefinitionToDto(jobSchedule.getJobDefinition()));
        jobScheduleDto.setTotalSize(totalCount);
        return jobScheduleDto;
    }

    public JobSchedule convertJobScheduleToEntity(JobScheduleDto jobScheduleDto) {
        JobSchedule jobSchedule = modelMapper.map(jobScheduleDto, JobSchedule.class);
        jobSchedule.setJobDefinition(convertJobDefinitionToEntity(jobScheduleDto.getJobDefinitionDto()));
        return jobSchedule;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job definitions
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public JobDefinitionDto convertJobDefinitionToDto(JobDefinition jobDefinition) {

        // Convert job definition
        JobDefinitionDto jobDefinitionDto = modelMapper.map(jobDefinition, JobDefinitionDto.class);
        jobDefinitionDto.setJobGroupDto(modelMapper.map(jobDefinition.getJobGroup(), JobGroupDto.class));

        // Add parameter
        jobDefinitionDto.setJobDefinitionParamDtos(jobDefinition.getJobDefinitionParams()
            .stream()
            .map(this::convertJobDefinitionParamToDto)
            .collect(toList()));
        return jobDefinitionDto;
    }

    public List<JobDefinitionDto> convertJobDefinitionToDto(List<JobDefinition> jobDefinitions) {
        return jobDefinitions.stream().map(this::convertJobDefinitionToDto).collect(toList());
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

    public List<JobDefinitionParamDto> convertJobDefinitionParamToDto(List<JobDefinitionParam> jobDefinitionParams) {
        return jobDefinitionParams.stream().map(this::convertJobDefinitionParamToDto).collect(toList());
    }

    public JobDefinitionParamDto convertJobDefinitionParamToDto(JobDefinitionParam jobDefinitionParam,
                                                                long totalCount) {
        JobDefinitionParamDto jobDefinitionParamDto = convertJobDefinitionParamToDto(jobDefinitionParam);
        jobDefinitionParamDto.setTotalSize(totalCount);
        return jobDefinitionParamDto;
    }

    public List<JobDefinitionParamDto> convertJobDefinitionParamToDto(List<JobDefinitionParam> jobDefinitionParams,
                                                                      long totalCount) {
        return jobDefinitionParams.stream().map(j -> convertJobDefinitionParamToDto(j, totalCount)).collect(toList());
    }

    public JobDefinition convertJobDefinitionToEntity(JobDefinitionDto jobDefinitionDto) {
        JobDefinition jobDefinition = modelMapper.map(jobDefinitionDto, JobDefinition.class);
        jobDefinition.setJobGroup(modelMapper.map(jobDefinitionDto.getJobGroupDto(), JobGroup.class));

        jobDefinition.setJobDefinitionParams(jobDefinitionDto.getJobDefinitionParamDtos()
                .stream()
                .map(this::convertJobDefinitionParamToEntity)
                .collect(toList()));
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

    public List<JobGroupDto> convertJobGroupToDto(List<JobGroup> jobGroups) {
        return jobGroups.stream().map(this::convertJobGroupToDto).collect(toList());
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
        return modelMapper.map(jobGroupDto, JobGroup.class);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Agent
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<AgentDto> convertAgentToDto(List<Agent> agentList) {
        return agentList.stream().map(this::convertAgentToDto).collect(toList());
    }

    public AgentDto convertAgentToDto(Agent agent) {
        AgentDto agentDto = modelMapper.map(agent, AgentDto.class);
        agentDto.setSchedules(agent.getSchedules().stream().map(this::convertJobScheduleToDto).collect(toList()));
        return agentDto;
    }

    public List<AgentDto> convertAgentToDto(List<Agent> agentList, long totalCount) {
        return agentList.stream().map(a -> convertAgentToDto(a, totalCount)).collect(toList());
    }

    public AgentDto convertAgentToDto(Agent agent, long totalCount) {
        AgentDto agentDto = convertAgentToDto(agent);
        agentDto.setTotalSize(totalCount);
        return agentDto;
    }

    public Agent convertAgentToEntity(AgentDto agentDto) {
        return modelMapper.map(agentDto, Agent.class);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Agent performance
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<AgentPerformanceDto> convertAgentPerformanceToDto(List<AgentPerformance> agentPerformanceList) {
        return agentPerformanceList.stream().map(this::convertAgentPerformanceToDto).collect(toList());
    }

    public AgentPerformanceDto convertAgentPerformanceToDto(AgentPerformance agentPerformance) {
        return modelMapper.map(agentPerformance, AgentPerformanceDto.class);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // User
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<UserDto> convertUserToDto(List<User> userList) {
        return userList.stream().map(this::convertUserToDto).collect(toList());
    }

    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public List<UserDto> convertUserToDto(List<User> userList, long totalCount) {
        return userList.stream().map(a -> convertUserToDto(a, totalCount)).collect(toList());
    }

    public UserDto convertUserToDto(User user, long totalCount) {
        UserDto userDto = convertUserToDto(user);
        userDto.setTotalSize(totalCount);
        return userDto;
    }

    public User convertUserToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // User group
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public List<UserGroupDto> convertUserGroupToDto(List<UserGroup> userGroupList) {
        return userGroupList.stream().map(this::convertUserGroupToDto).collect(toList());
    }

    public UserGroupDto convertUserGroupToDto(UserGroup userGroup) {
        return modelMapper.map(userGroup, UserGroupDto.class);
    }

    public List<UserGroupDto> convertUserGroupToDto(List<UserGroup> userGroupList, long totalCount) {
        return userGroupList.stream().map(a -> convertUserGroupToDto(a, totalCount)).collect(toList());
    }

    public UserGroupDto convertUserGroupToDto(UserGroup userGroup, long totalCount) {
        UserGroupDto userGroupDto = convertUserGroupToDto(userGroup);
        userGroupDto.setTotalSize(totalCount);
        return userGroupDto;
    }

    public UserGroup convertUserGroupToEntity(UserGroupDto userGroupDto) {
        return modelMapper.map(userGroupDto, UserGroup.class);
    }
}
