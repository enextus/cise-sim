import React, {Component} from 'react';
import {Grid} from '@material-ui/core';
import CreateMessageModal from "./SendMessageModal";
import IncidentMessageModal from "./IncidentMessageModal";
import DiscoveryMessageModal from "./DiscoveryMessageModal";

// Set of utility/functional Buttons
export default class ButtonsPanel extends Component {

    render() {

        const hideIncident = this.getServiceStore().serviceSelf.hideIncident;

        return (
            <Grid container   alignItems="flex-end" justify="flex-end" direction="row">
                <DiscoveryMessageModal store={this.props.store} />
                {hideIncident ? null: <IncidentMessageModal store={this.props.store} /> }
                <CreateMessageModal store={this.props.store} />
            </Grid>
        )
    }

    getServiceStore() {
        return this.props.store.serviceStore;
    }
}
