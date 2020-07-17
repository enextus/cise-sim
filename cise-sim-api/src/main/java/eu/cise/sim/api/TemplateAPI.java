package eu.cise.sim.api;

import eu.cise.sim.api.representation.TemplateParams;
import eu.cise.sim.templates.Template;

import java.util.List;

public interface TemplateAPI {

    ResponseApi<Template>       preview(TemplateParams templateParams);
    ResponseApi<List<Template>> getTemplates();

}
