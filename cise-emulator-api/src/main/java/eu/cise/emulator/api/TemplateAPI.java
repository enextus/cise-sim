package eu.cise.emulator.api;

import eu.cise.emulator.MessageProcessor;
import eu.cise.emulator.api.representation.TemplateParams;
import eu.cise.emulator.exceptions.LoaderEx;
import eu.cise.emulator.templates.Template;
import eu.cise.emulator.templates.TemplateLoader;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.XmlMapper;

public class TemplateAPI {

    private final MessageProcessor messageProcessor;
    private final TemplateLoader templateLoader;
    private final XmlMapper xmlMapper;

    public TemplateAPI(
        MessageProcessor messageProcessor,
        TemplateLoader templateLoader,
        XmlMapper xmlMapper) {

        this.messageProcessor = messageProcessor;
        this.templateLoader = templateLoader;
        this.xmlMapper = xmlMapper;
    }

    public PreviewResponse preview(TemplateParams templateParams) {
        try {

            Template template = templateLoader.loadTemplate(templateParams.getTemplateId());

            Message preparedMessage = messageProcessor
                .preview(xmlMapper.fromXML(template.getTemplateContent()),
                    templateParams.getSendParams());

            Template templateWithPreparedMessage = new Template(templateParams.getTemplateId(),
                template.getTemplateName(), xmlMapper.toXML(preparedMessage));

            return new PreviewResponse.OK(templateWithPreparedMessage);
        } catch (Exception e) {
            return new PreviewResponse.KO(e.getMessage());
        }

    }

    public TemplateListResponse getTemplates() {
        try {
            return new TemplateListResponse.OK(templateLoader.loadTemplateList());
        } catch (LoaderEx e) {
            return new TemplateListResponse.KO(e.getMessage());
        }
    }

}
