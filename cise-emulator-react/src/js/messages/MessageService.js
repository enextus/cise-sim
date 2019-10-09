// defaultPostData = {
//     'message_template': '',
//     'params': {
//         'message_id': '',
//         'requires_ack': false
//     }
// };
import {post} from '../api/API'

export async function sendMessage(templateId, messageId, correlationId, requiresAck) {
    try {
        console.log("sendMessage");
        const message = await post("templates",
            {
                message_template: templateId,
                params: {
                    message_id: messageId,
                    correlation_id: correlationId,
                    requires_ack: requiresAck,
                }
            }
        );
        console.log("sendMessage", message);
        return message;
    } catch (e) {
        console.error("sendMessage", e);
    }

}