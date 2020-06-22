import React, {Component} from "react";
import {Grid, withStyles} from "@material-ui/core";
import {withSnackbar} from "notistack";
import ButtonGroup from "@material-ui/core/ButtonGroup";
import Button from "@material-ui/core/Button";
import Typography from "@material-ui/core/Typography";

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
                <Grid item xs={1}>
                    <Typography variant="subtitle2"  align={"left"}>
                        Showing:
                    </Typography>
                </Grid>
                <Grid item xs={11} >
                    <Grid container   alignItems="flex-end" justify="flex-end" direction="row">
                <ButtonGroup color="primary" aria-label="outlined primary button group" size={"small"} >
                    <Button variant={this.state.variantAll} onClick={this.handleAllMessages} >All messages</Button>
                    <Button variant={this.state.variantFail} onClick={this.handleFailedMessages}>Failed request</Button>
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