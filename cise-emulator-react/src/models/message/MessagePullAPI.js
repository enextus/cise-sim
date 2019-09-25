import {computed, observable, action} from 'mobx'
import axios from "axios";

export default class MessagePullAPI {
    @observable   body= "";
    @observable   acknowledgement="";
    @observable   status= "";
    defaultGetConfig = {
        method: 'get',
        header: {
            'Content-Type': 'application/json'
        }
    };
    defaultServiceUrl ="/webapi/messages";


    @computed
    get isEmpty() {
        return (body==="");
    }


    @action
    pull() {
        axios.get(this.defaultServiceUrl(),this.defaultGetConfig)
            .then((response) => {
                console.debug("PULL CALL SUCCESS !!! status : ", response.data.status," /--/  body :  " , response.data.body," /--/ acknowledgement :   " , response.data.ack," /--/   "  );
                this.body = response.data.body;
                this.acknowledgement = response.data.ack;
                this.status = response.data.status;
            })
            .catch((err) => {
                let errortxt= ("Errors occur in PREVIEW phase \"web api pull call\"; with detail... \n" +
                " proposed url "+valuePostData.url+"reject the call with :", err);
                console.error(errortxt);
            })

    }
}


