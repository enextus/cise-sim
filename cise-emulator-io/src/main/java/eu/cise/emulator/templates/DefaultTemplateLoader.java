package eu.cise.emulator.templates;

import eu.cise.servicemodel.v1.message.Push;
import eu.eucise.xml.DefaultXmlMapper;
import eu.eucise.xml.XmlMapper;

import java.util.List;

import static eu.eucise.helpers.PushBuilder.newPush;

public class DefaultTemplateLoader implements TemplateLoader {

    private final XmlMapper xmlMapper;

    public DefaultTemplateLoader(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    @Override
    public Template loadTemplate(String templateId) {
        Push fakeMessage = newPush().id("messageId").correlationId("correlationId").build();
        XmlMapper nonValidatingXmlMapper = new DefaultXmlMapper.PrettyNotValidating();
        return new Template(templateId, "templateName", nonValidatingXmlMapper.toXML(fakeMessage));
    }

    @Override
    public List<Template> loadTemplateList() {
        return null;
    }
}
