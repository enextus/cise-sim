import {computed, observable, action} from 'mobx'
import axios from "axios";

export default class MessagePreview {

    @observable   previewContent= "";
    @observable   acknowledgeContent="";
    @observable   errorContent= "";

    @computed
    get status() {
        if (this.errorContent=="") {

            if (this.acknowledgeContent=="") {
                return "preview";
            } else {
                return "sent";
            }
        } else {
            return "error";
        }
    }

    @action
    preview(messageCandidate) {
        //clarify param
        let template = messageCandidate.templateService.value;
        let payload = messageCandidate.templatePayload.value;
        let asyncAcknowledge = messageCandidate.asyncAcknowledge;
        let correlationId = messageCandidate.correlationId;
        //call remote service
        let urlmake="http://localhost:8080/webapi/preview/"+template+"?";
        urlmake= urlmake +((payload!="")?("payload="+payload):"");
        urlmake= urlmake +((asyncAcknowledge!="")?("&request_async_ack="+asyncAcknowledge):"");
        urlmake= urlmake +((correlationId!="")?("&consolidation_id="+correlationId):"");
        urlmake= urlmake +((messageCandidate.messageId!="")?("&message_id="+messageCandidate.messageId):"");
        axios.get(urlmake)
            .then((response) => {
                console.debug("PREVIEW CALL SUCCESS !!! status : ", response.data[0].status," /--/  body :  " , response.data[0].body," /--/ ack :   " , response.data[0].ack," /--/   "  );
                this.previewContent= response.data[0].body;
                this.acknowledgeContent = response.data[0].ack;
                this.errorContent = response.data[0].status;
            })
            .catch((err) => {
                let errortxt= ("Some errors occur in phase \"web api call\"; with detail... \n" +
                " proposed url "+urlmake+"reject the call with :", err);
                console.error(errortxt);
                this.errorContent = errortxt;
                this.acknowledgeContent = "";
                this.errorContent = "";
            })
        //update content
    }

    @action
    send(messageCandidate) {
        //clarify param
        let template = messageCandidate.templateService.value;
        let payload = messageCandidate.templatePayload.value;
        let asyncAcknowledge = messageCandidate.asyncAcknowledge;
        let correlationId = messageCandidate.correlationId;
        //call remote service
        let urlmake="http://localhost:8080/webapi/send/"+template+"?";
        urlmake= urlmake +((payload!="")?("payload="+payload):"");
        urlmake= urlmake +((asyncAcknowledge!="")?("&request_async_ack="+asyncAcknowledge):"");
        urlmake= urlmake +((correlationId!="")?("&consolidation_id="+correlationId):"");
        urlmake= urlmake +((messageCandidate.messageId!="")?("&message_id="+messageCandidate.messageId):"");
        axios.get(urlmake)
            .then((response) => {
                console.debug("PREVIEW CALL SUCCESS !!! status : ", response.data[0].status," /--/  body :  " , response.data[0].body," /--/ ack :   " , response.data[0].ack," /--/   "  );
                this.previewContent= response.data[0].body;
                this.acknowledgeContent = response.data[0].ack;
                this.errorContent = response.data[0].status;
            })
            .catch((err) => {
                let errortxt= ("Some errors occur in phase \"web api call\"; with detail... \n" +
                    " proposed url "+urlmake+"reject the call with :", err);
                console.error(errortxt);
                this.errorContent = errortxt;
                this.acknowledgeContent = "";
                this.errorContent = "";
            })
        //update content
        ;
    }

    @action
    error(messageCandidate) {
        let template = messageCandidate.templateService;
        let payload = messageCandidate.templatePayload;
        let asyncAcknowledge = messageCandidate.asyncAcknowledge;
        let correlationId = messageCandidate.correlationId;
        //fake to pass error type result (testing)
        //update content (testing)
        this.previewContent = "";
        this.acknowledgeContent = "";
        this.errorContent = "Some errors occur * ; with detail... "
            +"\n proposed message "+messageCandidate.messageId
            +"\n asyncAcknowledge:"+ asyncAcknowledge
            +"\n correlationId:"+ correlationId
            +"\n templateService:"+ template.value + " / "+template.label
            +"\n templatePayload:"+ payload.value + " / "+payload.label ;
    }
}


