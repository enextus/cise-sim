import React, {Component} from 'react';
import {Box, Grid, Paper} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import {observer} from "mobx-react";
import MessageInfoCard from "../components/MessageForm/MessageInfoCard";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 16,
        margin: '16px auto',
        maxWidth: 800
    },
});

/**
 * This class handle the details of a thread message
 * It use 2 components: MessageInfoCard and MessageXml
 * It handle the request of the xml an to render the only MessageXml components
 */
@observer
class ThreadMessageDetails extends Component {



    getMsg = (uuid) =>  {
        console.log("getMsg uuid " + uuid );
      //  this.getMessageStore().getByShortInfoId(uuid);

    //    console.log("handleChange uuid " + uuid + "newExpanded "+newExpanded);
        if (uuid !== this.bodyId) {
            this.getMessageStore().getByShortInfoId(uuid);
            this.bodyId = uuid;
        }
    }

    /*
Signature:
function(event: object, expanded: boolean) => void
event: The event source of the callback.
expanded: The expanded state of the panel.
 */
    bodyId = null;

    handleChange = (uuid) => (event, newExpanded) => {

        console.log("handleChange uuid " + uuid + "newExpanded "+newExpanded);
        if (uuid !== this.bodyId) {
            this.getMessageStore().getByShortInfoId(uuid);
            this.bodyId = uuid;
        }
  //      setExpanded(newExpanded ? panel : false);
    };


    render() {

        const {classes} = this.props;
        const msgRcv  = this.getMessageStore().threadMessageDetails;
        const msgBody = this.getMessageStore().receivedMessage.body;

        return (
            <Box p="8px" mt="68px" mx="58px" bgcolor="#eeeeee">
                <Paper  className={classes.root} >
                        <Grid item xs={12}>
                            {msgRcv.map((msg) => <MessageInfoCard
                                key={msg.id}
                                selectMsg={() => this.getMsg(msg.id)}
                                msgInfo={msg}
                                change={() => this.handleChange(msg.id)}
                                body={this.bodyId === msg.id ? msgBody : null}
                                />)}
                        </Grid>
                </Paper>
            </Box>
        )
    }

    getMessageStore() {
        return this.props.store.messageStore;
    }

}


export default withStyles(styles)(ThreadMessageDetails);