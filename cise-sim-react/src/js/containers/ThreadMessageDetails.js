import React, {Component} from 'react';
import {Box, Grid} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import {observer} from "mobx-react";
import MessageInfoCard from "../forms/MessageForm/MessageInfoCard";
import Slide from "@material-ui/core/Slide";

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
class ThreadMessageDetails extends Component {

    buildAckSuccessFail(threadWithBodyList) {

        const ackType = 'Ack Synch'; // messageType

        let ackResult = [];
        threadWithBodyList.map( (msg) => {
            if (msg.msgInfo.messageType === ackType) {
                ackResult[msg.msgInfo.messageId.split('_')[0]] = msg.msgInfo.ackResult;
            }
        });

        threadWithBodyList.map( (msg) => {
            if (msg.msgInfo.messageType !== ackType) {
                msg.msgInfo.ackResult = ackResult[msg.msgInfo.messageId];
            }
        });
    }

    render() {

        const {classes} = this.props;
        const messageList  = this.getMessageStore().threadWithBody;

        // toDo the ordering by timestamp
        //messageList.sort(function(a,b) {return b.msgInfo.dateTime-a.msgInfo.dateTime});

        this.buildAckSuccessFail(messageList);

        return (
            <Box p="8px" mt="20px" mx="58px" hidden={messageList.length === 0}>
                <Slide  direction="right" in={messageList.length>0} unmountOnExit>
                    <Grid container className={classes.root}>
                        {messageList.map((msg) =>
                            <MessageInfoCard
                                key={msg.msgInfo.id}
                                msgInfo={msg.msgInfo}
                                body={msg.body}
                            />
                        )}

                    </Grid>
                </Slide>
            </Box>
        )
    }

    getMessageStore() {
        return this.props.store.messageStore;
    }

}

export default withStyles(styles)(ThreadMessageDetails);