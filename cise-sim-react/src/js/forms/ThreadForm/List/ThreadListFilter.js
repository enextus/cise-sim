import React, {Component} from "react";
import {Grid, withStyles} from "@material-ui/core";
import {withSnackbar} from "notistack";
import ButtonGroup from "@material-ui/core/ButtonGroup";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";
import {buttonSizeSmall, fontSizeExtraSmall, fontSizeSmall} from "../../../layouts/Font";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";

const styles = theme => ({
    button: {
        margin: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },
    palette: {
        primary: "pink",
        secondary: "black",
    },
});


class ThreadListFilter extends Component {

    state = {
        variantAll:'contained',
        variantFail:''
    }

    constructor(props) {
        super(props);
    }

    handleAllMessages = () => {
        this.getMessageStore().updateThreadFilter(null);
        this.setState({variantAll:'contained', variantFail:''});
    }

    handleFailedMessages = () => {
        this.getMessageStore().updateThreadFilter('fail');
        this.setState({variantAll:'', variantFail:'contained'});
    }

    render() {
        const {classes} = this.props;
        return (
            <Grid container   alignItems="flex-start" justify="flex-start" direction="row">

                <Grid item xs={12} >
                    <Grid container   alignItems="flex-start" justify="flex-start" direction="row">
                        <Typography variant="h6"  align={"left"}>
                            Showing:
                        </Typography>
                <ButtonGroup color="primary" aria-label="outlined primary button group" style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeExtraSmall}}>
                    <Button variant={this.state.variantAll} onClick={this.handleAllMessages}  style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeExtraSmall}}>All messages</Button>
                    <Button variant={this.state.variantFail} onClick={this.handleFailedMessages}  style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeExtraSmall}}>Failed request</Button>
                </ButtonGroup>
                    </Grid>
            </Grid>
            </Grid>
        )
    }

    renderNEW() {
        const {classes} = this.props;
        return (
            <TableContainer>
                <Table>
                    <TableBody>
                        <TableRow>
                            <TableCell align={"left"}>
                                <Typography variant="subtitle2"  align={"left"}>
                                    Showing:
                                </Typography>
                            </TableCell>
                            <TableCell align={"left"}>
                                <ButtonGroup color="primary" aria-label="outlined primary button group" style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeSmall}}>
                                    <Button variant={this.state.variantAll} onClick={this.handleAllMessages}  style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeSmall}}>All messages</Button>
                                    <Button variant={this.state.variantFail} onClick={this.handleFailedMessages}  style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeSmall}}>Failed request</Button>
                                </ButtonGroup>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }

    getMessageStore() {
        return this.props.store.messageStore;
    }
}

export default withStyles(styles)(withSnackbar(ThreadListFilter))