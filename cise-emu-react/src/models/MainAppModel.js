import { observable, computed, action } from "mobx";
import axios from "axios";
import User from "./member/User";
import Singleton from "../transport/socket";
import MessageType from "../containers/Chat/SendMessage/MessageType";

export default class MainAppModel {
    @observable socket ;
    @observable modalOpen = true;
    @observable memberId = "#TobeLoaded#";
    @observable memberList = [];
    @observable OptionService = ["#TobeLoaded#"];
    @observable OptionPayload = ["#TobeLoaded#"];
    @observable TimerSinceWithoutConnected = observable({secondsPassed: 0});

    @computed
    get IsModalOpened() {
        return this.modalOpen == true;
    }

    @computed
    get IsModalClosed() {
        return this.modalOpen == false;
    }
    @computed
    get IsConnected() {
        return (this.socket != undefined && this.socket != null);
    }

    @action
     closeModal() {
         this.modalOpen=false;
     }
    @action
     openModal() {
         this.modalOpen=true;
     }

    @action
    obtainSelfMember() {
        axios.get(("http://localhost:48080/webapi/activemember"))
            .then((response) => {
                console.log("SUCCESS !!! @axios call ", response.data)
                //response.data.map(item => {
                // ObjectMapper mapper = new ObjectMapper();
                    this.memberId = (response.data.name);
                //});
            })
            .catch((err) => {
                console.log("ERROR !!! @axios call ", err)
            })
    }

    @action
    registerSocket() {
        let self = this;
        this.socket = Singleton.getInstance();

        this.socket.onmessage = (response) => {
            let message = JSON.parse(response.data);
            let users;

            switch (message.type) {
                case MessageType.TEXT_MESSAGE:
                    self.props.messageReceived(message);
                    break;
                case MessageType.USER_JOINED:
                    users = JSON.parse(message.data);
                    self.props.userJoined(users);
                    break;
                case MessageType.USER_LEFT:
                    users = JSON.parse(message.data);
                    self.props.userLeft(users);
                    break;
                case MessageType.USER_JOINED_ACK:
                    let thisUser = message.user;
                    self.props.userJoinedAck(thisUser);
                    break;
                default:
            }
        }

        this.socket.onopen = () => {
            this.sendJoinedMessage();
        }

        window.onbeforeunload = () => {
            let messageDto = JSON.stringify({ user: appStore.memberId, type: MessageType.USER_LEFT });
            this.socket.send(messageDto);
        }

    }

    @action
    sendJoinedMessage() {
        let messageDto = JSON.stringify({ user: appStore.memberId, type: MessageType.USER_JOINED });
        this.socket.send(messageDto);
    }
}
