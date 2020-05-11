import React from 'react';
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import {blue, green} from "@material-ui/core/colors";

const msgHsTable = ( props ) => {




    // Formatting the Date Time
    const timestamp = new Date(props.dateTime);
    const msec = timestamp.getMilliseconds();
    let padding = '';
    if (msec < 10) {
        padding ='00';
    }
    else if (msec < 100) {
        padding = '0';
    }
    const localeDate = timestamp.toLocaleString()+'.'+padding+msec;

    let out;
    if (props.isSent) {
        out = (
            <TableRow key={props.id} style={{backgroundColor:"#ade6cb"}}>
            <TableCell align="right"/>
            <TableCell align="right" color={green}>{props.messageType}</TableCell>
            <TableCell align="right" color={green}>{props.serviceType}</TableCell>
            <TableCell align="right" color={green}>{localeDate}</TableCell>
            </TableRow>);

    }
    else {
        out = (
            <TableRow key={props.id} style={{backgroundColor:'#9095ee'}}>
                <TableCell align="left" >{localeDate}</TableCell>
                <TableCell align="left" color={blue}>{props.messageType}</TableCell>
                <TableCell align="left" color={blue}>{props.serviceType}</TableCell>
                <TableCell align="left"/>
            </TableRow>);
    }
    //  <TableCell component="th" scope="row" align="right">{props.dateTime}</TableCell>
    return out;
};

export default msgHsTable;