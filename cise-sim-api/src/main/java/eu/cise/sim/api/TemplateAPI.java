package eu.cise.sim.api;

import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.api.representation.TemplateParams;
import eu.cise.sim.exceptions.LoaderEx;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.cise.servicemodel.v1.message.Message;
import eu.eucise.xml.XmlMapper;

public class TemplateAPI {

    private final MessageProcessor messageProcessor;
    private final TemplateLoader templateLoader;
    private final XmlMapper xmlMapper;
    private final XmlMapper xmlMapperPrettyNotValidating;

    public TemplateAPI(
        MessageProcessor messageProcessor,
        TemplateLoader templateLoader,
        XmlMapper xmlMapper, XmlMapper xmlMapperPrettyNotValidating) {

        this.messageProcessor = messageProcessor;
        this.templateLoader = templateLoader;
        this.xmlMapper = xmlMapper;
        this.xmlMapperPrettyNotValidating = xmlMapperPrettyNotValidating;
    }

    public PreviewResponse preview(TemplateParams templateParams) {
        try {

            Template template = templateLoader.loadTemplate(templateParams.getTemplateId());

            Message preparedMessage = messageProcessor
                .preview(xmlMapper.fromXML(template.getTemplateContent()),
                    templateParams.getSendParams());

            Template templateWithPreparedMessage = new Template(templateParams.getTemplateId(),
                template.getTemplateName(), xmlMapperPrettyNotValidating.toXML(preparedMessage));

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
