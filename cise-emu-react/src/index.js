import React from "react";
import { render } from "react-dom";
import DevTools from "mobx-react-devtools";

import MessageList from "./components/MessageList";
import MessageListModel from "./models/MessageListModel";
import MainApp from "./app/MainApp";
import MainAppModel from "./models/MainAppModel";
import WaitModal from "./components/WaitModal";
import {Provider} from "mobx-react";
import MessageType from "./models/MessageType";


const stores = {
    appStore: new  MainAppModel(),
    messageStore: new MessageListModel()
}

render(
    <div>
        <DevTools />
          <MainApp store={stores}/>
    </div>,
    document.getElementById("root")
);
/*<MessageList store={store1} />*/

stores.messageStore.addMessage("Get Coffee");
stores.messageStore.addMessage("Write simpler code");
//stores.messageStore.messages[0].finished = true;

setInterval(() => {
    stores.messageStore.addMessagefull("Get a cookie as well",MessageType.MASTER_IN,"templatePush.xml","you.xml",true,"OTHER","#TOBELOADED#");
}, 5000);
setInterval(() => {
    stores.appStore.TimerSinceWithoutConnected.secondsPassed++;
}, 1000);
setInterval(() => {
    stores.appStore.closeModal();
}, 2000);
// playing around in the console
window.store = stores;

//React.createElement('div',null,
//         React.createElement('Provider',{"store":stores},
//             React.createElement('Application', null, null)))

    /*init messages.addMessage("Get Coffee");*/
    /*init messages.addMessage("Write simpler code");*/
    /*init messages.messages[0].finished = true;*/
    /*init messages.messages[0].steps.push("first Step")*/
    /*init messages.messages[0].steps.push("second Step")*/
    /*init messages.messages[0].steps.push("third Step")*/

// const socketClient = new SocketClient();
// playing around in the console
// store.add(socketClient.socket);
// window.store = store;
