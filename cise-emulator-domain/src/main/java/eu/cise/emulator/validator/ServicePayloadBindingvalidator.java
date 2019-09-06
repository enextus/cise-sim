package eu.cise.emulator.validator;
import com.github.mfatihercik.dsb.DSM;
import com.github.mfatihercik.dsb.DSMBuilder;

import java.io.IOException;
import java.util.*;

public class ServicePayloadBindingvalidator {
    private static ServicePayloadBindingvalidator selfRefSingleton = new ServicePayloadBindingvalidator() ;
    private final List<BindedService> servicesRef;

    static final String jsonData ="{\n" +
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

    static final String configContent=
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
        DSM dsm = new DSMBuilder(configContent).create();
        servicesRef = (List<BindedService>) dsm.toObject(jsonData);
    }



    public static ServicePayloadBindingvalidator getinstance() {
        if (ServicePayloadBindingvalidator.selfRefSingleton== null){
            return new ServicePayloadBindingvalidator();
        } else {
            return  ServicePayloadBindingvalidator.selfRefSingleton;
        }
    }

    public boolean isConformContentTypeBinding(String aContentType, String aServiceType){
        boolean sresponse = false;
        sresponse = (getServiceCorrespondContentBinding( aContentType ).equals(aServiceType));
        return sresponse;
    }

    public String getServiceCorrespondContentBinding(String aContentType){
        Iterator<BindedService> anIterator= servicesRef.iterator();
        while (anIterator.hasNext()){
            Object aObjectIterated= anIterator.next();
            ArrayList<String> aArrayListContentType  =(ArrayList<String> ) ((LinkedHashMap) aObjectIterated).get("contentTypes");
            boolean test = aArrayListContentType.contains(aContentType);
            //Arrays.stream(aArrayListContentType).anyMatch(aContentType::equals);
            if (test) {
                return (String) ((LinkedHashMap)aObjectIterated).get("serviceName");
            }
        }
        return "";
    }


    class BindedService{

        private  String serviceName;
        private String[] contentTypes;

        public BindedService() {
            super();
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String[] getContentTypes() {
            return contentTypes;
        }
        public boolean asContentType(String aContentType){
            boolean result = Arrays.stream(contentTypes).anyMatch(aContentType::equals);
            return result;
        }

        public void setContentTypes(String[] contentTypes) {
            this.contentTypes = contentTypes;
        }

    }
}





/* /test method alteration/
    public ServicePayloadBindingvalidator() {
        DSM dsm = new DSMBuilder(this.getClass().getClassLoader().getResource("ServiceContentTypeBinding.json.yml").getFile()).create();
        servicesRef = (List<BindedService>) dsm.toObject(this.getClass().getClassLoader().getResource("mapping.yml").getFile());
    }

    * */
