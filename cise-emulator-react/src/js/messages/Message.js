import {observable} from "mobx";

export default class Message {
    @observable body;
    @observable acknowledge;
    // status;
    // errorDetail;
    // error;

    constructor(props) {
        this.body = props.body;
        this.acknowledge = props.acknowledge;
    }
}