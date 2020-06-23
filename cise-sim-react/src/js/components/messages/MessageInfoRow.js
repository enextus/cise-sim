import React from 'react';
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

const msgTableRow = ( props ) => {

    // Direction and background color
    let direction;
    let backColor;
    if (props.msgInfo.isSent) {
        direction = "SENT";
        backColor = "#ade6cb"
    } else {
        direction = "RECV";
        backColor = "#4795ff"
    }

    // Formatting the Date Time
    const timestamp = new Date(props.msgInfo.dateTime);
    const msec = timestamp.getMilliseconds();
    let padding = '';
    if (msec < 10) {
        padding ='00';
    }
    else if (msec < 100) {
        padding = '0';
    }
    const localeDate = timestamp.toLocaleString()+'.'+padding+msec;

    // Style
    const rowStyle = {backgroundColor:backColor, align:'left'};

    return (
        <TableRow key={props.msgInfo.id} style={rowStyle} onClick={props.selectMsg}>
            <TableCell>{localeDate}</TableCell>
            <TableCell>{props.msgInfo.messageType}</TableCell>
            <TableCell>{props.msgInfo.serviceType}</TableCell>
            <TableCell>{direction}</TableCell>
        </TableRow>
    )
};

export default msgTableRow;