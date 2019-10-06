import {get} from '../api/API'
import Template from "./Template";

export async function getTemplateList() {
    const templates = await get("templates");
    return templates.map(t => new Template(t));
}

export function getTemplateById(templateId, messageId, correlationId, requestAck) {
    return new API().get(`templates/${id}`,
        {
            messageId: messageId,
            correlationId: correlationId,
            requestAck: requestAck
        }
    );
}