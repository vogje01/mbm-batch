package com.hlag.fis.batch.jobs.mysqlsynchronization.steps.shipment.documentationrequest;

import com.hlag.fis.db.db2.model.DocumentationRequestOld;
import com.hlag.fis.db.mysql.model.*;
import com.hlag.fis.db.mysql.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Documentation requests processor.
 * <p>
 * This processor inserts all the constraints to a documentation request. Currently this are:
 * <ul>
 * <li><b>Planned shipment:</b> the planned shipment is retrieved by shipment number and client.</li>
 * <li><b>Documentation center:</b> the documentation center is retrieved by name and supplement.</li>
 * </ul>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.2
 */
@Component
public class DocumentationRequestProcessor implements ItemProcessor<DocumentationRequestOld, DocumentationRequest> {

    private static final Logger logger = LoggerFactory.getLogger(DocumentationRequestProcessor.class);

    @Value("${documentationRequest.fullSync}")
    private boolean fullSync;

    private OrganizationPlaceRepository organizationPlaceRepository;

    private LocationRepository locationRepository;

    private GeoHierarchyRepository geoHierarchyRepository;

    private PlannedShipmentRepository plannedShipmentRepository;

    private DocumentationRequestRepository documentationRequestRepository;

    @Autowired
    public DocumentationRequestProcessor(
            OrganizationPlaceRepository organizationPlaceRepository,
            LocationRepository locationRepository,
            GeoHierarchyRepository geoHierarchyRepository,
            PlannedShipmentRepository plannedShipmentRepository,
            DocumentationRequestRepository documentationRequestRepository) {
        this.organizationPlaceRepository = organizationPlaceRepository;
        this.locationRepository = locationRepository;
        this.geoHierarchyRepository = geoHierarchyRepository;
        this.plannedShipmentRepository = plannedShipmentRepository;
        this.documentationRequestRepository = documentationRequestRepository;
    }

    /**
     * Item processor for the documentation request model.
     * <p>
     * This will create a new MySQL documentation request model.
     *
     * @param documentationRequestOld old DB2 documentation request.
     * @return MySQL documentation request model.
     */
    @Override
    public DocumentationRequest process(DocumentationRequestOld documentationRequestOld) {
        logger.debug("Processing old planned shipment - " + documentationRequestOld.toString());
        DocumentationRequest newDocRequest;
        Optional<DocumentationRequest> oldDocRequestOpt = documentationRequestRepository
                .findByClientAndNumberAndRelativeNumber(documentationRequestOld.getId().getClient(),
                        documentationRequestOld.getId().getNumber(),
                        documentationRequestOld.getId().getRelativeNumber());
        if (oldDocRequestOpt.isPresent()) {
            newDocRequest = oldDocRequestOpt.get();
            if (!fullSync && newDocRequest.getLastChange().equals(documentationRequestOld.getLastChange())) {
                // Nothing to do
                return null;
            }
            newDocRequest.update(documentationRequestOld);
        } else {
            newDocRequest = new DocumentationRequest();
            newDocRequest.update(documentationRequestOld);
        }

        // Add constraints
        getPlannedShipmentConstraint(documentationRequestOld, newDocRequest);
        getDocCenterConstraint(documentationRequestOld, newDocRequest);
        getCustomerConstraint(documentationRequestOld, newDocRequest);
        getIssuerConstraint(documentationRequestOld, newDocRequest);
        getIssuerGeoConstraint(documentationRequestOld, newDocRequest);
        getForwarderConstraint(documentationRequestOld, newDocRequest);
        getShipperConstraint(documentationRequestOld, newDocRequest);
        getConsigneeConstraint(documentationRequestOld, newDocRequest);
        getBookingOfficeConstraint(documentationRequestOld, newDocRequest);
        getStartLocationConstraint(documentationRequestOld, newDocRequest);
        getEndLocationConstraint(documentationRequestOld, newDocRequest);

        return newDocRequest;
    }

    /**
     * Add the one to many relationship from planned shipment to documentation request. The relationship
     * ist mapped by the planned shipment uuid in the documentation request.
     *
     * @param documentationRequest documentation request
     */
    private void getPlannedShipmentConstraint(DocumentationRequestOld documentationRequestOld, DocumentationRequest documentationRequest) {
        Optional<PlannedShipment> plannedShipmentOpt = plannedShipmentRepository.findByClientAndNumber(documentationRequestOld.getId().getClient(), documentationRequestOld.getId().getNumber());
        if (plannedShipmentOpt.isPresent()) {
            PlannedShipment plannedShipment = plannedShipmentOpt.get();
            if (documentationRequest.getPlannedShipment() == null || !documentationRequest.getPlannedShipment().getId().equals(plannedShipment.getId())) {
                documentationRequest.setPlannedShipment(plannedShipment);
            }
        }
    }

