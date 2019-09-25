import React, {Component} from 'react';
import SendMessage from './SendMessage/SendMessage';
import ReceiveMessage from './ReceiveMessage/ReceiveMessage';
import {Grid, Paper} from '@material-ui/core';
import {observer} from 'mobx-react';
import messageCandidate from "../../models/message/MessageCandidate";
import MessagePushAPI from "../../models/message/MessagePushAPI";

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
            height: '270px',
            width: '95%',
            margin: '5px auto',
            position: 'relative'
        };
        const ResultPaneStyle = {
            height: '330px',
            width: '95%',
            margin: '5px auto',
            position: 'relative'
        };
        const HistoricalPaneStyle = {
            height: '100% ',
            width: '100%',
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

        return (
            <div style={windowStyle}>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <Paper style={headerStyle}></Paper>
                    </Grid>
                    <Grid item xs={12}>
                            <SendMessage store={this.props.store} messageCandidate={messageCandidate}
                                         messagePreview={messagePreview}/>
                            <span>

                                         </span>
                            <ReceiveMessage store={this.props.store} messageCandidate={messageCandidate}
                                            messagePreview={messagePreview}/>

                    </Grid>

                    {/*    <Grid item xs={4}>*/}
                    {/*    <Paper style= {HistoricalPaneStyle}>*/}
                    {/*      <History store={this.props.store}  messageCandidate={messageCandidate} messagePreview={messagePreview} />*/}
                    {/*    </Paper>*/}
                    {/*</Grid>*/}
                </Grid>
            </div>
        );
    }

    obtainPreview(messageCandidate) {
        console.log("messageCandidate.asyncAcknowledge" + messageCandidate.asyncAcknowledge
            + "messageCandidate.templateService" + messageCandidate.templateService
            + "messageCandidate.correlationId" + messageCandidate.correlationId
            + "messageCandidate.templatePayload" + messageCandidate.templatePayload);
    }

    sendMessage(messageCandidate) {
        console.log("messageCandidate.asyncAcknowledge" + messageCandidate.asyncAcknowledge
            + "messageCandidate.templateService" + messageCandidate.templateService
            + "messageCandidate.correlationId" + messageCandidate.correlationId
            + "messageCandidate.templatePayload" + messageCandidate.templatePayload);
        // create a virtual message to send to the server
    }

}



