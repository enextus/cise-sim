import React from "react";
import TableContainer from "@material-ui/core/TableContainer";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableBody from "@material-ui/core/TableBody";
import MesRow from "./MessageInfoRow";
import {withStyles} from "@material-ui/core";


const headField = ['Date & Time', 'Message Type', 'Service Type', 'Direction'];

const styles = theme => ({
    table: {
    minWidth: 250,
    maxWidth: 650 }
})

const msgTable = ( props ) => {

    const {classes} = props;

    return (
        <TableContainer component={Paper}>
            <Table className={classes.table} size="small" aria-label="a dense table">
                <TableHead>
                    <TableRow>
                        {headField.map((t) => <TableCell align="center">{t}</TableCell>)}
                    </TableRow>
                </TableHead>
                <TableBody>
                    {props.msgRcv.map((msg) => <MesRow msgInfo={msg} key={msg.id}/>)}
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default withStyles(styles)(msgTable);