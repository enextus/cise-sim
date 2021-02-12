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
import MsgCounterUnsel from "../svg/msgs-counter-unselected.svg";
import MsgCounterSel from "../svg/msgs-counter-selected.svg";

import {fontSizeExtraSmall, fontSizeNormal, fontSizeSmall} from "../../../layouts/Font";
import Typography from "@material-ui/core/Typography";
import {date2String} from "../../CommonComponents/HelperFunctions";


const styles = theme => ({

    root: {
        minWidth: 275,
        padding : 2,
        margin :  4,
        paddingBottom:0,
        marginBottom: 0,
        marginRight:16,
        backgroundColor:"#cdeefd",
        '&:hover': {
            backgroundColor: "lightgrey",
        },

    },

    cardcontent :{
        padding:8,
        "&:last-child": {
            paddingBottom: 8
        },
    },

    msgtype :{
        textAlign: "left",
        color: "black",
        fontWeight: "bold",
        fontSize: fontSizeNormal,
        width: "35%",
        display:"inline-block"
    },

    localdate :{
        textAlign: "right",
        width: "65%",
        fontSize: fontSizeExtraSmall,
        display:"inline-block"
    },

    fromto: {
        textAlign: "left",
        fontSize: fontSizeExtraSmall,
        paddingBottom: 0,
        paddingTop: 0,
        paddingRight:0,
        borderBottom: 0,
    },

    srvtype :{
        textAlign: "left",
        fontSize: fontSizeSmall,
        paddingBottom: 0,
        paddingTop: 0,
        borderBottom: 0,
    },

    nummsg:{
        textAlign: "right",
        fontWeight: "bold",
        fontSize: fontSizeNormal,
        paddingBottom: 0,
        paddingTop: 0,
        borderBottom: 0,
    },

    specialicon: {
        borderBottom: 0,
    },

});


const messageInfoCard = (props)  => {

    const {classes} = props;
    const msgInfo = props.msgInfo;

    // Direction and From To
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
        direction = "RECV";
        if (msgInfo.from.length > 0) {
            fromto = "From: " + msgInfo.from;
        }
        else {
            fromto = "From: Unknown";
        }
    }


    // Formatting the Date Time
    const localeDate = date2String(msgInfo.mostRecentTimestamp);

    // Special effects
    let iconNumTh = MsgCounterUnsel;
    let redStyle = null;
    let cardStyle = [];
    if (!msgInfo.ackSuccess) {
        cardStyle = {marginRight:0,borderRight:16, borderRightColor: "red", borderRightStyle: "solid"}
        redStyle  = {color: "red"};
    }
    if (props.selected) {
        cardStyle.backgroundColor="lightgrey";
        iconNumTh = MsgCounterSel;
    }

    return (
        <Card
            key={msgInfo.id}
            className={classes.root}
            style={cardStyle}
        >
            <CardContent
                className={classes.cardcontent}
                onClick={props.selectThread}
            >
                <TableContainer>
                    <Table size="small" aria-label="a dense table" padding={"default"}>
                        <TableBody>

                            <TableRow >
                                <TableCell component="th" scope="row" style={{ borderBottom: 0, paddingRight: 0}}>
                                    <Typography className={classes.msgtype} style={redStyle}>{msgInfo.messageType}</Typography>
                                    <Typography className={classes.localdate}><strong>{direction} ●</strong> {localeDate}</Typography>
                                </TableCell>
                            </TableRow>

                            <TableRow >
                                <TableCell className={classes.fromto} component="th" scope="row">{fromto}</TableCell>
                            </TableRow>

                            <TableRow>
                                <TableCell className={classes.srvtype} component="th" scope="row">{msgInfo.serviceType}</TableCell>
                            </TableRow>

                            <TableRow>
                                <TableCell className={classes.nummsg}>
                                    {msgInfo.numTh}
                                    <img src={iconNumTh} alt="thn" style={{paddingLeft:4, width:20}}/>
                                </TableCell>
                            </TableRow>

                        </TableBody>
                    </Table>
                </TableContainer>
            </CardContent>
        </Card>
    );
}

export default withStyles(styles)(messageInfoCard);