    /**
     * Add the one to one relationship from documentation request to organizational place for the document center. The relationship
     * is mapped by the documentation request uuid.
     *
     * @param documentationRequest documentation request
     */
    private void getDocCenterConstraint(DocumentationRequestOld documentationRequestOld, DocumentationRequest documentationRequest) {
        if (documentationRequestOld.getDocCenterName() != null && documentationRequestOld.getDocCenterSupplement() > 0) {
            List<OrganizationPlace> organizationPlaces = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(
                    documentationRequestOld.getId().getClient(), documentationRequestOld.getDocCenterName(), documentationRequestOld.getDocCenterSupplement());
            if (!organizationPlaces.isEmpty()) {
                OrganizationPlace docCenter = organizationPlaces.get(0);
                if (documentationRequest.getDocCenter() == null || !documentationRequest.getDocCenter().getId().equals(docCenter.getId())) {
                    documentationRequest.setDocCenter(docCenter);
                }
            }
        }
    }

    /**
     * Add the one to one relationship from documentation request to organizational place for the customer. The relationship
     * is mapped by the documentation request uuid.
     *
     * @param documentationRequest documentation request
     */
    private void getCustomerConstraint(DocumentationRequestOld documentationRequestOld, DocumentationRequest documentationRequest) {
        if (documentationRequestOld.getCustomerName() != null && documentationRequestOld.getCustomerSupplement() > 0) {
            List<OrganizationPlace> organizationPlaces = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(
                    documentationRequestOld.getId().getClient(), documentationRequestOld.getCustomerName(), documentationRequestOld.getCustomerSupplement());
            if (!organizationPlaces.isEmpty()) {
                OrganizationPlace customer = organizationPlaces.get(0);
                if (documentationRequest.getCustomer() == null || !documentationRequest.getCustomer().getId().equals(customer.getId())) {
                    documentationRequest.setCustomer(customer);
                }
            }
        }
    }

    /**
     * Add the one to one relationship from documentation request to organizational place for the issuer. The relationship
     * is mapped by the documentation request uuid.
     *
     * @param documentationRequest documentation request
     */
    private void getIssuerConstraint(DocumentationRequestOld documentationRequestOld, DocumentationRequest documentationRequest) {
        if (documentationRequestOld.getIssuerName() != null && documentationRequestOld.getIssuerSupplement() > 0) {
            List<OrganizationPlace> organizationPlaces = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(
                    documentationRequestOld.getId().getClient(), documentationRequestOld.getIssuerName(), documentationRequestOld.getIssuerSupplement());
            if (!organizationPlaces.isEmpty()) {
                OrganizationPlace issuer = organizationPlaces.get(0);
                if (documentationRequest.getIssuer() == null || !documentationRequest.getIssuer().getId().equals(issuer.getId())) {
                    documentationRequest.setIssuer(issuer);
                }
            }
        }
    }

    /**
     * Add the one to one relationship from documentation request to geo hierarchy for the issuer geo. The relationship
     * is mapped by the geo hierarchy uuid.
     *
     * @param documentationRequest documentation request
     */
    private void getIssuerGeoConstraint(DocumentationRequestOld documentationRequestOld, DocumentationRequest documentationRequest) {
        if (documentationRequestOld.getIssuerGeoRegion() != null && documentationRequestOld.getIssuerGeoSubregion() != null) {
            GeoHierarchy geoHierarchy = geoHierarchyRepository.findGeoHierarchyByArea(documentationRequestOld.getId().getClient(),
                    documentationRequestOld.getIssuerGeoRegion(), documentationRequestOld.getIssuerGeoSubregion(), documentationRequestOld.getIssuerGeoArea());
            if (documentationRequest.getIssuerGeo() == null || !documentationRequest.getIssuerGeo().getId().equals(geoHierarchy.getId())) {
                documentationRequest.setIssuerGeo(geoHierarchy);
            }
        }
    }

    /**
     * Add the one to one relationship from documentation request to organizational place for the forwarder. The relationship
     * is mapped by the documentation request uuid.
     *
     * @param documentationRequest documentation request
     */
    private void getForwarderConstraint(DocumentationRequestOld documentationRequestOld, DocumentationRequest documentationRequest) {
        if (documentationRequestOld.getForwarderName() != null && documentationRequestOld.getForwarderSupplement() > 0) {
            List<OrganizationPlace> organizationPlaces = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(
                    documentationRequestOld.getId().getClient(), documentationRequestOld.getForwarderName(), documentationRequestOld.getForwarderSupplement());
            if (!organizationPlaces.isEmpty()) {
                OrganizationPlace forwarder = organizationPlaces.get(0);
                if (documentationRequest.getForwarder() == null || !documentationRequest.getForwarder().getId().equals(forwarder.getId())) {
                    documentationRequest.setForwarder(forwarder);
                }
            }
        }
    }

