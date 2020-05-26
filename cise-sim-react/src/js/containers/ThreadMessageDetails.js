import React, {Component} from 'react';
import {Box, Grid, Paper} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import {observer} from "mobx-react";
import MessageInfoCard from "../components/MessageForm/MessageInfoCard";
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

    render() {

        const {classes} = this.props;
        const messageList  = this.getMessageStore().threadWithBody;

        // toDo the ordering by timestamp
        //messageList.sort(function(a,b) {return b.msgInfo.dateTime-a.msgInfo.dateTime});

        return (
            <Box p="8px" mt="68px" mx="58px" bgcolor="#eeeeee" hidden={messageList.length === 0}>
                <Slide  direction="right" in={messageList.length>0} unmountOnExit>
                <Paper  className={classes.root} >
                    <Grid container>

                        {messageList.map((msg) =>
                            <MessageInfoCard
                                key={msg.msgInfo.id}
                                msgInfo={msg.msgInfo}
                                body={msg.body}
                            />
                        )}

                    </Grid>
                </Paper>
                </Slide>
            </Box>
        )
    }

    getMessageStore() {
        return this.props.store.messageStore;
    }

}

export default withStyles(styles)(ThreadMessageDetails);