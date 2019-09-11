package eu.cise.emulator.validator;

import java.util.Arrays;

public class BoundService {

    private String serviceName;
    private String[] contentTypes;

    public BoundService() {
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

    public boolean asContentType(String aContentType) {
        boolean result = Arrays.stream(contentTypes).anyMatch(aContentType::equals);
        return result;
    }

    public void setContentTypes(String[] contentTypes) {
        this.contentTypes = contentTypes;
    }

}
