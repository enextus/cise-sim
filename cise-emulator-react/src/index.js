import React from "react";
import {render} from "react-dom";
import DevTools from "mobx-react-devtools";
import MainApp from "./js/MainApp";
import MuiThemeProvider from "@material-ui/core/styles/MuiThemeProvider";
import createMuiTheme from "@material-ui/core/styles/createMuiTheme";
import {blue, pink} from "@material-ui/core/colors";
import {CssBaseline} from "@material-ui/core";

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
            root: {
                display: 'grow'
            }
        }
    }
});

render(
    <React.Fragment>
        <DevTools/>
        <MuiThemeProvider theme={theme}>
            <CssBaseline/>
            <MainApp/>
        </MuiThemeProvider>
    </React.Fragment>,
    document.getElementById("root")
);