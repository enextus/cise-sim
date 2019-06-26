package eu.cise.emulator.app.resource;

import ch.qos.logback.classic.Logger;
import eu.cise.emulator.app.CiseEmuApplication;
import eu.cise.emulator.app.core.InstanceID;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/sim-LSA/rest/")
public class InboundRESTMessageService {
    String version;
    InstanceID instanceID;

        public InboundRESTMessageService(String version, InstanceID instanceID) {
            this.version=version;
            this.instanceID=instanceID;
        }



    @POST
    @Consumes("text/plain,text/xml,application/xml")
    @Produces ("text/plain")
    @Path("/CISEMessageServiceREST")
    public String sendMessage(String msg) throws Exception {

        Logger mylogger= (Logger) LoggerFactory.getLogger(InboundRESTMessageService.class.getName());
        // TODO: implement the xml rest reception
        /*XmlMapper mapper = new DefaultXmlMapper.Pretty();
        String completedMessage = mapper.toXML(someMessage ).toString();
        return someMessage;*/
        mylogger.debug( "CISEMessageServiceRESTXML received POST message"+msg);
        return "Received@"+ CiseEmuApplication.getMemberId() + ":"+msg;
    }
}