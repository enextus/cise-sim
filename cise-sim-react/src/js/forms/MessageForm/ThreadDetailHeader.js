import React from 'react';
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import {fontSizeLarge} from "../../layouts/Font";

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
        <TableContainer style={{paddingLeft:20}}>
            <Table size="small" aria-label="a dense table">
                <TableBody>
                    <TableRow>
                        <TableCell >
                            <Typography style={{padding:6, fontSize:fontSizeLarge}} variant="h5" component="h1" align={"left"}>
                                Thread Detail
                            </Typography>
                        </TableCell>
                    </TableRow>
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default withStyles(styles)(threadListHeader);