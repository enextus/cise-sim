package eu.cise.emulator.validator;
import com.github.mfatihercik.dsb.DSM;
import com.github.mfatihercik.dsb.DSMBuilder
import spock.lang.Shared
import spock.lang.Unroll
import spock.lang.Ignore
import java.io.IOException;
import java.util.*;

@Ignore
class ServicePayloadBindingvalidatorSpec extends spock.lang.Specification{


    @Shared
    private ServicePayloadBindingvalidator avalidator= new ServicePayloadBindingvalidator();



    @Unroll
    def "validate the content Type linked to the ServiceType"() {
        expect:
        avalidator.isConformContentTypeBinding(aContentType,aServiceType) == aResult;

        where:
        aContentType   | aServiceType    | aResult
        "Action" | "ActionService" |  true
        "Vessel" | "VesselService" |  true
        "Organization" | "OrganizationService" |  true
        "FormalOrganization" | "OrganizationService" |  true
    }

    def "doesnt validate content Type  not linked to the ServiceType"() {
        expect:
        avalidator.isConformContentTypeBinding(aContentType,aServiceType) == aResult;

        where:
        aContentType   | aServiceType    | aResult
        "Action" | "VesselService" |  false
        "Vessel" | "ActionService" |  false
        "Organization" | "ActionService" |  false
        "FormalOrganization" | "ActionService" |  false
    }

}