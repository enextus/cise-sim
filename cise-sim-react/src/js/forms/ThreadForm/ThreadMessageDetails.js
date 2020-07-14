import React, {Component} from 'react';
import {Box, Grid} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import {observer} from "mobx-react";
import MessageInfoCard from "./Details/MessageInfoCard";
import Slide from "@material-ui/core/Slide";
import Logo from "./svg/empty-history-icon.svg"
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import {fontSizeNormal} from "../../layouts/Font";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 8,
        margin: '8px auto',
        maxWidth: 1100
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

    buildList(classes, messageList) {

        return ( <Slide  direction="right" in={messageList.length>0} unmountOnExit>
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

        )
    }

    buildWelcome(classes) {

        return (
            <TableContainer>
                <Table size="small" aria-label="a dense table" style={{marginTop:200}}>
                    <TableBody>
                        <TableRow>
                            <TableCell style={{borderBottom:0}}>
                                <p align="center">
                                    <img src={Logo}  width="150" alt="Select a thread to see details" />
                                </p>
                            </TableCell>
                        </TableRow>
                        <TableRow style={{borderBottom:0}}>
                            <TableCell style={{borderBottom:0, textAlign:"center", fontSize:fontSizeNormal}}>
                                Select a thread to see details
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }

    render() {

        const {classes} = this.props;
        const messageList  = this.getMessageStore().threadWithBody;

        let showThreadDetails;
        if (messageList.length === 0) {
            showThreadDetails = this.buildWelcome(classes);
        }
        else {
            this.buildAckSuccessFail(messageList);
            showThreadDetails = this.buildList(classes, messageList);
        }

        return (
            <Box p="8px" mt="20px" mx="8px">
                {showThreadDetails}
            </Box>
        )
    }

    getMessageStore() {
        return this.props.store.messageStore;
    }

}

export default withStyles(styles)(ThreadMessageDetails);