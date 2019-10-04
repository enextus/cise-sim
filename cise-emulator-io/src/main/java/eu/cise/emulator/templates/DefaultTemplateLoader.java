package eu.cise.emulator.templates;

import eu.cise.servicemodel.v1.message.*;
import eu.cise.servicemodel.v1.service.ServiceOperationType;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;

import java.util.Date;
import java.util.List;

import static eu.eucise.helpers.PushBuilder.newPush;
import static eu.eucise.helpers.ServiceBuilder.newService;

public class DefaultTemplateLoader implements TemplateLoader {

    private final XmlMapper xmlMapper;

    public DefaultTemplateLoader(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    @Override
    public Template loadTemplate(String templateId) {
        Push fakeMessage = newPush()
                .id("mesageId")
                .correlationId("correlation-id")
                .creationDateTime(new Date())
                .priority(PriorityType.HIGH)
                .informationSecurityLevel(InformationSecurityLevelType.NON_CLASSIFIED)
                .informationSensitivity(InformationSensitivityType.GREEN)
                .setEncryptedPayload("false")
                .isPersonalData(false)
                .purpose(PurposeType.NON_SPECIFIED)
                .sender(newService().id("service-id").operation(ServiceOperationType.PUSH).build())
                .build();;
        XmlMapper nonValidatingXmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        return new Template(templateId, "templateName", nonValidatingXmlMapper.toXML(fakeMessage));
    }

    @Override
    public List<Template> loadTemplateList() {
        return null;
    }
}
