
const AppConfig = {
    PROTOCOL: "wss:",
    // TODO: change to localhost if you wish to run it locally
    HOST: "//localhost",
    PORT: "48080"

    //PROTOCOL: "wss:",
    //HOST: "//serene-wave-94653.herokuapp.com",
    //PORT: ":9000"
}
//https://github.com/jeffijoe/logpipe-server/blob/master/src/frontend/app/stores/LogStore.js mobx can be used
const Singleton = (function () {
    let instance;

    function createInstance() {
        // TODO: add +  PORT if you want to run it locally
        const socket = new WebSocket(AppConfig.PROTOCOL + AppConfig.HOST );
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

export default Singleton;