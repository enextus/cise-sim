import React, {Component} from 'react';
import {Box, Grid, Paper} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import ThreadMsgInfo from '../components/MessageForm/ThreadMessageInfo';
import {observer} from "mobx-react";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 16,
        margin: '16px auto',
        maxWidth: 800
    },
});


@observer
class ThreadMessageList extends Component {


    getMsg = (correlationId) =>  {
        const msgRcv = this.getMessageStore().historyMsgList;

        const result = msgRcv.filter((item, index) => {
            return item.correlationId === correlationId;
        })

        this.getMessageStore().updateThreadDetails(result);

        console.log(result);
    }

    buildThreadCards = (msgList) => {

        let group = [];
        let counter = [];

        let msg;
        for (msg of msgList) {
            if (group[msg.correlationId] === undefined) {
                group[msg.correlationId] = msg;
                counter[msg.correlationId] = 1;
            } else {
                counter[msg.correlationId]++;
                if (msg.messageType !== 'Ack Synch' && (group[msg.correlationId].messageType === 'Ack Synch' || group[msg.correlationId].dateTime < msg.dateTime)) {
                    group[msg.correlationId]  = msg;
                }
            }
        }

        let result = []
        let item;
        for (item in group) {
            group[item].numTh = counter[item];
            result.push(group[item]);
        }

        // Do the ordering by timestamp
        result.sort(function(a,b) {return b.dateTime-a.dateTime});

        return result;
    }


    render() {

        const {classes} = this.props;
        const msgRcv = this.getMessageStore().historyMsgList;
        const maxShow = this.getServiceStore().serviceSelf.messageHistoryMaxLength;
        this.getMessageStore().setHistoryMaxCapacity(maxShow);

        const threadCards = this.buildThreadCards(msgRcv);

        console.log(threadCards);
        return (
            <Box p="8px" mt="68px" mx="58px" bgcolor="#eeeeee">
                <Paper  className={classes.root} >
                        <Grid item xs={12}>
                            {threadCards.map((msg) => <ThreadMsgInfo msgInfo={msg} key={msg.id} selectMsg={() => this.getMsg(msg.correlationId)}/>)}
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