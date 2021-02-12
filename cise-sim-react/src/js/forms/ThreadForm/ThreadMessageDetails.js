/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

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

        const ackType = 'Sync Ack'; // messageType

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
                <Table size="small" aria-label="a dense table" style={{marginTop:"5%"}}>
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