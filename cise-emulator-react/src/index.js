import React from "react";
import {render} from "react-dom";
import DevTools from "mobx-react-devtools";
import MessageListModel from "./models/MessageListModel";
import MainAppModel from "./models/MainAppModel";
import MessageType from "./models/MessageType";
import MainApp from "./app/MainApp";
import {autorun, when} from "mobx";
import MuiThemeProvider from "@material-ui/core/styles/MuiThemeProvider";
import createMuiTheme from "@material-ui/core/styles/createMuiTheme";
import {blue, pink} from "@material-ui/core/colors";
import {CssBaseline} from "@material-ui/core";

const stores = {
    appStore: new MainAppModel(),
    messageStore: new MessageListModel()
};

const theme = createMuiTheme({
    palette: {
        primary: blue,
        secondary: pink,
        type: 'light',
        background: {
            default: "#eeeeee"
        },
    },
});

render(
    <React.Fragment>
        <DevTools/>
        <MuiThemeProvider theme={theme}>
            <CssBaseline />
            <MainApp store={stores}/>
        </MuiThemeProvider>
    </React.Fragment>,
    document.getElementById("root")
);



autorun(() => {
    stores.appStore.loadXmlTemplates();
    stores.appStore.loadServiceId();
});

when(
    () => stores.appStore.isConnected,
    () => stores.appStore.closeModal()
);



window.store = stores;
