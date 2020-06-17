package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.BatchPerformanceType;
import com.momentum.batch.common.domain.dto.BatchPerformanceDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.manager.service.BatchPerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * Batch performance REST controller.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/batchperformance")
public class BatchPerformanceController {

    private static final Logger logger = LoggerFactory.getLogger(BatchPerformanceController.class);

    private final MethodTimer t = new MethodTimer();

    private final BatchPerformanceService batchPerformanceService;

    private final ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param batchPerformanceService service implementation.
     */
    @Autowired
    public BatchPerformanceController(BatchPerformanceService batchPerformanceService, ModelConverter modelConverter) {
        this.batchPerformanceService = batchPerformanceService;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns batch performance data.
     *
     * @return batch performance data
     */
    @GetMapping(value = "/byNodeName/{nodeName}", produces = {"application/json"})
    public ResponseEntity<List<BatchPerformanceDto>> findByNodeNameAndType(@PathVariable String nodeName, @RequestParam String type, @RequestParam String metric,
                                                                           @RequestParam(value = "scale", required = false) Long scale,
                                                                           @RequestParam(value = "startTime", required = false) Long startTime,
                                                                           @RequestParam(value = "endTime", required = false) Long endTime) {
        t.restart();

        // Get parameters
        List<BatchPerformance> allBatchPerformances = batchPerformanceService.findData(nodeName, BatchPerformanceType.valueOf(type),
                metric, new Timestamp(startTime * 1000), new Timestamp(endTime * 1000));

        List<BatchPerformanceDto> batchPerformanceDtoes = modelConverter.convertBatchPerformanceToDto(allBatchPerformances);
        logger.debug(format("Finished batch performance data request- count: {0} {1}", batchPerformanceDtoes.size(), t.elapsedStr()));

        return ResponseEntity.ok(batchPerformanceDtoes);
    }
}
