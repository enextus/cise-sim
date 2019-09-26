import React, {Component} from 'react';
import SendMessage from './SendMessage/SendMessage';
import PushedMessage from './SendMessage/PushedMessage';
import PulledMessage from './ReceiveMessage/PulledMessage';
import {Grid, Paper} from '@material-ui/core';
import {observer} from 'mobx-react';
import messageCandidate from "../../models/message/MessageCandidate";
import MessagePushAPI from "../../models/message/MessagePushAPI";
import MessagePullAPI from "../../models/message/MessagePullAPI";

@observer
export default class Panels extends Component {


    render() {
        const headerStyle = {
            height: '30px',
            width: '100%',
            margin: '10px auto',
            position: 'relative'
        };
        const commandPaneStyle = {
            height: '170px',
            width: '95%',
            margin: '5px auto',
            position: 'relative'
        };

        const windowStyle = {
            height: '99%',
            width: '99%',
            margin: '10px auto',
            backgroundColor: "rgba(205,205,205,0.6)"
        };

        const messagePreview = new MessagePushAPI();
        const messageReceived = new MessagePullAPI();

        setInterval(function() {
            messageReceived.timer += 1;
            messageReceived.pull();
        }, 3000);

        return (
            <div style={windowStyle}>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <Paper style={headerStyle}></Paper>
                    </Grid>
                    <Grid item xs={12}>
                        <Paper style={commandPaneStyle}><SendMessage store={this.props.store}
                                                                     messageCandidate={messageCandidate}
                                                                     messagePreview={messagePreview}/> </Paper>

                        <PushedMessage store={this.props.store} messageCandidate={messageCandidate}
                                       messagePreview={messagePreview}/>
                        <PulledMessage store={this.props.store} messageReceived={messageReceived}/>
                    </Grid>
                </Grid>
            </div>
        );
    }
}



