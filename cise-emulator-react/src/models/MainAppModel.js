import {observable, computed, action} from "mobx";
//import axios from "axios";
import Singleton from "../transport/socket";
import MessageType from "../components/Panels/SendMessage/MessageType";
import FileRef from "./FileRef";

export default class MainAppModel {
    @observable socket;
    @observable modalOpen = true;
    @observable memberId = "#TobeLoaded#";
    @observable memberList = [];
    @observable options = [];
    @observable optionsPayload = [];

    //@observable TimerSinceWithoutConnected = observable({secondsPassed: 0});

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
        return (
            this.socket != undefined
            && this.socket != null
            && this.options.length > 0
            && this.optionsPayload.length > 0
        );
    }


    @computed get templateOptions() {
        if (!this.IsConnected) return [{label: "#None", value: "#None"}];
        console.log("giveOptions", this.options);
        return this.options.map(x => ({label: x.name, value: x.hash}));
    }

    @computed get payloadOptions() {
        if (!this.IsConnected) return [{label: "#None", value: "#None"}];
        return this.optionsPayload.map(x => ({label: x.name, value: x.hash}));
    }

    @action
    closeModal() {
        this.modalOpen = false;
    }

    @action
    openModal() {
        this.modalOpen = true;
    }

    @action
    obtainSelfMember() {
        this.memberId = "cx.simlsa4-nodecx.nodecx.eucise.cx"
        // axios.get(("http://localhost:8080/webapi/member/0"))
        //     .then((response) => {
        //         console.log("obtainSelfMember SUCCESS !!! @axios call ", response.data);
        //         //response.data.map(item => {
        //         // ObjectMapper mapper = new ObjectMapper();
        //         this.memberId = (response.data.name);
        //         //});
        //     })
        //     .catch((err) => {
        //         console.log("ERROR !!! @axios call ", err)
        //     })
    }

    @action
    obtainXmlTemplates() {
        this.options = [];
        this.options.push(
            new FileRef("#None", "/None", "#None")
        );
        this.options.push(
            new FileRef("pushTemplate.xml", "/tmp/pushTemplate.xml", "345435345")
        );
        //
        // axios.get(("http://localhost:8080/webapi/template"))
        //     .then((response) => {
        //         console.log("obtainXmlTemplates SUCCESS !!! @axios call ", response.data);
        //         this.optionService = [];
        //         for(let itXmlFile of response.data){
        //             this.optionService.push( //FIFO
        //                 new FileRef(itXmlFile.name, itXmlFile.path, itXmlFile.hash)
        //             );
        //         }
        //         console.log("this optionService:"+this.optionService);
        //     })
        //     .catch((err) => {
        //         console.log("ERROR !!! @axios call ", err)
        //     })
    }


    @action
    obtainXmlPayloads() {
        this.optionsPayload = [];
        this.optionsPayload.push(
            new FileRef("#None", "/None", "#None")
        );
        this.optionsPayload.push(
            new FileRef("vessel.xml", "/tmp/vessel.xml", "875474554")
        );
        // axios.get(("http://localhost:8080/webapi/payload"))
        //     .then((response) => {
        //         console.log("obtainXmlPayloads SUCCESS !!! @axios call ", response.data);
        //         this.optionsPayload = [];
        //         for (let itXmlFile of response.data) {
        //             this.optionsPayload.push(
        //                 new FileRef(itXmlFile.name, itXmlFile.path, itXmlFile.hash)
        //             );
        //         }
        //
        //         console.log("this optionsPayload:" + this.optionsPayload);
        //     })
        //     .catch((err) => {
        //         console.log("ERROR !!! @axios call ", err)
        //     })
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
                    self.messageReceived(message.data);
                    break;
                case MessageType.MEMBER_JOINED:
                    users = JSON.parse(message.data);
                    self.userJoined(users);
                    break;
                case MessageType.MEMBER_LEFT:
                    users = JSON.parse(message.data);
                    self.userLeft(users);
                    break;
                case MessageType.MEMBER_JOINED_ACK:
                    let thisUser = message.user;
                    self.userJoinedAck(thisUser);
                    break;
                default:
            }
        };

        this.socket.onopen = () => {
            this.sendJoinedMessage();
        };

        window.onbeforeunload = () => {
            let messageDto = JSON.stringify({
                member: this.memberId,
                type: MessageType.MEMBER_LEFT,
                data: '',
                acknowledgment: '',
                status: 'Success'
            });
            this.socket.send(messageDto);
        }

    }

    @action
    sendJoinedMessage(message) {
        let messageDto = JSON.stringify({
            member: this.memberId,
            type: MessageType.MEMBER_JOINED,
            data: '',
            acknowledgment: '',
            status: 'Success'
        });
        this.socket.send(messageDto);
    }

    @action
    userJoinedAck(users) {
        let messageDto = JSON.stringify({
            member: this.memberId,
            type: MessageType.MEMBER_JOINED_ACK,
            data: '',
            acknowledgment: '',
            status: 'Success'
        });
        this.socket.send(messageDto);
    }

    @action
    userJoined(users) {
        let messageDto = JSON.stringify({
            member: this.memberId,
            type: MessageType.MEMBER_JOINED_ACK,
            data: '',
            acknowledgment: '',
            status: 'Success'
        });
        this.socket.send(messageDto);
    }

    @action
    userLeft(users) {
        let messageDto = JSON.stringify({
            member: this.memberId,
            type: MessageType.MEMBER_LEFT,
            data: '',
            acknowledgment: '',
            status: 'Success'
        });
        this.socket.send(messageDto);
    }

    @action
    messageReceived(message) {
        //let messageDto = JSON.stringify({ member: this.memberId, type: MessageType.MEMBER_LEFT, data: message ,acknowledgment:'',status:'Success'});
        console.log("received :" + message);
        alert(message);

    }

}
