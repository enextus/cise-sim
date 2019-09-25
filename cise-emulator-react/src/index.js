import React from "react";
import {render} from "react-dom";
import DevTools from "mobx-react-devtools";
import MessageListModel from "./models/MessageListModel";
import MainAppModel from "./models/MainAppModel";
import MessageType from "./models/MessageType";
import MainApp from "./app/MainApp";
import {autorun, when} from "mobx";
import ThemeProvider from "@material-ui/styles/ThemeProvider";
import createMuiTheme from "@material-ui/core/styles/createMuiTheme";
import {blue, pink} from "@material-ui/core/colors";

const stores = {
    appStore: new MainAppModel(),
    messageStore: new MessageListModel()
};

const theme = createMuiTheme({
    palette: {
        primary: blue,
        secondary: pink,
        type: 'light'
    },
});

stores.messageStore.createNewMessage(
    "",
    MessageType.MASTER_IN,
    "",
    "",
    "false",
    "",
    "");

render(
    <React.Fragment>
        <DevTools/>
        <ThemeProvider theme={theme}>
            <MainApp store={stores}/>
        </ThemeProvider>
    </React.Fragment>,
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
