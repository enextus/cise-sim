
export default class MessageShortInfo {

    id;
    dateTime;
    messageType;
    serviceType;
    isSent;

    constructor(props) {
        this.id = props.id;
        this.dateTime = props.dateTime;
        this.messageType = props.messageType;
        this.serviceType = props.serviceType;
        if (Boolean(props.sent)) {
            this.isSent = "SENT";
        } else {
            this.isSent = "RECV";
        }

    }

    render() {
        return (
            <div>
                <p>{this.dateTime}</p>
                <p>{this.messageType}</p>
                <p>{this.serviceType}</p>
                <p>{this.isSent}</p>
            </div>
        )
    }
}