import {computed, observable, action} from 'mobx'
import axios from "axios";

export default class MessagePushAPI {

    @observable   previewContent= "";
    @observable   acknowledgeContent="";
    @observable   errorContent= "";
    defaultPostConfig = {
        method: 'post',
        header: {
            'Content-Type': 'application/json'
        }};
    defaultPostURL ='http://localhost:8080/api/messages';
    defaultPostData = {
    'message_template': '',
    'params': {
        'message_id': '',
        'requires_ack': false
    }
    };

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
        const valuePostData = JSON.parse(JSON.stringify(this.defaultPostData))
        valuePostData.message_template = messageCandidate.templateService.value;
        if (messageCandidate.templatePayload.value != "#none")    valuePostData.message_payload = messageCandidate.templatePayload.value;
        valuePostData.params.message_id = messageCandidate.messageId;
        valuePostData.params.correlation_id = (messageCandidate.correlationId=""? messageId : messageCandidate.correlationId);

        if (messageCandidate.asyncAcknowledge !== undefined && messageCandidate.asyncAcknowledge.value !== undefined ){
            aSourceValue == messageCandidate.asyncAcknowledge.valueOf()
            valuePostData.params.requires_ack = aSourceValue;
        }


        console.debug("ready preview: ", valuePostData);
        axios.post(this.defaultPostURL,valuePostData,this.defaultPostConfig)
            .then((response) => {
                console.debug("PREVIEW CALL SUCCESS !!! status : ", response.data[0].status," /--/  body :  " , response.data[0].body," /--/ ack :   " , response.data[0].ack," /--/   "  );
                this.previewContent= response.data[0].body;
                this.acknowledgeContent = response.data[0].ack;
                this.errorContent = response.data[0].status;
            })
            .catch((err) => {
                let errortxt= ("Errors occur in PREVIEW phase \"web api call\"; with detail... \n" +
                " proposed url "+valuePostData.url+"reject the call with :", err);
                console.error(errortxt);
                this.errorContent = errortxt;
                this.acknowledgeContent = "";
                this.errorContent = "";
            })
    }

    @action
    send(messageCandidate) {
        const valuePostData = JSON.parse(JSON.stringify(this.defaultPostData))
        valuePostData.message_template = messageCandidate.templateService.value;
        if (messageCandidate.templatePayload.value != "#none")    valuePostData.message_payload = messageCandidate.templatePayload.value;
        valuePostData.params.message_id = messageCandidate.messageId;
        valuePostData.params.correlation_id = (messageCandidate.correlationId=""? messageId : messageCandidate.correlationId);

        if (messageCandidate.asyncAcknowledge !== undefined && messageCandidate.asyncAcknowledge.value !== undefined ){
            aSourceValue == messageCandidate.asyncAcknowledge.valueOf()
            valuePostData.params.requires_ack = aSourceValue;
        }


        console.debug("ready send: ", valuePostData);
        axios.post(this.defaultPostURL,valuePostData,this.defaultPostConfig)
            .then((response) => {
                console.debug("SEND CALL SUCCESS !!! status : ", response.data[0].status," /--/  body :  " , response.data[0].body," /--/ ack :   " , response.data[0].ack," /--/   "  );
                this.previewContent= response.data[0].body;
                this.acknowledgeContent = response.data[0].ack;
                this.errorContent = response.data[0].status;
            })
            .catch((err) => {
                let errortxt= ("Errors occur in SEND phase \"web api call\"; with detail... \n" +
                " proposed url "+valuePostData.url+"reject the call with :", err);
                console.error(errortxt);
                this.errorContent = errortxt;
                this.acknowledgeContent = "";
                this.errorContent = "";
            })
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


