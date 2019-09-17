import React from "react";
import { render } from "react-dom";
import DevTools from "mobx-react-devtools";
import MessageListModel from "./models/MessageListModel";
import MainAppModel from "./models/MainAppModel";
import MessageType from "./models/MessageType";
import MainApp from "./app/MainApp";
import {autorun} from "mobx";
import { MuiThemeProvider, createMuiTheme } from '@material-ui/styles';

const stores = {
    appStore: new  MainAppModel(),
    messageStore: new MessageListModel()
};

stores.messageStore.createNewMessage("",MessageType.MASTER_IN,"","","false","","");
render(

    <div>
        <DevTools />
          <MainApp store={stores}/>
    </div>,
    document.getElementById("root")
);

autorun(() => {
    stores.appStore.obtainXmlTemplates();
    stores.appStore.obtainXmlPayloads();
    stores.appStore.obtainSelfMember();
    stores.appStore.registerSocket();

       });

setInterval(() => {
    //stores.appStore.IsConnected
    //         &&
    if (stores.appStore.IsModalOpened )
{
    stores.appStore.closeModal();
}
}, 5000);

//stores.add(socketClient.socket);
window.store = stores;
