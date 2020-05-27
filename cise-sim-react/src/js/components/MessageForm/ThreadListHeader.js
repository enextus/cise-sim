import React from 'react';
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core/styles";
import MsgClearButton from "./MessageInfoClearButton";
import Paper from "@material-ui/core/Paper";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 8,
        margin: '16px auto',
        maxWidth: 800,
        overflowY: 'scroll',
        maxHeight: 800,
    },
    button: {
        margin: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },

});


const threadListHeader = (props)  => {

    const {classes} = props;

    return (
        <TableContainer component={Paper} >
            <Table size="small" aria-label="a dense table">
                <TableBody>
                    <TableRow>
                        <TableCell> <Typography variant="h5" component="h1" align={"left"}>
                            Thread Messages History
                        </Typography>
                        </TableCell>
                        <TableCell align={"right"}> <MsgClearButton messageStore={props.store.messageStore} /></TableCell>
                    </TableRow>
                </TableBody>
            </Table>
        </TableContainer>
    )
/*
    return (
        <Grid container>
            <Grid item xs={8} >
                <Typography variant="h5" component="h1" align={"center"}>
                    Thread Messages History
                </Typography>
            </Grid>
            <Grid item xs={4} >
                <MsgClearButton messageStore={props.store.messageStore} />
            </Grid>
        </Grid>
    )

 */
}

export default withStyles(styles)(threadListHeader);