package eu.cise.emulator.app.resources;

import eu.cise.emulator.app.candidate.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;

@Path("/sim-LSA/CISEMessageService")
@Produces(MediaType.TEXT_PLAIN)
//@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MessageSoapWsdlDefinition {
    private static final String NOT_INICIATED = "NOT_INICIATED";
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSoapWsdlDefinition.class);
    private static final MessageSoapWsdlDefinition instance =new MessageSoapWsdlDefinition(NOT_INICIATED);
    private String Content;

    private MessageSoapWsdlDefinition( String content) {
        this.Content = content;
    }
    protected String getContent(){
        return Content;
    }
    protected void setContent(String acontent){
        this.Content = acontent;
    }

    public static  MessageSoapWsdlDefinition getInstance(){
        if (instance.getContent().equals(NOT_INICIATED)){
            StringBuffer futurContent=new StringBuffer();
//            ClassLoader classLoader = MessageSoapWsdlDefinition.class.getClassLoader();
//            File refWsdl= null;
//            String filename = "META-INF/services/message.wsld";
//            URL resource = classLoader.getResource(filename);
//            if (resource == null) {
//                throw new IllegalArgumentException("file is not found!");
//            } else {
//                refWsdl= new File(resource.getFile());
//            }
//
//
//
//            if (refWsdl.canRead()){
//                BufferedReader reader;
//                try {
//                    reader = new BufferedReader(new FileReader(refWsdl.getAbsoluteFile()));
//                    String line = reader.readLine();
//                    while (line != null) {
//                        futurContent.append(line);
//                        line = reader.readLine();
//                    }
//                    reader.close();
//                } catch (IOException e) {
//                    throw  new UncheckedExecutionException(new ConfigurationException("could not read file message.wsld"));
//                }
//
//            }else{
//             throw  new UncheckedExecutionException(new ConfigurationException("could not find file message.wsld"));
//           }
            futurContent.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<wsdl:definitions xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:tns=\"http://www.cise.eu/accesspoint/service/v1/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:ns1=\"http://schemas.xmlsoap.org/soap/http\" name=\"CISEMessageService\" targetNamespace=\"http://www.cise.eu/accesspoint/service/v1/\">\n" +
                    "\n" +
                    "\n" +
                    "    <wsdl:types>\n" +
                    "\n" +
                    "\n" +
                    "        <xs:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:tns=\"http://www.cise.eu/servicemodel/v1/service/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:ns1=\"http://www.cise.eu/servicemodel/v1/authority/\" targetNamespace=\"http://www.cise.eu/servicemodel/v1/service/\" version=\"1.0\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:import namespace=\"http://www.cise.eu/servicemodel/v1/authority/\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"Service\" type=\"tns:Service\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"ServiceProfile\" type=\"tns:ServiceProfile\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"SubscriptionCapability\" type=\"tns:SubscriptionCapability\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"Service\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"DataFreshness\" type=\"tns:DataFreshnessType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"SeaBasin\" type=\"ns1:SeaBasinType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"ServiceID\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"ServiceOperation\" type=\"tns:ServiceOperationType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ServiceRole\" type=\"tns:ServiceRoleType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ServiceStatus\" type=\"tns:ServiceStatusType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ServiceType\" type=\"tns:ServiceType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Participant\" type=\"ns1:Participant\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ServiceCapability\" type=\"tns:ServiceCapability\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"ServiceCapability\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ExpectedResponseTime\" type=\"xs:int\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"MaxEntitiesPerMsg\" type=\"xs:int\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"MaxNumberOfRequests\" type=\"xs:int\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"QueryByExampleType\" type=\"tns:QueryByExampleType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"SubscriptionCapability\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:extension base=\"tns:ServiceCapability\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element minOccurs=\"0\" name=\"MaxFrequency\" type=\"xs:duration\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element minOccurs=\"0\" name=\"RefreshRate\" type=\"xs:duration\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element minOccurs=\"0\" name=\"SubscriptionEnd\" type=\"xs:dateTime\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    </xs:extension>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"ServiceProfile\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Community\" type=\"ns1:CommunityType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Country\" type=\"ns1:CountryType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"DataFreshness\" type=\"tns:DataFreshnessType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Function\" type=\"ns1:FunctionType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"SeaBasin\" type=\"ns1:SeaBasinType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ServiceID\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ServiceOperation\" type=\"tns:ServiceOperationType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ServiceRole\" type=\"tns:ServiceRoleType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ServiceType\" type=\"tns:ServiceType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ServiceStatus\" type=\"tns:ServiceStatusType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ServiceCapability\" type=\"tns:ServiceCapability\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"DataFreshnessType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Historic\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"RealTime\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NearlyRealTime\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Unknown\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"ServiceOperationType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Pull\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Push\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Subscribe\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Acknowledgement\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Feedback\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"ServiceRoleType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Consumer\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Provider\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"ServiceStatusType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Draft\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Online\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Maintenance\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Deprecated\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Offline\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"ServiceType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ActionService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AgentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AircraftService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AnomalyService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CargoDocumentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CargoService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CertificateDocumentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CrisisIncidentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"DocumentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EventDocumentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IncidentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IrregularMigrationIncidentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LandVehicleService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LawInfringementIncidentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LocationService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LocationDocumentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MaritimeSafetyIncidentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MeteoOceanographicConditionService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MovementService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"OperationalAssetService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"OrganizationService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"OrganizationDocumentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PersonService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PersonDocumentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"RiskDocumentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"RiskService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"VesselDocumentService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"VesselService\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"QueryByExampleType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BestEffort\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ExactSearch\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        </xs:schema>\n" +
                    "\n" +
                    "\n" +
                    "        <xs:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:tns=\"http://www.cise.eu/servicemodel/v1/message/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:ns1=\"http://www.cise.eu/servicemodel/v1/service/\" targetNamespace=\"http://www.cise.eu/servicemodel/v1/message/\" version=\"1.0\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:import namespace=\"http://www.cise.eu/servicemodel/v1/service/\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:import namespace=\"http://www.cise.eu/servicemodel/v1/authority/\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"Acknowledgement\" type=\"tns:Acknowledgement\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"EncryptedEntityPayload\" type=\"tns:EncryptedEntityPayload\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"Feedback\" type=\"tns:Feedback\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"PullRequest\" type=\"tns:PullRequest\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"PullResponse\" type=\"tns:PullResponse\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"Push\" type=\"tns:Push\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"ReliabilityProfile\" type=\"tns:ReliabilityProfile\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"XmlEntityPayload\" type=\"tns:XmlEntityPayload\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType abstract=\"true\" name=\"Message\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ContextID\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"CorrelationID\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"CreationDateTime\" type=\"xs:dateTime\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"MessageID\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"Priority\" type=\"tns:PriorityType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"RequiresAck\" type=\"xs:boolean\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"Sender\" type=\"ns1:Service\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Recipient\" type=\"ns1:Service\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Payload\" type=\"tns:CoreEntityPayload\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Reliability\" type=\"tns:ReliabilityProfile\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"CcRecipients\" type=\"ns1:Service\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:any namespace=\"##other\" processContents=\"skip\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType abstract=\"true\" name=\"CoreEntityPayload\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"InformationSecurityLevel\" type=\"tns:InformationSecurityLevelType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"InformationSensitivity\" type=\"tns:InformationSensitivityType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"IsPersonalData\" type=\"xs:boolean\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"Purpose\" type=\"tns:PurposeType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"RetentionPeriod\" type=\"xs:dateTime\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"EnsureEncryption\" type=\"xs:boolean\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"XmlEntityPayload\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:extension base=\"tns:CoreEntityPayload\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:any maxOccurs=\"unbounded\" minOccurs=\"0\" namespace=\"##other\" processContents=\"lax\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    </xs:extension>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"EncryptedEntityPayload\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:extension base=\"tns:CoreEntityPayload\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element name=\"Entities\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    </xs:extension>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"ReliabilityProfile\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"RetryStrategy\" type=\"tns:RetryStrategyType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"Push\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:extension base=\"tns:Message\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"DiscoveryProfiles\" type=\"ns1:ServiceProfile\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    </xs:extension>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"PullResponse\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:extension base=\"tns:Message\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element minOccurs=\"0\" name=\"ErrorDetail\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element name=\"ResultCode\" type=\"tns:ResponseCodeType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element minOccurs=\"0\" name=\"Fulfils\" type=\"ns1:ServiceCapability\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    </xs:extension>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"PullRequest\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:extension base=\"tns:Message\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element name=\"PullType\" type=\"tns:PullType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element minOccurs=\"0\" name=\"ResponseTimeOut\" type=\"xs:int\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element minOccurs=\"0\" name=\"Requests\" type=\"ns1:ServiceCapability\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"DiscoveryProfiles\" type=\"ns1:ServiceProfile\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element minOccurs=\"0\" name=\"PayloadSelector\" type=\"tns:PayloadSelector\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    </xs:extension>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"PayloadSelector\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element maxOccurs=\"unbounded\" name=\"Selectors\" type=\"tns:SelectorCondition\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"SelectorCondition\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"Selector\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element name=\"Operator\" type=\"tns:ConditionOperatorType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"Feedback\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:extension base=\"tns:Message\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element name=\"FeedbackType\" type=\"tns:FeedbackType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element minOccurs=\"0\" name=\"Reason\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element name=\"RefMessageID\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    </xs:extension>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"Acknowledgement\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:extension base=\"tns:Message\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element name=\"AckCode\" type=\"tns:AcknowledgementType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element minOccurs=\"0\" name=\"AckDetail\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                            <xs:element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"DiscoveredServices\" type=\"ns1:Service\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                        </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    </xs:extension>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:complexContent>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"PriorityType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Low\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Medium\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"High\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"InformationSecurityLevelType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EUTopSecret\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EUSecret\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EUConfidential\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EURestricted\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NonClassified\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NonSpecified\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"InformationSensitivityType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Red\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Amber\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Green\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"White\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NonSpecified\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"PurposeType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"VTM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Safety\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Security\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SAR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Operation\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FisheriesWarning\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FisheriesMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FisheriesOperation\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EnvironmentMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EnvironmentWarning\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EnvironmentResponse\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CustomsMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CustomsOperation\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BorderMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BorderOperation\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LawEnforcementMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LawEnforcementOperation\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"DefenceMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CounterTerrorism\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CSDPTask\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NonSpecified\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"RetryStrategyType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NoRetry\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LowReliability\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"HighReliability\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"ResponseCodeType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Success\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EndPointNotFound\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SecurityError\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"InternalGatewayFault\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"InvalidRequestObject\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Unauthorized\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BadRequest\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ServiceTypeNotSupported\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EntityTypeNotAccepted\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ObjectTypeNotAccepted\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ServerError\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TimestampError\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AuthenticationError\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"PullType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GetSubscribers\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Discover\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Request\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Subscribe\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Unsubscribe\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"ConditionOperatorType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EQUAL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NOT_EQUAL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LIKE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NOT_LIKE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IS_NULL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GREATER_THAN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GREATER_THAN_OR_EQUAL_TO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LESS_THAN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LESS_THAN_OR_EQUAL_TO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"FeedbackType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"info\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"delete\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"AcknowledgementType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Success\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EndPointNotFound\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SecurityError\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"InternalGatewayFault\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"InvalidRequestObject\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Unauthorized\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BadRequest\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ServiceTypeNotSupported\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EntityTypeNotAccepted\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ObjectTypeNotAccepted\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ServerError\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TimestampError\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AuthenticationError\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NetworkError\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ServiceManagerError\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        </xs:schema>\n" +
                    "\n" +
                    "\n" +
                    "        <xs:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:tns=\"http://www.cise.eu/servicemodel/v1/authority/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:ns1=\"http://schemas.xmlsoap.org/soap/http\" targetNamespace=\"http://www.cise.eu/servicemodel/v1/authority/\" version=\"1.0\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"Gateway\" type=\"tns:Gateway\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"Participant\" type=\"tns:Participant\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"PointOfContact\" type=\"tns:PointOfContact\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"Participant\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Id\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Name\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Description\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"ClassificationLevel\" type=\"tns:ClassificationLevelType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"EndpointUrl\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"EndpointType\" type=\"tns:EndpointType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"ProvidedServicesIds\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Gateway\" type=\"tns:Gateway\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Owner\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"PointOfContact\" type=\"tns:PointOfContact\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"AreasOfInterest\" type=\"tns:SeaBasinType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"Communities\" type=\"tns:CommunityType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element maxOccurs=\"unbounded\" minOccurs=\"0\" name=\"Functions\" type=\"tns:FunctionType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"MemberState\" type=\"tns:CountryType\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"Gateway\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Id\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"PointOfContact\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Name\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Surname\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Address\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Telephone\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"Email\" type=\"xs:string\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"SeaBasinType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Atlantic\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BalticSea\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NorthSea\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Mediterranean\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BlackSea\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"OutermostRegions\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ArcticOcean\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NonSpecified\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"ClassificationLevelType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Unclassified\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EURestricted\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"EndpointType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"REST\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SOAP\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"CommunityType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GeneralLawEnforcement\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Customs\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MarineEnvironment\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MaritimeSafetySecurity\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"DefenceMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FisheriesControl\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BorderControl\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Other\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NonSpecified\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"FunctionType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"VTM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Safety\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Security\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SAR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"Operation\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FisheriesWarning\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FisheriesMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FisheriesOperation\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EnvironmentMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EnvironmentWarning\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EnvironmentResponse\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CustomsMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CustomsOperation\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BorderMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BorderOperation\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LawEnforcementMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LawEnforcementOperation\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"DefenceMonitoring\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CounterTerrorism\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CSDPTask\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NonSpecified\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:simpleType name=\"CountryType\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:restriction base=\"xs:string\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AF\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AX\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"DZ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AS\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AD\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AI\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AQ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AW\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AU\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AT\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AZ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BS\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BH\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BD\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BB\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BY\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BZ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BJ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BT\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BQ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BW\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BV\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BF\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BI\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CV\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"KH\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"KY\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CF\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TD\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CX\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CC\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"KM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CD\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CK\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CI\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"HR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CU\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CW\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CY\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CZ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"DK\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"DJ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"DM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"DO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EC\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SV\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GQ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ER\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ET\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FK\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FJ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FI\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GF\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PF\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TF\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"DE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GH\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GI\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GD\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GP\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GU\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GT\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GW\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GY\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"HT\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"HM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"VA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"HN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"HK\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"HU\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IS\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ID\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IQ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"IT\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"JM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"JP\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"JE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"JO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"KZ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"KE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"KI\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"KP\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"KR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"KW\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"KG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LV\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LB\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LS\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LY\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LI\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LT\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LU\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MK\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MW\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MY\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MV\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ML\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MT\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MH\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MQ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MU\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"YT\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MX\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"FM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MD\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MC\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ME\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MS\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MZ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NP\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NC\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NZ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NI\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NU\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NF\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MP\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"NO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"OM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PK\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PW\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PS\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PY\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PH\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PT\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"QA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"RE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"RO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"RU\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"RW\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"BL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SH\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"KN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LC\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"MF\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"PM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"VC\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"WS\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ST\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"RS\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SC\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SX\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SK\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SI\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SB\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ZA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GS\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SS\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ES\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"LK\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SD\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SJ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SZ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"CH\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"SY\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TW\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TJ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TZ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TH\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TL\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TK\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TO\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TT\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TR\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TC\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"TV\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"UG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"UA\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"AE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"GB\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"UM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"US\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"UY\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"UZ\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"VU\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"VE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"VN\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"VG\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"VI\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"WF\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EH\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"YE\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ZM\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"ZW\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"EU\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:enumeration value=\"RC\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:restriction>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:simpleType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        </xs:schema>\n" +
                    "\n" +
                    "\n" +
                    "        <xs:schema xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" xmlns:tns=\"http://www.cise.eu/accesspoint/service/v1/\" xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" xmlns:ns2=\"http://www.cise.eu/servicemodel/v1/service/\" xmlns:ns1=\"http://www.cise.eu/servicemodel/v1/message/\" elementFormDefault=\"unqualified\" targetNamespace=\"http://www.cise.eu/accesspoint/service/v1/\" version=\"1.0\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:import namespace=\"http://www.cise.eu/servicemodel/v1/message/\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:import namespace=\"http://www.cise.eu/servicemodel/v1/service/\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"message\" type=\"ns1:Message\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"send\" type=\"tns:send\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:element name=\"sendResponse\" type=\"tns:sendResponse\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"send\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"message\" type=\"ns1:Message\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            <xs:complexType name=\"sendResponse\">\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                <xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                    <xs:element minOccurs=\"0\" name=\"return\" type=\"ns1:Acknowledgement\"/>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "                </xs:sequence>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "            </xs:complexType>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "        </xs:schema>\n" +
                    "\n" +
                    "\n" +
                    "    </wsdl:types>\n" +
                    "\n" +
                    "\n" +
                    "    <wsdl:message name=\"sendResponse\">\n" +
                    "\n" +
                    "\n" +
                    "        <wsdl:part element=\"tns:sendResponse\" name=\"parameters\">\n" +
                    "\n" +
                    "\n" +
                    "        </wsdl:part>\n" +
                    "\n" +
                    "\n" +
                    "    </wsdl:message>\n" +
                    "\n" +
                    "\n" +
                    "    <wsdl:message name=\"send\">\n" +
                    "\n" +
                    "\n" +
                    "        <wsdl:part element=\"tns:send\" name=\"parameters\">\n" +
                    "\n" +
                    "\n" +
                    "        </wsdl:part>\n" +
                    "\n" +
                    "\n" +
                    "    </wsdl:message>\n" +
                    "\n" +
                    "\n" +
                    "    <wsdl:portType name=\"CISEMessageServiceSoapImpl\">\n" +
                    "\n" +
                    "\n" +
                    "        <wsdl:operation name=\"send\">\n" +
                    "\n" +
                    "\n" +
                    "            <wsdl:input message=\"tns:send\" name=\"send\">\n" +
                    "\n" +
                    "\n" +
                    "            </wsdl:input>\n" +
                    "\n" +
                    "\n" +
                    "            <wsdl:output message=\"tns:sendResponse\" name=\"sendResponse\">\n" +
                    "\n" +
                    "\n" +
                    "            </wsdl:output>\n" +
                    "\n" +
                    "\n" +
                    "        </wsdl:operation>\n" +
                    "\n" +
                    "\n" +
                    "    </wsdl:portType>\n" +
                    "\n" +
                    "\n" +
                    "    <wsdl:binding name=\"CISEMessageServiceSoapBinding\" type=\"tns:CISEMessageServiceSoapImpl\">\n" +
                    "\n" +
                    "\n" +
                    "        <soap:binding style=\"document\" transport=\"http://schemas.xmlsoap.org/soap/http\"/>\n" +
                    "\n" +
                    "\n" +
                    "        <wsdl:operation name=\"send\">\n" +
                    "\n" +
                    "\n" +
                    "            <soap:operation soapAction=\"\" style=\"document\"/>\n" +
                    "\n" +
                    "\n" +
                    "            <wsdl:input name=\"send\">\n" +
                    "\n" +
                    "\n" +
                    "                <soap:body use=\"literal\"/>\n" +
                    "\n" +
                    "\n" +
                    "            </wsdl:input>\n" +
                    "\n" +
                    "\n" +
                    "            <wsdl:output name=\"sendResponse\">\n" +
                    "\n" +
                    "\n" +
                    "                <soap:body use=\"literal\"/>\n" +
                    "\n" +
                    "\n" +
                    "            </wsdl:output>\n" +
                    "\n" +
                    "\n" +
                    "        </wsdl:operation>\n" +
                    "\n" +
                    "\n" +
                    "    </wsdl:binding>\n" +
                    "\n" +
                    "\n" +
                    "    <wsdl:service name=\"CISEMessageService\">\n" +
                    "\n" +
                    "\n" +
                    "        <wsdl:port binding=\"tns:CISEMessageServiceSoapBinding\" name=\"CISEMessageServiceSoapPort\">\n" +
                    "\n" +
                    "\n" +
                    "            <soap:address location=\"http://localhost:8080/sim-LSA/CISEMessageService\"/>\n" +
                    "\n" +
                    "\n" +
                    "        </wsdl:port>\n" +
                    "\n" +
                    "\n" +
                    "    </wsdl:service>\n" +
                    "\n" +
                    "\n" +
                    "</wsdl:definitions>");
            instance.setContent(futurContent.toString());
        }

        return instance;
    }

    @GET
    @Path("ping")
    public String getServerTime() {
        System.out.println("RESTful Service 'MessageService' is running ==> ping");
        return "received ping on "+new Date().toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_XML)
    public Message getMessageResponseInJSON(@QueryParam("?wsdl") String something) {
        Message content= new Message();
        content.setBody(""+something);
        content.setId(1);

        return content; //do not use Response object because this causes issues when generating XML automatically

    }


    @POST
    public String createMessageResponseInXml( String content) {
        LOGGER.info("Received a message: {}", content);
        return content;
    }

}
