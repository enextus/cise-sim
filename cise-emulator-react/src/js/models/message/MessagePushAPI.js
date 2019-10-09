import {action, computed, observable} from 'mobx'
import axios from "axios";

export default class MessagePushAPI {

    @observable   previewContent = "";
    @observable   acknowledgeContent = "";
    @observable   errorContent = "";
    defaultPostConfig = {
        method: 'post',
        header: {
            'Content-Type': 'application/json'
        }
    };

    defaultPostData = {
        'message_template': '',
        'params': {
            'message_id': '',
            'requires_ack': false
        }
    };
    defaultGetConfig = {
        method: 'get',
        header: {
            'Content-Type': 'application/json'
        }
    };

    defaultServiceUrl = "/api/";


    @computed
    get isError() {
        if (this.errorContent >= 400) {
            return true;
        } else {
            return false;
        }
    }

    @computed
    get status() {
        if (this.isError) {
            return "error";
        } else {
            if (acknowledgeContent.isEmpty()) {
                return "preview";
            } else {
                return "sent";
            }
        }
    }


    @action
    send(messageCandidate) {

        const serviceUrl = ((document.location.hostname == "localhost") ? 'http://localhost:47080' + this.defaultServiceUrl + 'templates' : 'http://' + document.location.host + this.defaultServiceUrl + 'templates');
        const valuePostData = JSON.parse(JSON.stringify(this.defaultPostData))
        valuePostData.message_template = messageCandidate.templateService.value;
        if (messageCandidate.templatePayload.value != "#none") valuePostData.message_payload = messageCandidate.templatePayload.value;
        valuePostData.params.message_id = messageCandidate.messageId;
        valuePostData.params.correlation_id = (messageCandidate.correlationId = "" ? messageId : messageCandidate.correlationId);
        if (messageCandidate.asyncAcknowledge !== undefined) {
            valuePostData.params.requires_ack = messageCandidate.asyncAcknowledge;
        }

        axios.post(serviceUrl, valuePostData, this.defaultPostConfig)
            .then((response) => {
                console.debug("SEND SUCCESS  :status: ", response.data.status, " /--/  :body:  ", response.data.body, " /--/ :acknowledge:   ", response.data.acknowledge, " /--/   ");
                this.previewContent = response.data.body;
                this.acknowledgeContent = response.data.acknowledge;
                this.errorContent = response.data.status;
            })
            .catch((err) => {
                let errortxt = ("Errors occur in SEND phase \"web api call\"; with detail... \n" +
                " proposed url " + valuePostData.url + "reject the call with :", err);
                console.error(errortxt);
                this.errorContent = errortxt;
                this.acknowledgeContent = "";
            })
    }

    @action
    preview() {
        const serviceUrl = ((document.location.hostname == "localhost") ? 'http://localhost:47080' + this.defaultServiceUrl + 'templates/12345?messageId=msg-id&correlationId=corrId&requestAck=false' : 'http://' + document.location.host + this.defaultServiceUrl + 'templates/12345?messageId=msg-id&correlationId=corrId&requestAck=false');

        axios.get(serviceUrl, this.defaultGetConfig)
            .then((response) => {
                if (response.status !== 204) {
                    this.body = response.data.templateContent;
                    this.id = response.data.templateId;
                    this.name = response.data.templateName;
                    this.timer = 0;
                    return ((this.body.substr(10, 30) !== returnjsonpart) ? this.body.substr(27, 30) : "")
                }
            })
            .catch((err) => {
                return (" \"templates api \" error call to :" + this.defaultServiceUrl + ":", err);
            });
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
            + "\n proposed message " + messageCandidate.messageId
            + "\n asyncAcknowledge:" + asyncAcknowledge
            + "\n correlationId:" + correlationId
            + "\n templateService:" + template.value + " / " + template.label
            + "\n templatePayload:" + payload.value + " / " + payload.label;
    }
}


