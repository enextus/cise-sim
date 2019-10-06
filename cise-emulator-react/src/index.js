import React from "react";
import {render} from "react-dom";
import DevTools from "mobx-react-devtools";
import MessageStore from "./js/models/MessageStore";
import AppStore from "./js/models/AppStore";
import TemplateStore from "./js/templates/TemplateStore";
import MainApp from "./js/MainApp";
import MuiThemeProvider from "@material-ui/core/styles/MuiThemeProvider";
import createMuiTheme from "@material-ui/core/styles/createMuiTheme";
import {blue, pink} from "@material-ui/core/colors";
import {CssBaseline} from "@material-ui/core";
import {autorun} from "mobx";

const stores = {
    appStore: new AppStore(),
    templateStore: new TemplateStore(),
    messageStore: new MessageStore()
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
    overrides: {
        // Style sheet name to overrides⚛️
        MuiExpansionPanelDetails: {
            root:{
                display: 'grow' 
            }
        }
    }    
});

autorun(() => {
    stores.appStore.loadServiceId();
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