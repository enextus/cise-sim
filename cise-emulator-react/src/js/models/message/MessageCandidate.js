import {observable} from 'mobx'
import MessageType from "../../components/Panels/SendMessage/MessageType";

var messageCandidate = observable({
    messageId : "",
    messageState : "virtual",
    correlationId : "",
    messageType : MessageType.MASTER_OUT,
    templateService : "#None",
    templatePayload : "#None",
    asyncAcknowledge : false
});
export default messageCandidate;

