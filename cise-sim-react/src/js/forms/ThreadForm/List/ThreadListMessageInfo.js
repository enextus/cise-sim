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
        width: "30%",
        display:"inline-block"
    },

    localdate :{
        textAlign: "right",
        width: "69%",
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
