package eu.cise.sim.api;

import eu.cise.servicemodel.v1.message.Message;
import eu.cise.sim.api.representation.TemplateParams;
import eu.cise.sim.engine.MessageProcessor;
import eu.cise.sim.exceptions.LoaderEx;
import eu.cise.sim.templates.Template;
import eu.cise.sim.templates.TemplateLoader;
import eu.eucise.xml.XmlMapper;

import java.util.List;

public class DefaultTemplateAPI implements TemplateAPI {

    private final MessageProcessor messageProcessor;
    private final TemplateLoader templateLoader;
    private final XmlMapper xmlMapper;
    private final XmlMapper xmlMapperPrettyNotValidating;

    public DefaultTemplateAPI(
        MessageProcessor messageProcessor,
        TemplateLoader templateLoader,
        XmlMapper xmlMapper,
        XmlMapper xmlMapperPrettyNotValidating) {

        this.messageProcessor = messageProcessor;
        this.templateLoader = templateLoader;
        this.xmlMapper = xmlMapper;
        this.xmlMapperPrettyNotValidating = xmlMapperPrettyNotValidating;
    }

    @Override
    public ResponseApi<Template> preview(TemplateParams templateParams) {
        try {

            Template template = templateLoader.loadTemplate(templateParams.getTemplateId());

            Message preparedMessage = messageProcessor.preview(xmlMapper.fromXML(template.getTemplateContent()), templateParams.getSendParams());

            Template templateWithPreparedMessage = new Template(templateParams.getTemplateId(),
                                                                template.getTemplateName(),
                                                                xmlMapperPrettyNotValidating.toXML(preparedMessage));

            return new ResponseApi<>(templateWithPreparedMessage);
        } catch (Exception e) {
            return new ResponseApi<>(ResponseApi.ErrorId.FATAL, e.getMessage());
        }

    }

    @Override
    public ResponseApi<List<Template>> getTemplates() {
        try {
            return new ResponseApi<>(templateLoader.loadTemplateList());
        } catch (LoaderEx e) {
            return new ResponseApi<>(ResponseApi.ErrorId.FATAL, e.getMessage());
        }
    }

}
