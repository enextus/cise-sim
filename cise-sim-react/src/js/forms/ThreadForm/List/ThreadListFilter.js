import React, {Component} from "react";
import {Grid, withStyles} from "@material-ui/core";
import {withSnackbar} from "notistack";
import ButtonGroup from "@material-ui/core/ButtonGroup";
import Button from "@material-ui/core/Button";
import {buttonSizeSmall, fontSizeUltraSmall} from "../../../layouts/Font";


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
                    <Grid container   alignItems="flex-end" justify="flex-end" direction="row">
                        <ButtonGroup color="primary" aria-label="outlined primary button group" style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeUltraSmall}}>
                            <Button variant={this.state.variantAll} onClick={this.handleAllMessages}  style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeUltraSmall}}>All messages</Button>
                            <Button variant={this.state.variantFail} onClick={this.handleFailedMessages}  style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeUltraSmall}}>Errors</Button>
                        </ButtonGroup>
                    </Grid>
                </Grid>
            </Grid>
        )
    }

    getMessageStore() {
        return this.props.store.messageStore;
    }
}

export default withStyles(styles)(withSnackbar(ThreadListFilter))