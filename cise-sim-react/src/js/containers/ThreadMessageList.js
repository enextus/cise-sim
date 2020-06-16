import React, {Component} from 'react';
import {Box, Grid, Paper} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import ThreadMsgInfo from '../components/MessageForm/ThreadMessageInfo';
import {observer} from "mobx-react";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 8,
        margin: '16px auto',
        maxWidth: 800,
        overflowY: 'scroll',
        maxHeight: 800,
    },
    button: {
        align: "right"
    }

});


@observer
class ThreadMessageList extends Component {


    selectThread = (correlationId) =>  {
        const msgRcv = this.getMessageStore().historyMsgList;

        const result = msgRcv.filter(item => item.correlationId === correlationId);

        // Do the ordering by timestamp
        result.sort(function(a,b) {return b.dateTime-a.dateTime});

        this.getMessageStore().getThreadWithBody(result);
        this.getMessageStore().updateThreadIdSelected(correlationId);
    }

    buildThreadCards = (msgList) => {

        let group = [];
        let counter = [];
        let mostRecentTimestamp = [];
        let ackSuccess = [];
        let rcvAckSynch = [];

        let msg;
        for (msg of msgList) {
            if (group[msg.correlationId] === undefined) {
                group[msg.correlationId] = {...msg};
                counter[msg.correlationId] = 1;
                mostRecentTimestamp[msg.correlationId] = msg.dateTime;
                ackSuccess[msg.correlationId] = Boolean('true');
                rcvAckSynch[msg.correlationId] = Boolean('false');
            } else {
                counter[msg.correlationId]++;
                if (mostRecentTimestamp[msg.correlationId] <  msg.dateTime) {
                    mostRecentTimestamp[msg.correlationId] =  msg.dateTime;
                }
                if (msg.messageType !== 'Ack Synch' && (group[msg.correlationId].messageType === 'Ack Synch' || group[msg.correlationId].dateTime >= msg.dateTime)) {
                    group[msg.correlationId]  =  {...msg};

                }
            }
            if (msg.messageType === 'Ack Synch') {
                ackSuccess[msg.correlationId] = ackSuccess[msg.correlationId] && msg.ackResult.includes('Success');
                rcvAckSynch[msg.correlationId] = Boolean('true');
            }
        }

        let result = []
        let item;
        for (item in group) {
            group[item].numTh = counter[item];
            group[item].mostRecentTimestamp = mostRecentTimestamp[item];
            group[item].ackSuccess =  rcvAckSynch[msg.correlationId] ? ackSuccess[item]: Boolean('false');
            result.push(group[item]);
        }

        // Do the ordering by timestamp
        result.sort(function(a,b) {return b.dateTime-a.dateTime});


        return result;
    }


    render() {

        const {classes} = this.props;

        // Set the max number of messages to be shown
        const maxShow = this.getServiceStore().serviceSelf.messageHistoryMaxLength;
        this.getMessageStore().setThreadMaxCapacity(maxShow);

        // Manage the message and create the thread groups
        const msgRcv = this.getMessageStore().historyMsgList;
        const threadCards = this.buildThreadCards(msgRcv);

        // Render
        return (
            <Box p="8px" mt="20px" mx="20px" bgcolor="#eeeeee" hidden={threadCards.length === 0} >
                <Paper  className={classes.root} >
                        <Grid item xs={12} >
                            {threadCards.map((msg) =>
                                <ThreadMsgInfo
                                    key={msg.id}
                                    msgInfo={msg}
                                    selectThread={() => this.selectThread(msg.correlationId)}
                                    selected={this.getMessageStore().threadIdSelected === msg.correlationId}
                                />)}
                        </Grid>
                </Paper>
            </Box>
        )
    }

    getMessageStore() {
        return this.props.store.messageStore;
    }

    getServiceStore() {
        return this.props.store.serviceStore;
    }
}


export default withStyles(styles)(ThreadMessageList);