import React, {Component} from 'react';
import {Grid} from '@material-ui/core';
import Paper from "@material-ui/core/Paper";
import ButtonsPanel from "./ButtonsPanelRight";
import ChronoHistoryMessages from "./ThreadMessageList";
import ThreadMessageDetails from "./ThreadMessageDetails";
import {withStyles} from "@material-ui/core/styles";
import ThreadListHeader from "../forms/MessageForm/ThreadListHeader";
import ThreadDetailHeader from "../forms/MessageForm/ThreadDetailHeader";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 16,
        margin: '16px auto',
        maxWidth: 800
    },
});

class BodyThread extends Component {

    render() {

        return (
            <Paper elevation={3}>
            <Grid container spacing={2}>

                <Grid item xs={12}>
                    <Paper elevation={3}>
                        <h1>da capire</h1>
                    </Paper>
                </Grid>

                <Grid item xs={12}>
                        <ButtonsPanel store={this.props.store} />
                </Grid>

                <Grid item xs={3} >
                       <ThreadListHeader store={this.props.store}/>
                       <ChronoHistoryMessages  store={this.props.store} />
                </Grid>

                <Grid item xs={9}>
                        <ThreadDetailHeader store={this.props.store} />
                       <ThreadMessageDetails  store={this.props.store} />
                </Grid>

            </Grid>
            </Paper>
        )
    }
}

export default withStyles(styles)(BodyThread)