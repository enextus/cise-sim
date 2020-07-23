import React, {Component} from 'react';
import {Grid} from '@material-ui/core';
import CreateMessageModal from "./CreateMessageModal";
import IncidentMessageModal from "./IncidentMessageModal";
import DiscoveryMessageModal from "./DiscoveryMessageModal";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import {observer} from "mobx-react";

// Set of utility/functional Buttons
@observer
export default class ButtonsPanel extends Component {


    render() {
        const showIncident = this.getServiceStore().serviceSelf.showIncident;
        const discoverySender = this.getServiceStore().serviceSelf.discoverySender;
        return (
            <TableContainer>
                <Table size="small">
                    <TableBody>
                        <TableRow>
                            <TableCell>
                                <Grid container alignItems="flex-end" justify="flex-end" direction="row">
                                    {discoverySender ? <DiscoveryMessageModal store={this.props.store} sender={discoverySender}/> : null}
                                    {showIncident ? <IncidentMessageModal store={this.props.store} /> : null }
                                    <CreateMessageModal store={this.props.store} />
                                </Grid>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }
    getServiceStore() {
        return this.props.store.serviceStore;
    }
}
