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
        let returnjsonpart= (this.body!==""?this.body.substr(27,30):"");
        let errortxt= "unknow error";
        axios.get(serviceUrl, this.defaultGetConfig)
            .then((response) => {
                if (response.status != 204) {
                this.body = response.data.body;
                this.acknowledgement = response.data.acknowledge;
                this.status = response.data.status;
                this.timer = 0;
                returnjsonpart=((this.body.substr(10,30)!==returnjsonpart)?this.body.substr(27,30):"")
                errortxt="";
                }
            })
            .catch((err) => {
                errortxt = (" \"web api \" error call to :" + this.defaultServiceUrl + ":", err);
                return errortxt;
            })
     return(returnjsonpart)       
     
    }
}


