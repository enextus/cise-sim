import React, {Component} from 'react';
import {Grid} from '@material-ui/core';
import TransitionsModal from "./SendMessageModal";
import IncidentMessageModal from "./IncidentMessageModal";

// Set of utility/functional Buttons
export default class ButtonsPanel extends Component {

    render() {

        return (
            <Grid container  alignItems="flex-start" justify="flex-start" direction="row">
                    <TransitionsModal store={this.props.store} />
                    <IncidentMessageModal store={this.props.store} />
            </Grid>
        )
    }
}
