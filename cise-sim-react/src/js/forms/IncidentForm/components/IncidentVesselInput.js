/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

import React, {Component} from "react";
import IncidentSelect from "../../CommonComponents/SelectorInfo";
import Tooltip from "@material-ui/core/Tooltip";
import {Paper, TextField} from "@material-ui/core";
import {withStyles} from "@material-ui/core/styles";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";


const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 16,
        margin: '16px auto',
        maxWidth: 800
    },

    button: {
        margin: theme.spacing(1),
    },

    rightIcon: {
        marginLeft: theme.spacing(1),
    },
});

class IncidentVesselInput extends Component {

    constructor(props) {
        super(props);
    }

    getIncidentStore() {
        return this.props.store.incidentStore
    };

    // Vessel Type
    handleChangeVesselType = (event) => {
        console.log("IncidentVesselInput handleChangeVesselType " +event.target.value);
        this.getIncidentStore().getVesselInputArrayItem(this.props.id).vesselType = event.target.value;
    }

    getSelectVessel() {
        const list = this.getIncidentStore().labelVesselTypeList;
        return <IncidentSelect
            title="Vessel Type"
            listValueLabel={list}
            change={this.handleChangeVesselType}
        />
    }

    // Role
    handleChangeRole = (event) => {
        console.log("IncidentVesselInput handleChangeRole " +event.target.value);
        this.getIncidentStore().getVesselInputArrayItem(this.props.id).role = event.target.value;

    }

    getSelectRole() {
        const list = this.getIncidentStore().labelRoleList;
        return <IncidentSelect
            title="Role Type"
            listValueLabel={list}
            change={this.handleChangeRole}
        />
    }

    // imoNumber
    handleChangeImonumber = (event) => {
        console.log("IncidentVesselInput handleChangeImonumber " +event.target.value);
        this.getIncidentStore().getVesselInputArrayItem(this.props.id).imoNumber = event.target.value;
    }

    getImonumberInput() {
        return  <Tooltip title={"Insert the Imo number value"} >
            <TextField
                name="imonumberId"
                label="Imo number"
                fullWidth={true}
                color="primary"
                variant="outlined"
                value={this.imoNumber}
                onChange={this.handleChangeImonumber}
            />
        </Tooltip>
    }

    // imoNumber
    handleChangeMmsi = (event) => {
        console.log("IncidentVesselInput handleChangeMmsi " +event.target.value);
        this.getIncidentStore().getVesselInputArrayItem(this.props.id).mmsi = event.target.value;
    }

    getMmsiInput() {
        return  <Tooltip title={"Insert the mmsi value"} >
            <TextField
                name="mmsiId"
                label="mmsi"
                fullWidth={true}
                color="primary"
                variant="outlined"
                value={this.mmsi}
                onChange={this.handleChangeMmsi}
            />
        </Tooltip>
    }

    render() {

        const {classes} = this.props;

        return (
            <TableContainer component={Paper} >
                <Table size="small" aria-label="a dense table">
                    <TableBody>
                        <TableRow>

                            <TableCell>
                                {this.getSelectVessel()}
                            </TableCell>

                            <TableCell>
                                {this.getSelectRole()}
                            </TableCell>

                            <TableCell>
                                {this.getImonumberInput()}
                            </TableCell>

                            <TableCell>
                                {this.getMmsiInput()}
                            </TableCell>

                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }
}

export default withStyles(styles)(IncidentVesselInput);