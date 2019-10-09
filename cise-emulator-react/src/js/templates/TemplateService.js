import {get} from '../api/API'
import Template from "./Template";

export async function getTemplateList() {
    const templates = await get("templates");
    return templates.map(t => new Template(t));
}

// requestAck must be transformed in requiresAck
export async function getTemplateById(templateId, messageId, correlationId, requiresAck) {
    console.log("getTemplateById");
    const template = await get(`templates/${templateId}`,
        {
            messageId: messageId,
            correlationId: correlationId,
            requestAck: requiresAck
        }
    );
    console.log("getTemplateById", template);

    if(template.code !== undefined){
        return template;
    }

    return new Template(template);

}