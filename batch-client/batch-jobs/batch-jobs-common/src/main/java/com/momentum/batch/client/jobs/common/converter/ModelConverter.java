package com.momentum.batch.client.jobs.common.converter;

import com.momentum.batch.common.domain.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.1
 */
public class ModelConverter {

    private final ModelMapper modelMapper;

    private final ModelConverterHelper modelConverterHelper;

    @Autowired
    public ModelConverter(ModelMapper modelMapper, ModelConverterHelper modelConverterHelper) {
        this.modelMapper = modelMapper;
        this.modelConverterHelper = modelConverterHelper;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job executions
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public JobExecutionDto convertJobExecutionToDto(JobExecution jobExecution) {
        JobExecutionDto jobExecutionDto = modelMapper.map(jobExecution, JobExecutionDto.class);
        jobExecutionDto.setJobExecutionContextDto(convertJobExecutionContextToDto(jobExecution.getExecutionContext()));
        List<JobExecutionParamDto> jobExecutionParamDtoes = jobExecution.getJobParameters()
                .getParameters()
                .entrySet()
                .stream()
                .map(this::convertJobParameterToDto).collect(toList());
        jobExecutionDto.setJobExecutionParamDtoes(jobExecutionParamDtoes);
        return jobExecutionDto;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job execution params
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

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Job execution context
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public JobExecutionContextDto convertJobExecutionContextToDto(ExecutionContext jobExecutionContext) {
        JobExecutionContextDto jobExecutionContextDto = new JobExecutionContextDto();
        jobExecutionContextDto.setSerializedContext(modelConverterHelper.jsonSerialize(jobExecutionContext));
        return jobExecutionContextDto;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Step executions
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public StepExecutionDto convertStepExecutionToDto(StepExecution stepExecution) {
        StepExecutionDto stepExecutionDto = modelMapper.map(stepExecution, StepExecutionDto.class);
        stepExecutionDto.setStepExecutionContextDto(convertStepExecutionContextToDto(stepExecution.getExecutionContext()));
        return stepExecutionDto;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    // Step execution context
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public StepExecutionContextDto convertStepExecutionContextToDto(ExecutionContext stepExecutionContext) {
        StepExecutionContextDto stepExecutionContextDto = new StepExecutionContextDto();
        stepExecutionContextDto.setSerializedContext(modelConverterHelper.jsonSerialize(stepExecutionContext));
        return stepExecutionContextDto;
    }
}
