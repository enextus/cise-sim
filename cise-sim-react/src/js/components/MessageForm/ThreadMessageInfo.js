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

const styles = theme => ({
    root: {
        minWidth: 275,
        padding : 2,
        margin :  4
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
        marginBottom: 2,
    },

    cardcontent :{
        padding:8,
    },


    tablebody : {
        backgroundColor: "antiquewhite",
        '&:hover': {
            backgroundColor: "#F1614A",
        },

    },

    tablebodyselected : {
        backgroundColor:  "#F1614A",
    },

    cellmsgtype :{
        textAlign: "left"
    },

    celdirection: {
        width: '50%',
        textAlign: "left"
    },

    celllocaldate :{
        textAlign: "right",
        width: "30%"
    },
    cellsrvtype :{
        textAlign: "left"
    },

    cellempty :{

    },
    cellnumth :{
        textAlign: "right"
    },

});


const messageInfoCard = (props)  => {

    const {classes} = props;
    const msgInfo = props.msgInfo;

    // Direction and background color
    let direction;
    let fromto;
    let backColor;
    if (msgInfo.isSent) {
        direction = "SENT";
        if (msgInfo.to.length > 0) {
            fromto = "To: " + msgInfo.to;
        }
        else {
            fromto = "To: Unknown";
        }
        backColor = "#ade6cb"
    } else {
        direction = "RECV";
        if (msgInfo.from.length > 0) {
            fromto = "From: " + msgInfo.from;
        }
        else {
            fromto = "From: Unknown";
        }
        backColor = "#4795ff"
    }


    // Formatting the Date Time
    const timestamp = new Date(msgInfo.dateTime);
    /*
    const msec = timestamp.getMilliseconds();
    let padding = '';
    if (msec < 10) {
        padding ='00';
    }
    else if (msec < 100) {
        padding = '0';
    }
    const localeDate = timestamp.toLocaleString()+'.'+padding+msec;
   */
    const localeDate = timestamp.toLocaleString();


    return (
        <Card
            className={classes.root}
            key={msgInfo.id}
        >
            <CardContent
                className={classes.cardcontent}
                onClick={props.selectThread}
            >

                <TableContainer
                    component={Paper}
                >

                    <Table size="small" aria-label="a dense table">
                        <TableBody
                            className={props.selected ? classes.tablebodyselected:classes.tablebody}>

                            <TableRow>
                                <TableCell className={classes.cellmsgtype} component="th" scope="row">{msgInfo.messageType} ({direction})</TableCell>
                                <TableCell className={classes.celllocaldate}>{localeDate}</TableCell>
                            </TableRow>
                            <TableRow><TableCell align={"left"} component="th" scope="row">{fromto}</TableCell><TableCell style={{width:'1%'}}/></TableRow>
                            <TableRow>
                                <TableCell className={classes.cellsrvtype} component="th" scope="row">{msgInfo.serviceType}</TableCell>
                                <TableCell className={classes.cellnumth}>{msgInfo.numTh}</TableCell>
                            </TableRow>

                        </TableBody>
                    </Table>
                </TableContainer>
            </CardContent>
        </Card>
    );
}

export default withStyles(styles)(messageInfoCard);
