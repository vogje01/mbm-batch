package com.momentum.batch.server.manager.controller;

import com.momentum.batch.server.database.domain.dto.JobStatisticDto;
import com.momentum.batch.server.database.domain.dto.StepStatisticDto;
import com.momentum.batch.server.manager.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@RestController
@RequestMapping("/api/status")
public class StatusController {

    private final StatusService statusService;

    /**
     * Constructor.
     *
     * @param statusService service implementation.
     */
    @Autowired
    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    /**
     * Returns the job statistics.
     *
     * @return job statistics.
     */
    @GetMapping(value = "/jobstatus", produces = {"application/hal+json"})
    public ResponseEntity<JobStatisticDto> getJobStatistics() {
        return ResponseEntity.ok(statusService.getJobStatistics());
    }

    /**
     * Returns the step statistics.
     *
     * @return step statistics.
     */
    @GetMapping(value = "/stepstatus", produces = {"application/hal+json"})
    public ResponseEntity<StepStatisticDto> getStepStatistics() {
        return ResponseEntity.ok(statusService.getStepStatistics());
    }
}
