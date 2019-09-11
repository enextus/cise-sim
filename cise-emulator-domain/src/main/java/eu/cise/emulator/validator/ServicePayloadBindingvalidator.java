package eu.cise.emulator.validator;

import com.github.mfatihercik.dsb.DSM;
import com.github.mfatihercik.dsb.DSMBuilder;

import java.util.*;

public class ServicePayloadBindingvalidator {
    private final List<BoundService> servicesRef;

    private static final String JSON_DATA = "{\n" +
            "  \"services\": [  {\"serviceName\":\"ActionService\", \"contentTypes\":[\"Action\"]},\n" +
            "    {\"serviceName\":\"AgentService\",\"contentTypes\":[\"Agent\", \"Person and Organization\"]},\n" +
            "    {\"serviceName\":\"AircraftService\",\"contentTypes\":[\"Aircraft\"]},\n" +
            "    {\"serviceName\":\"AnomalyService\",\"contentTypes\":[\"Anomaly\"]},\n" +
            "    {\"serviceName\":\"CargoDocumentService\",\"contentTypes\":[\"CargoDocument\"]},\n" +
            "    {\"serviceName\":\"CargoService\",\"contentTypes\":[\"Cargo\"]},\n" +
            "    {\"serviceName\":\"CertificateDocumentService\",\"contentTypes\":[\"CertificateDocument\"]},\n" +
            "    {\"serviceName\":\"CrisisIncident\",\"contentTypes\":[\"CrisisIncident\"]},\n" +
            "    {\"serviceName\":\"DocumentService\",\"contentTypes\":[\"Document\", \"Stream\"]},\n" +
            "    {\"serviceName\":\"EventDocumentService\",\"contentTypes\":[\"EventDocument\"]},\n" +
            "    {\"serviceName\":\"IncidentService\",\"contentTypes\":[\"Incident\"]},\n" +
            "    {\"serviceName\":\"IrregularMigrationIncidentService\",\"contentTypes\":[\"IrregularMigrationIncident\"]},\n" +
            "    {\"serviceName\":\"LandVehicleService\",\"contentTypes\":[\"LandVehicle\"]},\n" +
            "    {\"serviceName\":\"LawInfringementIncidentService\",\"contentTypes\":[\"LawInfringementIncident\"]},\n" +
            "    {\"serviceName\":\"LocationService\",\"contentTypes\":[\"Location\", \"PortLocation\", \"PortFacilityLocation\", \"NamedLocation\"]},\n" +
            "    {\"serviceName\":\"LocationDocumentService\",\"contentTypes\":[\"LocationDocument\"]},\n" +
            "    {\"serviceName\":\"MaritimeSafetyIncidentService\",\"contentTypes\":[\"MaritimeSafetyIncident\"]},\n" +
            "    {\"serviceName\":\"MeteoOceanographicConditionService\",\"contentTypes\":[\"MeteoOceanographicCondition\"]},\n" +
            "    {\"serviceName\":\"MovementService\",\"contentTypes\":[\"Movement\"]},\n" +
            "    {\"serviceName\":\"OperationalAssetService\",\"contentTypes\":[\"OperationalAsset\"]},\n" +
            "    {\"serviceName\":\"OrganizationService\",\"contentTypes\":[\"Organization\", \"PortOrganization\", \"OrganizationalUnit\", \"OrganizationalCollaboration\", \"FormalOrganization\"]},\n" +
            "    {\"serviceName\":\"OrganizationDocumentService\",\"contentTypes\":[\"OrganizationDocument\"]},\n" +
            "    {\"serviceName\":\"PersonService\",\"contentTypes\":[\"Person\"]},\n" +
            "    {\"serviceName\":\"PersonDocumentService\",\"contentTypes\":[\"PersonDocument\"]},\n" +
            "    {\"serviceName\":\"RiskDocumentService\",\"contentTypes\":[\"RiskDocument\"]},\n" +
            "    {\"serviceName\":\"RiskService\",\"contentTypes\":[\"Risk\"]},\n" +
            "    {\"serviceName\":\"VesselDocumentService\",\"contentTypes\":[\"VesselDocument\"]},\n" +
            "    {\"serviceName\":\"VesselService\",\"contentTypes\":[\"Vessel\"]}\n" +
            "  ]\n" +
            "\n" +
            "}";

    private static final String CONFIG_CONTENT =
            "version: 1.0\n" +
                    "params:\n" +
                    "  dateFormat: dd.MM.yyyy  # default 'dateFormat' for all 'date' dataType\n" +
                    "result:\n" +
                    "  type: array\n" +
                    "  path: /services/*\n" +
                    "  fields:\n" +
                    "    serviceName: default\n" +
                    "    contentTypes:\n" +
                    "      type: array\n";

    public ServicePayloadBindingvalidator() {
        DSM dsm = new DSMBuilder(CONFIG_CONTENT).create();
        servicesRef = (List<BoundService>) dsm.toObject(JSON_DATA);
    }


    public boolean isConformContentTypeBinding(String aContentType, String aServiceType) {
        boolean sresponse = false;
        sresponse = (getServiceCorrespondContentBinding(aContentType).equals(aServiceType));
        return sresponse;
    }

    public String getServiceCorrespondContentBinding(String aContentType) {
        Iterator<BoundService> anIterator = servicesRef.iterator();
        while (anIterator.hasNext()) {
            Object aObjectIterated = anIterator.next();
            ArrayList<String> aArrayListContentType = (ArrayList<String>) ((LinkedHashMap) aObjectIterated).get("contentTypes");
            boolean test = aArrayListContentType.contains(aContentType);
            //Arrays.stream(aArrayListContentType).anyMatch(aContentType::equals);
            if (test) {
                return (String) ((LinkedHashMap) aObjectIterated).get("serviceName");
            }
        }
        return "";
    }

}
