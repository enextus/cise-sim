import React, {Component} from 'react';
import {Grid} from '@material-ui/core';
import {observer} from 'mobx-react';
import Paper from "@material-ui/core/Paper";
import ButtonsPanel from "./ButtonsPanel";
import ChronoHistoryMessages from "./ChronoThreadMessages";
import ThreadMessageDetails from "./ThreadMessageDetails";


@observer
export default class BodyV2 extends Component {


    render() {

        return (
            <Grid container spacing={2}>

                <Grid item xs={12}>
                    <Paper elevation={3}>
                        <h1>da capire</h1>
                    </Paper>
                </Grid>

                <Grid item xs={12}>
                    <Paper elevation={3}>
                        <ButtonsPanel store={this.props.store} />
                    </Paper>
                </Grid>

                <Grid item xs={6}>
                    <Paper elevation={3} >
                        <ChronoHistoryMessages  store={this.props.store} />
                    </Paper>
                </Grid>

                <Grid item xs={6}>
                    <Paper elevation={3}>
                       <ThreadMessageDetails  store={this.props.store} />
                    </Paper>
                </Grid>

            </Grid>
        )
    }
}