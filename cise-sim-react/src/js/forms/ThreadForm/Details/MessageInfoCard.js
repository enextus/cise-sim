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

import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import Table from "@material-ui/core/Table";
import TableContainer from "@material-ui/core/TableContainer";

import ExpansionPanelPreview from "./ExpansionPanelPreview";
import IconMsgOk from "../svg/msg-ok.svg";
import IconMsgKo from "../svg/msg-alert.svg";
import {fontSizeLarge, fontSizeNormal, fontSizeSmall} from "../../../layouts/Font";
import {date2String} from "../../CommonComponents/HelperFunctions";

const styles = theme => ({
    root: {
        minWidth: 275,
    },
    card : {
        minWidth: 275,
        marginBottom: 4,
        borderRadius: 16,
    },

    bullet: {
        display: 'inline-block',
        margin: '0 2px',
        transform: 'scale(0.8)',
    },

    pos: {
        marginBottom: 12,
    },
    icon: {
        marginRight: "5px",
        color: "#f7931e",
    },
    hide: {
        visibility: 'hidden',
    },
    xml: {
        maxHeight:250
    },

    msgtype :{
        textAlign: "left",
        color: "black",
        fontWeight: "bold",
        fontSize: fontSizeLarge,
        width: "15%",
        paddingBottom:0,
        borderBottom:0,
        paddingRight: 0,
    },

    localdate :{
        textAlign: "right",
        width: "10%",
        fontSize: fontSizeSmall,
        paddingLeft:0,
        "&:last-child": {
            paddingRight: 0
        },
        paddingBottom:0,
        borderBottom:0,
    },

    fromto: {
        textAlign: "left",
        fontSize: fontSizeNormal,
        paddingTop: 0,
        borderBottom: 0,
        paddingBottom:0
    },

    srvtype :{
        textAlign: "left",
        fontSize: fontSizeNormal,

        borderBottom: 0,
        paddingBottom: 0,
        paddingTop: 0,

    },

});


const messageInfoCard = (props)  => {

    const {classes} = props;
    const msgInfo = props.msgInfo;

    // Direction and From/To
    let direction;
    let fromto;
    if (msgInfo.isSent) {
        direction = "SENT";
        if (msgInfo.to.length > 0) {
            fromto = "To: " + msgInfo.to;
        }
        else {
            fromto = "To: Unknown";
        }
    } else {
        direction = "RECEIVED";
        if (msgInfo.from.length > 0) {
            fromto = "From: " + msgInfo.from;
        }
        else {
            fromto = "From: Unknown";
        }
    }

    const msgTo = msgInfo.to.length > 0 ? "To: " + msgInfo.to : null;
    const msgFrom = msgInfo.from.length > 0 ? "From: " + msgInfo.from : null;

    // Formatting the Date Time
    const timestamp = new Date(msgInfo.dateTime);
    const localeDate = date2String(timestamp);

    // Ack Style
    if (!msgInfo.ackResult) {
        msgInfo.ackResult = 'Not Received';
    }
    const isSuccess = msgInfo.ackResult.includes('Success');
    const ackTextColor = isSuccess ? "green" : "red";
    const rowAckCodeStyle = {color:ackTextColor, textAlign:'right'};

    // Message type with wrong ack result
    let iconMsg = IconMsgOk;
    let messageType = msgInfo.messageType;
    let messageTypeColor = null;
    let expandPreview = false;
    if (!isSuccess && messageType === 'Sync Ack') {
        messageType = messageType+" - "+msgInfo.ackResult;
        messageTypeColor = {color: "red"};
        iconMsg = IconMsgKo;
        expandPreview = true;
    }

    // Rendering
    return (
        <Card className={classes.card} key={msgInfo.id} >
            <CardContent>
                <TableContainer>
                    <Table size="small" aria-label="a dense table">
                        <TableBody>

                            <TableRow>
                                <TableCell className={classes.msgtype} style={messageTypeColor}>
                                    <img src={iconMsg} alt="thn" style={{paddingRight:6, width:22}}/>
                                    {messageType}
                                </TableCell>
                                <TableCell className={classes.localdate}><strong>{direction} ●</strong> {localeDate}</TableCell>
                            </TableRow>

                            {msgFrom ?
                            <TableRow>
                                <TableCell className={classes.fromto}>{msgFrom}</TableCell>
                                <TableCell className={classes.fromto}/>
                            </TableRow>
                            : null }

                            {msgTo ?
                                <TableRow>
                                    <TableCell className={classes.fromto}>{msgTo}</TableCell>
                                    <TableCell className={classes.fromto}/>
                                </TableRow>
                                : null }

                            {messageType.startsWith('Sync Ack') || messageType.startsWith('Discover') ? null :
                            <TableRow>
                                <TableCell className={classes.srvtype}>Service Type : {msgInfo.serviceType}</TableCell>
                                <TableCell className={classes.srvtype}/>
                            </TableRow>
                            }
                        </TableBody>
                    </Table>
                </TableContainer>
                <ExpansionPanelPreview body={props.body} numLines={6} expanded={expandPreview}/>
            </CardContent>
        </Card>
    );
}

export default withStyles(styles)(messageInfoCard);
