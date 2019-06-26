import React from "react";
import { render } from "react-dom";
import DevTools from "mobx-react-devtools";
import MessageList from "./components/MessageList";
import MessageListModel from "./models/MessageListModel";
import MainAppModel from "./models/MainAppModel";
import MainApp from "./app/MainApp";

import WaitModal from "./components/WaitModal";
import {Provider} from "mobx-react";
import MessageType from "./models/MessageType";
import {autorun} from "mobx";


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



autorun(() => {
    stores.appStore.obtainSelfMember();
    stores.appStore.registerSocket();
       });

setInterval(() => {
    if (stores.appStore.IsConnected)
{
    stores.appStore.closeModal();
} else {
    stores.appStore.openModal();
}
}, 3000);





//stores.add(socketClient.socket);
window.store = stores;
