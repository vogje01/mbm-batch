package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis;

import com.hlag.fis.batch.builder.BatchFlowBuilder;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.client.ClientStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.clientauthorization.ClientAuthorizationStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.clientrole.ClientRoleStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.functionalunit.FunctionalUnitStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.message.MessageStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.messagespecification.MessageSpecificationStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.securityorganization.SecurityOrganizationStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.trustworthexclusion.TrustworthExclusionStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.userauthorization.UserAuthorizationStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.userrole.UserRoleStep;
import com.hlag.fis.batch.jobs.mysqlsynchronization.steps.basis.users.UsersStep;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class BasisFlows {

    /**
     * Basis is active
     */
    @Value("${basis.entityActive}")
    private boolean basisEntityActive;
    /**
     * Users are active
     */
    @Value("${users.entityActive}")
    private boolean usersEntityActive;
    /**
     * Clients are active
     */
    @Value("${client.entityActive}")
    private boolean clientEntityActive;
    /**
     * Client roles are active
     */
    @Value("${clientRole.entityActive}")
    private boolean clientRoleEntityActive;
    /**
     * User roles are active
     */
    @Value("${userRole.entityActive}")
    private boolean userRoleEntityActive;
    /**
     * User roles are active
     */
    @Value("${functionalUnit.entityActive}")
    private boolean functionalUnitEntityActive;
    /**
     * User authorization are active
     */
    @Value("${userAuthorization.entityActive}")
    private boolean userAuthorizationEntityActive;
    /**
     * Client authorization are active
     */
    @Value("${clientAuthorization.entityActive}")
    private boolean clientAuthorizationEntityActive;
    /**
     * Client authorization are active
     */
    @Value("${securityOrganization.entityActive}")
    private boolean securityOrganizationEntityActive;
    /**
     * Trustworth exclusion are active
     */
    @Value("${trustworthExclusion.entityActive}")
    private boolean trustworthExclusionEntityActive;
    /**
     * Message are active
     */
    @Value("${message.entityActive}")
    private boolean messageEntityActive;
    /**
     * Message specification are active
     */
    @Value("${messageSpecification.entityActive}")
    private boolean messageSpecificationEntityActive;
    /**
     * Users synchronization step.
     */
    private UsersStep usersStep;
    /**
     * Clients synchronization step.
     */
    private ClientStep clientStep;
    /**
     * User roles synchronization step.
     */
    private UserRoleStep userRoleStep;
    /**
     * Client roles synchronization step.
     */
    private ClientRoleStep clientRoleStep;
    /**
     * Functional unit synchronization step.
     */
    private FunctionalUnitStep functionalUnitStep;
    private UserAuthorizationStep userAuthorizationStep;
    private ClientAuthorizationStep clientAuthorizationStep;
    private SecurityOrganizationStep securityOrganizationStep;
    private TrustworthExclusionStep trustworthExclusionStep;
    private MessageStep messageStep;
    private MessageSpecificationStep messageSpecificationStep;

    @Autowired
    @SuppressWarnings({"squid:S00107"})
    public BasisFlows(
            UsersStep usersStep,
            ClientStep clientStep,
            UserRoleStep userRoleStep,
            ClientRoleStep clientRoleStep,
            FunctionalUnitStep functionalUnitStep,
            UserAuthorizationStep userAuthorizationStep,
            ClientAuthorizationStep clientAuthorizationStep,
            SecurityOrganizationStep securityOrganizationStep,
            TrustworthExclusionStep trustworthExclusionStep,
            MessageStep messageStep,
            MessageSpecificationStep messageSpecificationStep) {
        this.usersStep = usersStep;
        this.clientStep = clientStep;
        this.userRoleStep = userRoleStep;
        this.clientRoleStep = clientRoleStep;
        this.userAuthorizationStep = userAuthorizationStep;
        this.clientAuthorizationStep = clientAuthorizationStep;
        this.securityOrganizationStep = securityOrganizationStep;
        this.trustworthExclusionStep = trustworthExclusionStep;
        this.functionalUnitStep = functionalUnitStep;
        this.messageStep = messageStep;
        this.messageSpecificationStep = messageSpecificationStep;
    }

    /**
     * Return a list of basis model flows.
     * <p>
     * The flows will be executed in parallel.
     *
     * @return list of basis steps to be executed in parallel.
     */
    public Flow getFlows() {

        List<Step> steps = new ArrayList<>();
        if (basisEntityActive || usersEntityActive) {
            steps.add(usersStep.synchronizeUsers());
        }
        if (basisEntityActive || clientEntityActive) {
            steps.add(clientStep.synchronizeClient());
        }
        if (basisEntityActive || clientRoleEntityActive) {
            steps.add(clientRoleStep.synchronizeClientRole());
        }
        if (basisEntityActive || userRoleEntityActive) {
            steps.add(userRoleStep.synchronizeUserRole());
        }
        if (basisEntityActive || functionalUnitEntityActive) {
            steps.add(functionalUnitStep.synchronizeFunctionalUnit());
        }
        if (basisEntityActive || userAuthorizationEntityActive) {
            steps.add(userAuthorizationStep.synchronizeUserAuthorization());
        }
        if (basisEntityActive || clientAuthorizationEntityActive) {
            steps.add(clientAuthorizationStep.synchronizeClientAuthorization());
        }
        if (basisEntityActive || securityOrganizationEntityActive) {
            steps.add(securityOrganizationStep.synchronizeSecurityOrganization());
        }
        if (basisEntityActive || trustworthExclusionEntityActive) {
            steps.add(trustworthExclusionStep.synchronizeTrustworthExclusion());
        }
        if (basisEntityActive || messageEntityActive) {
            steps.add(messageStep.synchronizeMessage());
        }
        if (basisEntityActive || messageSpecificationEntityActive) {
            steps.add(messageSpecificationStep.synchronizeMessageSpecification());
        }
        if (steps.size() > 0) {
            return new BatchFlowBuilder<Flow>("Basis flows").splitSteps(steps).build();
        }
        return null;
    }
}
