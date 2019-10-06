import {observable} from 'mobx'

var messageCandidate = observable({
    messageId: "",
    messageState: "virtual",
    correlationId: "",
    messageType: "MASTER",
    templateService: "#None",
    templatePayload: "#None",
    asyncAcknowledge: false
});
export default messageCandidate;

