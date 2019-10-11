export default class Message {
    body;
    acknowledge;
    status;
    errorDetail;
    error;

    constructor(props) {
        this.body = props.body;
        this.acknowledge = props.acknowledge;
    }
}