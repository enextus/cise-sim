import React, {Component} from 'react';
import {Grid} from '@material-ui/core';
import CreateMessage from "./SendMessageModal";
import DiscoveryMessageModal from "./DiscoveryMessageModal";

// Set of utility/functional Buttons
export default class ButtonsPanel extends Component {

    render() {

        return (
            <Grid container  alignItems="flex-end" justify="flex-end" direction="row">

                <DiscoveryMessageModal store={this.props.store} />
                <CreateMessage store={this.props.store} />
            </Grid>
        )
    }
}
