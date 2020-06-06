package com.momentum.batch.client.common.converter;

import com.momentum.batch.domain.dto.JobExecutionDto;
import com.momentum.batch.domain.dto.JobExecutionParamDto;
import com.momentum.batch.domain.dto.JobInstanceDto;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.JobParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    private final ModelMapper modelMapper;

    @Autowired
    public ModelConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job executions
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
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

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job instance
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public JobInstanceDto convertJobInstanceToDto(JobInstance jobInstance) {
        JobInstanceDto jobInstanceDto = modelMapper.map(jobInstance, JobInstanceDto.class);
        jobInstanceDto.setId(null);
        return jobInstanceDto;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job executions
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public JobExecutionParamDto convertJobParameterToDto(Map.Entry<String, JobParameter> entry) {
        JobExecutionParamDto jobExecutionParamDto = modelMapper.map(entry.getValue(), JobExecutionParamDto.class);
        jobExecutionParamDto.setIdentifying(entry.getValue().isIdentifying());
        jobExecutionParamDto.setKeyName(entry.getKey());
        jobExecutionParamDto.setTypeCd(entry.getValue().getType().name());
        switch (entry.getValue().getType()) {
            case STRING -> jobExecutionParamDto.setStringVal((String) entry.getValue().getValue());
            case DATE -> jobExecutionParamDto.setDateVal((Date) entry.getValue().getValue());
            case LONG -> jobExecutionParamDto.setLongVal((Long) entry.getValue().getValue());
            case DOUBLE -> jobExecutionParamDto.setDoubleVal((Double) entry.getValue().getValue());
        }
        return jobExecutionParamDto;
    }
}
