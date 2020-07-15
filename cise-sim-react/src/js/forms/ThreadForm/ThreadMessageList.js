import React, {Component} from 'react';
import {Box} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import ThreadMsgInfo from './List/ThreadListMessageInfo';
import {observer} from "mobx-react";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 8,
        margin: '16px auto',
        maxWidth: 800,
        overflowY: 'scroll',
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
                if (msg.messageType !== 'Sync Ack' && (group[msg.correlationId].messageType === 'Sync Ack' || group[msg.correlationId].dateTime >= msg.dateTime)) {
                    group[msg.correlationId]  =  {...msg};

                }
            }
            if (msg.messageType === 'Sync Ack') {
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
        result.sort(function(a,b) {return b.mostRecentTimestamp-a.mostRecentTimestamp});


        return result;
    }


    render() {

        const {classes} = this.props;

        // Set the max number of messages to be shown
        const maxShow = this.getServiceStore().serviceSelf.messageHistoryMaxLength;
        this.getMessageStore().setThreadMaxCapacity(maxShow);

        // Manage the message and create the thread groups
        const msgRcv = this.getMessageStore().historyMsgList;
        let threadCards = this.buildThreadCards(msgRcv);

        const msgFilter = this.getMessageStore().threadFilter;
        if (msgFilter) {
            if(msgFilter === 'fail') {
                threadCards = threadCards.filter((msg) => !msg.ackSuccess);
            }
        }

        // Render ( maxHeight="420px"  overflow= "scroll") .. ?
        return (
            <Box hidden={threadCards.length === 0}  >

                    {threadCards.map((msg) =>
                        <ThreadMsgInfo
                            key={msg.id}
                            msgInfo={msg}
                            selectThread={() => this.selectThread(msg.correlationId)}
                            selected={this.getMessageStore().threadIdSelected === msg.correlationId}
                        />)}

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