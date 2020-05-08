import React from 'react';
import {render} from 'react-dom';
import MainApp from './js/MainApp';
import {ThemeProvider as MuiThemeProvider} from '@material-ui/core/styles';
import createMuiTheme from '@material-ui/core/styles/createMuiTheme';
import {CssBaseline} from '@material-ui/core';
import {SnackbarProvider} from 'notistack';

const theme = createMuiTheme({
  typography: {
    fontFamily: [
      'Open Sans',
      'Montserrat',
      'BlinkMacSystemFont',
      '"Segoe UI"',
      'Roboto',
      '"Helvetica Neue"',
      'Arial',
      'sans-serif',
      '"Apple Color Emoji"',
      '"Segoe UI Emoji"',
      '"Segoe UI Symbol"',
    ].join(','),
  },
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
    },
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
              vertical:   'bottom',
              horizontal: 'right',
            }}
            hideIconVariant={true}
        >
          <MainApp/>
        </SnackbarProvider>
      </MuiThemeProvider>
    </React.Fragment>,
    document.getElementById("root")
)
