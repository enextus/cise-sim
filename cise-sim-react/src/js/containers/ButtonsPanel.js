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
import ThreadListFilter from "../forms/ThreadForm/List/ThreadListFilter";

// Set of utility/functional Buttons
export default class ButtonsPanel extends Component {


    render() {
        const hideIncident = this.getServiceStore().serviceSelf.hideIncident;

        return (
            <TableContainer>
                <Table size="small">
                    <TableBody>
                        <TableRow>
                            <TableCell>
                                <ThreadListFilter  store={this.props.store}/>
                            </TableCell>
                            <TableCell>
                                <Grid container   alignItems="flex-end" justify="flex-end" direction="row">
                                    <DiscoveryMessageModal store={this.props.store} />
                                    {hideIncident ? null: <IncidentMessageModal store={this.props.store} /> }
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