    /**
     * Add the one to one relationship from documentation request to organizational place for the shipper. The relationship
     * is mapped by the documentation request uuid.
     *
     * @param documentationRequest documentation request
     */
    private void getShipperConstraint(DocumentationRequestOld documentationRequestOld, DocumentationRequest documentationRequest) {
        if (documentationRequestOld.getShipperName() != null && documentationRequestOld.getShipperSupplement() > 0) {
            List<OrganizationPlace> organizationPlaces = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(
                    documentationRequestOld.getId().getClient(), documentationRequestOld.getShipperName(), documentationRequestOld.getShipperSupplement());
            if (!organizationPlaces.isEmpty()) {
                OrganizationPlace shipper = organizationPlaces.get(0);
                if (documentationRequest.getShipper() == null || !documentationRequest.getShipper().getId().equals(shipper.getId())) {
                    documentationRequest.setShipper(shipper);
                }
            }
        }
    }

    /**
     * Add the one to one relationship from documentation request to organizational place for the consignee. The relationship
     * is mapped by the documentation request uuid.
     *
     * @param documentationRequest documentation request
     */
    private void getConsigneeConstraint(DocumentationRequestOld documentationRequestOld, DocumentationRequest documentationRequest) {
        if (documentationRequestOld.getConsigneeName() != null && documentationRequestOld.getConsigneeSupplement() > 0) {
            List<OrganizationPlace> organizationPlaces = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(
                    documentationRequestOld.getId().getClient(), documentationRequestOld.getConsigneeName(), documentationRequestOld.getConsigneeSupplement());
            if (!organizationPlaces.isEmpty()) {
                OrganizationPlace consignee = organizationPlaces.get(0);
                if (documentationRequest.getConsignee() == null || !documentationRequest.getConsignee().getId().equals(consignee.getId())) {
                    documentationRequest.setConsignee(consignee);
                }
            }
        }
    }

    /**
     * Add the one to one relationship from documentation request to organizational place for the bookingOffice. The relationship
     * is mapped by the documentation request uuid.
     *
     * @param documentationRequest documentation request
     */
    private void getBookingOfficeConstraint(DocumentationRequestOld documentationRequestOld, DocumentationRequest documentationRequest) {
        if (documentationRequestOld.getBookingOfficeName() != null && documentationRequestOld.getBookingOfficeSupplement() > 0) {
            List<OrganizationPlace> organizationPlaces = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(
                    documentationRequestOld.getId().getClient(), documentationRequestOld.getBookingOfficeName(), documentationRequestOld.getBookingOfficeSupplement());
            if (!organizationPlaces.isEmpty()) {
                OrganizationPlace bookingOffice = organizationPlaces.get(0);
                if (documentationRequest.getBookingOffice() == null || !documentationRequest.getBookingOffice().getId().equals(bookingOffice.getId())) {
                    documentationRequest.setBookingOffice(bookingOffice);
                }
            }
        }
    }

    /**
     * Add the one to one relationship from documentation request to geo hierarchy for the start geo. The relationship
     * is mapped by the geo hierarchy uuid.
     *
     * @param documentationRequest documentation request
     */
    private void getStartLocationConstraint(DocumentationRequestOld documentationRequestOld, DocumentationRequest documentationRequest) {
        if (documentationRequestOld.getStartStdLocation() != null) {
            // BUG in locations, there should be only one, but sometimes.....
            List<Location> locations = locationRepository.findByClientAndBusinessLocode(
                    documentationRequestOld.getId().getClient(), documentationRequestOld.getStartStdLocation());
            if (!locations.isEmpty()) {
                Location location = locations.get(0);
                if (documentationRequest.getStartLocation() == null || !documentationRequest.getStartLocation().getId().equals(location.getId())) {
                    documentationRequest.setStartLocation(location);
                }
            }
        }
    }

    /**
     * Add the one to one relationship from documentation request to geo hierarchy for the end geo. The relationship
     * is mapped by the geo hierarchy uuid.
     *
     * @param documentationRequest documentation request
     */
    private void getEndLocationConstraint(DocumentationRequestOld documentationRequestOld, DocumentationRequest documentationRequest) {
        if (documentationRequestOld.getEndStdLocation() != null) {
            // BUG in locations, there should be only one, but sometimes.....
            List<Location> locations = locationRepository.findByClientAndBusinessLocode(
                    documentationRequestOld.getId().getClient(), documentationRequestOld.getStartStdLocation());
            if (!locations.isEmpty()) {
                Location location = locations.get(0);
                if (documentationRequest.getEndLocation() == null || !documentationRequest.getEndLocation().getId().equals(location.getId())) {
                    documentationRequest.setEndLocation(location);
                }
            }
        }
    }
}