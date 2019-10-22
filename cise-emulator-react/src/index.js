import React from "react";
import {render} from "react-dom";
import MainApp from "./js/MainApp";
import MuiThemeProvider from "@material-ui/core/styles/MuiThemeProvider";
import createMuiTheme from "@material-ui/core/styles/createMuiTheme";
import {blue, pink} from "@material-ui/core/colors";
import {CssBaseline} from "@material-ui/core";
import { SnackbarProvider } from 'notistack';
import Button from '@material-ui/core/Button';

const theme = createMuiTheme({
  palette: {
    primary: {main: '#0B6192'},
    secondary: {main: '#F1614A'},
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
      <MuiThemeProvider theme={theme}>
        <CssBaseline/>
        <SnackbarProvider
            dense
            maxSnack={5}
            anchorOrigin={{
              vertical: 'bottom',
              horizontal: 'right',
            }}
            hideIconVariant= {true}
        >
          <MainApp/>
        </SnackbarProvider>
      </MuiThemeProvider>
    </React.Fragment>,
    document.getElementById("root")
);
