
const AppConfig = {
    PROTOCOL: "ws:",
    // TODO: change to localhost if you wish to run it locally
    HOST: "//localhost",
    PORT: ":8080",
    PATH: "/websocket"
//    PROTOCOL: "wss:",
//    HOST: "//serene-wave-94653.herokuapp.com",
//    PORT: ":9000"
}



const ciseWebsocket = (function () {
    let instance;

    function createInstance() {
        // TODO: add +  PORT if you want to run it locally
        const socket = new WebSocket(AppConfig.PROTOCOL + AppConfig.HOST + AppConfig.PORT +AppConfig.PATH   );
        //+":"+AppConfig.PORT+"/ihmmonosocket");
        return socket;
    }

    return {
        getInstance: function () {
            if (!instance) {
                instance = createInstance();
            }
            return instance;
        }
    };
})();

export default ciseWebsocket;