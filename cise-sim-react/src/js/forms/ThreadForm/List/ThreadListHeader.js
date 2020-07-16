import React from 'react';
import {withStyles} from "@material-ui/core/styles";
import ThreadListClearButton from "./ThreadListClearButton";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import {Box} from "@material-ui/core";
import ThreadListFilter from "./ThreadListFilter";


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
        <Box>
        <TableContainer style={{paddingLeft:20}}>
            <Table size="small" aria-label="a dense table">
                <TableBody>
                    <TableRow style={{padding:0}}>
                        <TableCell align={"left"} style={{padding:0}}>
                            <ThreadListFilter store={props.store} />
                        </TableCell>
                        <TableCell align={"right"} style={{padding:0}}>
                            <ThreadListClearButton messageStore={props.store.messageStore} />
                        </TableCell>
                    </TableRow>
                </TableBody>
            </Table>
        </TableContainer>
        </Box>
    )
}

export default withStyles(styles)(threadListHeader);