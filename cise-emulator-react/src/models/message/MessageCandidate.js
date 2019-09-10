import {observable} from 'mobx'
import MessageType from "../../components/Panels/SendMessage/MessageType";

var messageCandidate = observable({
    messageId : "",
    messageState : "virtual",
    correlationId : "",
    messageType : MessageType.MASTER_OUT,
    templateService : "#none",
    templatePayload : "#none",
    asyncAcknowledge : false
});
export default messageCandidate;

