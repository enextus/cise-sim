import React from 'react';
import {makeStyles} from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardContent from '@material-ui/core/CardContent';
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableRow from "@material-ui/core/TableRow";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableContainer from "@material-ui/core/TableContainer";

const useStyles = makeStyles({
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
});


const messageInfoCard = (props)  => {

    const classes = useStyles();
    const msgInfo = props.msgInfo;

    // Direction and background color
    let direction;
    let backColor;
    if (msgInfo.isSent) {
        direction = "SENT";
        backColor = "#ade6cb"
    } else {
        direction = "RECV";
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
            <CardContent onClick={props.selectMsg}>
                <TableContainer component={Paper}>
                    <Table size="small" aria-label="a dense table">
                        <TableBody>
                            <TableRow>
                                <TableCell>{msgInfo.messageType}</TableCell>
                                <TableCell>{direction}</TableCell>
                                <TableCell>{localeDate}</TableCell>
                            </TableRow>
                            <TableRow>
                                <TableCell>{msgInfo.serviceType}</TableCell>
                                <TableCell/>
                                <TableCell>{msgInfo.numTh}</TableCell>
                            </TableRow>
                        </TableBody>
                    </Table>
                </TableContainer>
            </CardContent>
        </Card>
    );
}

export default messageInfoCard;
