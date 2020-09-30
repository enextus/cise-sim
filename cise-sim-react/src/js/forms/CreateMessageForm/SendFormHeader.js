import React from 'react';
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core/styles";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import TableContainer from "@material-ui/core/TableContainer";
import CloseRoundedIcon from '@material-ui/icons/CloseRounded';

import {fontSizeNormal} from "../../layouts/Font";
import IconButton from "@material-ui/core/IconButton";

const styles = theme => ({
    root: {
        backgroundColor: "white",
    },
    headertext : {
        fontWeight: "bold",
        fontSize: fontSizeNormal,
    }
});


const sendFormHeader = (props)  => {

    const {classes} = props;

    return (
        <TableContainer className={classes.root} >
            <Table size="small" aria-label="a dense table">
                <TableBody>
                    <TableRow>
                        <TableCell>
                            <Typography variant="h6" component="h1" align={"left"} style={{fontWeight: "bold",}}>
                               New Message
                            </Typography>
                        </TableCell>

                        <TableCell align={"right"} style={{paddingRight:0}}>
                            <IconButton
                                data-dismiss="create-message"
                                onClick={props.onclose}
                                fontSize="inherit">
                                <CloseRoundedIcon/></IconButton>
                        </TableCell>
                    </TableRow>
                </TableBody>
            </Table>
        </TableContainer>
    )
}

export default withStyles(styles)(sendFormHeader);