import React, {Component} from 'react';
import {Grid} from '@material-ui/core';
import Paper from "@material-ui/core/Paper";
import ButtonsPanel from "./ButtonsPanel";
import ChronoHistoryMessages from "./ThreadMessageList";
import ThreadMessageDetails from "./ThreadMessageDetails";
import Typography from "@material-ui/core/Typography";
import {withStyles} from "@material-ui/core/styles";


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

                <Grid item xs={4} >
                    <Paper elevation={3} >
                        <Typography variant="h5" component="h1" align={"center"}>
                            Thread Messages History
                        </Typography>
                        <ChronoHistoryMessages  store={this.props.store} />
                    </Paper>
                </Grid>

                <Grid item xs={8}>
                    <Paper elevation={3}>
                        <Typography variant="h5" component="h1" align={"center"}>
                            Thread Detail
                        </Typography>
                       <ThreadMessageDetails  store={this.props.store} />
                    </Paper>
                </Grid>

            </Grid>
        )
    }
}

export default withStyles(styles)(BodyThread)