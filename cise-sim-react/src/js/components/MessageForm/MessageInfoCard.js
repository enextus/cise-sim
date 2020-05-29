import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableContainer from "@material-ui/core/TableContainer";
import {ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Typography} from "@material-ui/core";
import ExpandMoreIcon from "@material-ui/icons/ExpandMore";
import EmailIcon from "@material-ui/icons/Email";
import XmlContent from "../common/XmlContent";


const styles = theme => ({
    root: {
        minWidth: 275,
    },

    card : {
        minWidth: 275,
        marginBottom: 4,
    },

    bullet: {
        display: 'inline-block',
        margin: '0 2px',
        transform: 'scale(0.8)',
    },
    title: {
        fontSize: 14,
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
    }
});


const messageXml = (props, classes) => {

    return (
        <ExpansionPanel>
            <ExpansionPanelSummary
                expandIcon={<ExpandMoreIcon/>}
                aria-controls="receiveMessageContent"
                id="ReceivedMessage">

                <EmailIcon className={classes.icon}/>
                <Typography className={classes.title}><b>Show message</b></Typography>

            </ExpansionPanelSummary>
            <ExpansionPanelDetails>

                <XmlContent>{props.body}</XmlContent>

            </ExpansionPanelDetails>
        </ExpansionPanel>
    )
}


const messageInfoCard = (props)  => {

    const {classes} = props;
    const msgInfo = props.msgInfo;

    // Direction and From/To
    let direction;
    let fromto;
    if (msgInfo.isSent) {
        direction = "Sent";
        if (msgInfo.to.length > 0) {
            fromto = "To: " + msgInfo.to;
        }
        else {
            fromto = "To: Unknown";
        }
    } else {
        direction = "Received";
        if (msgInfo.from.length > 0) {
            fromto = "From: " + msgInfo.from;
        }
        else {
            fromto = "From: Unknown";
        }
    }

    // Formatting the Date Time
    const timestamp = new Date(msgInfo.dateTime);
    const msec = timestamp.getMilliseconds();
    let padding = '';
    if (msec < 10) {
        padding ='00';
    }
    else if (msec < 100) {
        padding = '0';
    }
    const localeDate = timestamp.toLocaleString()+'.'+padding+msec;

    // Ack Style
    if (!msgInfo.ackResult) {
        msgInfo.ackResult = 'Not Received';
    }
    const isSuccess = msgInfo.ackResult.includes('Success');
    const ackTextColor = isSuccess ? "green" : "red";
    const rowAckCodeStyle = {color:ackTextColor, textAlign:'right'};
    const cardStyle = { border: "2px solid " + ackTextColor };
    const ackTableCell = <TableCell style={rowAckCodeStyle}> Ack : {msgInfo.ackResult}</TableCell>

    // Rendering
    return (
        <Card className={classes.card} key={msgInfo.id} style={cardStyle} >
            <CardContent>
                <TableContainer component={Paper} >
                    <Table size="small" aria-label="a dense table">
                        <TableBody>
                            <TableRow>
                                <TableCell> Type : {msgInfo.messageType}</TableCell>
                                <TableCell align={"right"}>{direction}</TableCell>
                            </TableRow>
                            <TableRow><TableCell align={"left"}> {fromto}</TableCell><TableCell/></TableRow>
                            <TableRow><TableCell> Time : {localeDate}</TableCell><TableCell/></TableRow>
                            <TableRow>
                                <TableCell> Service Type : {msgInfo.serviceType}</TableCell>
                                {ackTableCell}
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>
                {messageXml(props, classes)}
            </CardContent>
        </Card>
    );
}

export default withStyles(styles)(messageInfoCard);
