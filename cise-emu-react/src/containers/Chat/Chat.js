import React, { Component } from 'react';
import ChatHistory from './ChatHistory/ChatHistory';
import SendMessage from './SendMessage/SendMessage';
import ReceiveMessage from './ReceiveMessage/ReceiveMessage';
import { Grid , Paper} from '@material-ui/core';


class Chat extends Component {
    render() {
        const headerStyle = {
            height: '30px' ,
            width: '100%',
            margin: '10px auto',
            position: 'relative'
        };
        const commandPaneStyle = {
            height: '300px' ,
            width: '100%',
            margin: '10px auto',
            position: 'relative'
        };
        const ResultPaneStyle = {
            height: '300px' ,
            width: '100%',
            margin: '10px auto',
            position: 'relative'
        };
        const HistoricalPaneStyle = {
            height: '100%' ,
            width: '100%',
            margin: '10px auto',
            position: 'relative'
        };

        const windowStyle = {
            height: '100%' ,
            width: '100%',
            margin: '10px auto',
            backgroundColor: "rgba(183,186,149,0.6)"
        };

        return (
            <div style= {windowStyle}>
            <Grid container spacing={2} >
            <Grid item xs={12}>
            <Paper style= {headerStyle}></Paper>  
            </Grid>   

                <Grid item xs={8}>
                    <Paper style= {commandPaneStyle}>
                        <SendMessage />
                    </Paper>
                    <Paper style= {ResultPaneStyle}>
                        <ReceiveMessage />
                    </Paper>
                </Grid>
                  
                <Grid item xs={4}>
                <Paper style= {HistoricalPaneStyle}>
                    <ChatHistory />
                </Paper>
            </Grid>          
            </Grid></div>
        );
    }

}

export default Chat;

