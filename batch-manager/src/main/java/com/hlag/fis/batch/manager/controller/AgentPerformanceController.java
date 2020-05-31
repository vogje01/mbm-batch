package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import com.hlag.fis.batch.domain.dto.AgentPerformanceDto;
import com.hlag.fis.batch.manager.service.AgentPerformanceService;
import com.hlag.fis.batch.util.MethodTimer;
import com.hlag.fis.batch.util.ModelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Agent performance REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/agentperformance")
public class AgentPerformanceController {

    private static final Logger logger = LoggerFactory.getLogger(AgentPerformanceController.class);

    private MethodTimer t = new MethodTimer();

    private AgentPerformanceService service;

    private ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param service service implementation.
     */
    @Autowired
    public AgentPerformanceController(AgentPerformanceService service, ModelConverter modelConverter) {
        this.service = service;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns one page of job definitions.
     *
     * @return on page of job definitions.
     */
    @GetMapping(value = "/byNodeName/{nodeName}", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<AgentPerformanceDto>> findByNodeNameAndType(@PathVariable("nodeName") String nodeName,
                                                                                      @RequestParam("type") String type,
                                                                                      @RequestParam(value = "scale", required = false) Long scale,
                                                                                      @RequestParam(value = "startTime", required = false) Long startTime,
                                                                                      @RequestParam(value = "endTime", required = false) Long endTime) {
        t.restart();

        // Get parameters
        Timestamp startTimestamp = new Timestamp(startTime * 1000);
        Timestamp endTimestamp = new Timestamp(endTime * 1000);
        Page<AgentPerformance> allAgentPerformances = service.findByNodeNameAndTypeAndTimeRange(nodeName,
                AgentPerformanceType.valueOf(type), startTimestamp, endTimestamp, Pageable.unpaged());

        List<AgentPerformanceDto> agentPerformanceDtoes = modelConverter.convertAgentPerformanceToDto(allAgentPerformances.toList());

        Link self = linkTo(methodOn(AgentPerformanceController.class).findByNodeNameAndType(nodeName, type, scale, startTime, endTime)).withSelfRel();
        logger.debug(format("Finished agent performance data request- count: {0} {1}", allAgentPerformances.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(agentPerformanceDtoes, self));
    }
}
