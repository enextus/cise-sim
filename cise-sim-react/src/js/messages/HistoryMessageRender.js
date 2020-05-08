import React from 'react';
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

const msgHsTable = ( props ) => {

    let direction;
    if (props.isSent) {
        direction = "SENT";
    } else {
        direction = "RECV";
    }

    //  <TableCell component="th" scope="row" align="right">{props.dateTime}</TableCell>
    return (
        <TableRow key={props.id}>
            <TableCell align="right">{props.dateTime}</TableCell>
            <TableCell align="right">{props.messageType}</TableCell>
            <TableCell align="center">{props.serviceType}</TableCell>
            <TableCell align="center">{direction}</TableCell>
        </TableRow>
    )
};

export default msgHsTable;