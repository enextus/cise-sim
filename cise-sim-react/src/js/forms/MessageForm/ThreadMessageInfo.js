import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import Table from "@material-ui/core/Table";
import TableContainer from "@material-ui/core/TableContainer";

const styles = theme => ({

    root: {
        minWidth: 275,
        padding : 2,
        margin :  4,
        paddingBottom:0,
        marginBottom: 0,
        marginRight:16,
        backgroundColor: "white",
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
        fontSize: "large",
        borderBottom: 0,
        width: "15%",
    },

    localdate :{
        textAlign: "right",
        width: "10%",
        fontSize: "smaller",
        borderBottom: 0,
    },

    fromto: {
        textAlign: "left",
        paddingBottom: 0,
        paddingTop: 0,
        fontSize: "smaller",
        borderBottom: 0,

    },

    srvtype :{
        textAlign: "left",
        paddingBottom: 0,
        paddingTop: 0,
        fontSize: "smaller",
        borderBottom: 0,
    },

    nummsg:{
        textAlign: "right",
        fontWeight: "bold",
        fontSize: "large",
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
    const timestamp = new Date(msgInfo.mostRecentTimestamp);
    const localeDate = timestamp.toLocaleString().replace(',', ' ° ');

    // Special effects
    let redStyle = null;
    let cardStyle = [];
    if (!msgInfo.ackSuccess) {
        cardStyle = {marginRight:0,borderRight:16, borderRightColor: "red", borderRightStyle: "solid"}
        redStyle  = {color: "red"};
    }
    if (props.selected) {
        cardStyle.backgroundColor="lightgrey";
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
                        <TableBody

                        >

                            <TableRow>
                                <TableCell
                                    className={classes.msgtype} component="th" scope="row"
                                    style={redStyle}
                                >
                                    {msgInfo.messageType} {direction}
                                </TableCell>
                                <TableCell className={classes.localdate}>{localeDate}</TableCell>
                            </TableRow>

                            <TableRow >
                                <TableCell className={classes.fromto} align={"left"} component="th" scope="row">{fromto}</TableCell>
                                <TableCell className={classes.fromto} style={{width:'1%'}}/>
                            </TableRow>

                            <TableRow>
                                <TableCell className={classes.srvtype} component="th" scope="row">{msgInfo.serviceType}</TableCell>
                                <TableCell className={classes.srvtype} style={{width: '1%'}}/>
                            </TableRow>

                            <TableRow>
                                <TableCell className={classes.specialicon} component="th" scope="row"/>
                                <TableCell className={classes.nummsg}>{msgInfo.numTh}</TableCell>
                            </TableRow>

                        </TableBody>
                    </Table>
                </TableContainer>
            </CardContent>
        </Card>
    );
}

export default withStyles(styles)(messageInfoCard);
