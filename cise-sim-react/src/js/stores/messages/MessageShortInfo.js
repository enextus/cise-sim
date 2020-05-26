export default class MessageShortInfo {

    id;
    dateTime;
    messageType;
    serviceType;
    isSent;
    messageId;
    correlationId;

    constructor(props) {
        this.id             = props.id;
        this.dateTime       = props.dateTime;
        this.messageType    = props.messageType;
        this.serviceType    = props.serviceType;
        this.isSent         = Boolean(props.sent);
        this.messageId      = props.messageId;
        this.correlationId  = props.correlationId;
    }
}