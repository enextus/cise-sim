import React, {Component} from 'react';
import SendForm from './SendForm';
import PushedMessage from './PushedMessage';
import PreviewMessage from './PreviewMessage';
import PulledMessage from '../messages/components/PulledMessage';
import {Grid} from '@material-ui/core';
import {observer} from 'mobx-react';
import messageCandidate from "../models/message/MessageCandidate";
import MessagePullAPI from "../models/message/MessagePullAPI";

@observer
export default class Body extends Component {
    lastReceivePull;
    prevReceivePull;

    render() {
        const messageReceived = new MessagePullAPI();

        setInterval(function () {
            messageReceived.timer += 1;
            this.lastReceivePull = messageReceived.pull(); //TODO: include comparison prev/last in messageStore
            if (this.lastReceivePull !== "" && this.prevReceivePull !== this.lastReceivePull) {
                messageStore.createEvent(this.lastReceivePull);
                this.prevReceivePull = this.lastReceivePull
            }
        }, 3000);

        return (
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <SendForm
                        store={this.props.store}
                        messageCandidate={messageCandidate}/>
                    <PreviewMessage
                        store={this.props.store}/>
                    <PushedMessage
                        store={this.props.store}/>
                    <PulledMessage
                        store={this.props.store}
                        messageReceived={messageReceived}/>
                </Grid>
            </Grid>
        );
    }
}