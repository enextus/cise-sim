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
        <ExpansionPanel className={classes.root}>
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

    // Direction and background color
    let direction;
    let backColor;
    if (msgInfo.isSent) {
        direction = "Sent";
        backColor = "#ade6cb"
    } else {
        direction = "Received";
        backColor = "#4795ff"
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

    return (
        <Card className={classes.root} key={msgInfo.id}>
            <CardContent>
                <TableContainer component={Paper}>
                    <Table size="small" aria-label="a dense table">
                        <TableBody>
                            <TableRow>
                                <TableCell>Type : {msgInfo.messageType}</TableCell>
                                <TableCell align={"right"}>{direction}</TableCell>
                            </TableRow>
                            <TableRow><TableCell> Time : {localeDate}</TableCell><TableCell/></TableRow>
                            <TableRow><TableCell> Service Type : {msgInfo.serviceType}</TableCell><TableCell/></TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>
                {messageXml(props, classes)}
            </CardContent>
        </Card>
    );
}

export default withStyles(styles)(messageInfoCard);
