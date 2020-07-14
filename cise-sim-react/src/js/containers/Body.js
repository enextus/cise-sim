import React, {Component} from 'react';
import {Grid} from '@material-ui/core';
import Paper from "@material-ui/core/Paper";
import ButtonsPanel from "./ButtonsPanel";
import ThreadMessageList from "../forms/ThreadForm/ThreadMessageList";
import ThreadMessageDetails from "../forms/ThreadForm/ThreadMessageDetails";
import {withStyles} from "@material-ui/core/styles";
import ThreadListHeader from "../forms/ThreadForm/List/ThreadListHeader";
import ThreadDetailHeader from "../forms/ThreadForm/Details/ThreadDetailHeader";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 16,
        margin: '16px auto',
        maxWidth: 800
    },
});

class Body extends Component {

    render() {
        const {classes} = this.props;

        return (
                <Grid container spacing={2} style={{backgroundColor:"white"}}>

                    <Grid item xs={12}>
                        <Paper elevation={3}>
                            <h1>Here nav bar</h1>
                        </Paper>
                    </Grid>

                    <Grid item xs={12}>
                        <ButtonsPanel store={this.props.store} />
                    </Grid>

                    <Grid item xs={3} style={{paddingRight:0}}>
                        <ThreadListHeader store={this.props.store}/>
                        <ThreadMessageList  store={this.props.store} />
                    </Grid>

                    <Grid item xs={9}>
                        <ThreadDetailHeader store={this.props.store} />
                        <ThreadMessageDetails  store={this.props.store} />
                    </Grid>

                </Grid>
        )
    }
}

export default withStyles(styles)(Body)