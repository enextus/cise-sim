import {action, computed, observable} from 'mobx'
import axios from "axios";

export default class MessagePullAPI {
    @observable  timer = 0;
    @observable  counter = 0;
    @observable   body = "";
    @observable   acknowledgement = "";
    @observable   status = "";
    defaultGetConfig = {
        method: 'get',
        header: {
            'Content-Type': 'application/json'
        }
    };
    defaultServiceUrl = "/webapi/";


    @computed
    get isEmpty() {
        return (body === "");
    }


    @action
    pull() {
        const serviceUrl = ((document.location.hostname == "localhost") ? 'http://localhost:47080' + this.defaultServiceUrl + 'messages' : 'http://' + document.location.host + this.defaultServiceUrl + 'messages');

        axios.get(serviceUrl, this.defaultGetConfig)
            .then((response) => {

                console.debug("PULL CALL SUCCESS !!! status : ", response.data.status, " /--/  body :  ", response.data.body, " /--/ acknowledge :   ", response.data.acknowledge, " /--/   ");
                if (response.status != 204) {
                this.body = response.data.body;
                this.acknowledgement = response.data.acknowledge;
                this.status = response.data.status;
                this.timer = 0;
                }
            })
            .catch((err) => {
                let errortxt = ("Errors occur in PREVIEW phase \"web api pull call\"; with detail... \n" +
                " proposed url " + this.defaultServiceUrl + "reject the call with :", err);

            })

    }
}


