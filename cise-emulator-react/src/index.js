import React from "react";
import {render} from "react-dom";
import DevTools from "mobx-react-devtools";
import MessageListModel from "./models/MessageListModel";
import MainAppModel from "./models/MainAppModel";
import MessageType from "./models/MessageType";
import MainApp from "./app/MainApp";
import {autorun, when} from "mobx";

const stores = {
    appStore: new MainAppModel(),
    messageStore: new MessageListModel()
};

stores.messageStore.createNewMessage("", MessageType.MASTER_IN, "", "", "false", "", "");
render(
    <div>
        <DevTools/>
        <MainApp store={stores}/>
    </div>,
    document.getElementById("root")
);

autorun(() => {
    stores.appStore.obtainXmlTemplates();
    stores.appStore.obtainSelfMember();
});


when(
    // predicate
    () => stores.appStore.IsConnected,
    // effect
    () => stores.appStore.closeModal()
);

window.store = stores;
