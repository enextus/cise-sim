import {observable} from "mobx";

export default class Message {
    @observable body;
    @observable acknowledge;
    @observable checksum;
    // status;
    // errorDetail;
    // error;


    constructor(props) {
        this.body = props.body;
        this.acknowledge = props.acknowledge;
        this.checksum = this.calcCheckSum(""+props.body+props.acknowledge);
    }

    // or create a computed value if mutability is required
    calcCheckSum(s){

        var hash = 0, strlen = s.length, i, c;
        if ( strlen === 0 ) {
            return hash;
        }
        for ( i = 0; i < strlen; i++ ) {
            c = s.charCodeAt( i );
            hash = ((hash << 5) - hash) + c;
            hash = hash & hash; // Convert to 32bit integer
        }
        return hash;
    }
}