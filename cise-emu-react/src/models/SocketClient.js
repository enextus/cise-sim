import {action, observable} from "mobx";

export default class SocketClient {

    PROTOCOL= "ws:";
    // TODO: change to localhost if you wish to run it locally
    HOST= "//localhost";
    PORT= "48080";
    @observable
    data=null;
    @observable
    socket=null;

    constructor(){
         this.socket = new WebSocket(this.PROTOCOL + this.HOST + ":" + this.PORT + "/");
              this.socket.onopen =()=> {
                    console.log(`WebSocket OPEN: ${error}`)
                };
              this.socket.onerror = () => {
                    console.log(`WebSocket error: ${error}`)
                };
  }


  }

