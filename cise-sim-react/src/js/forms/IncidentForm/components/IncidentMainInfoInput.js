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

import React, {Component} from 'react';
import {withStyles} from '@material-ui/core/styles';

import IncidentSelect from "../../CommonComponents/SelectorInfo";
import Tooltip from "@material-ui/core/Tooltip";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import Typography from "@material-ui/core/Typography";
import TextField from "@material-ui/core/TextField";
import Paper from "@material-ui/core/Paper";

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
    icon : {
        padding: 0,
        margin: 0,
        color: "red",
    },
    cellicon : {
        padding: 4,
        width: 16,
    }


});

class IncidentMainInfoInput extends Component {

    state = {

        incidentType: undefined,

        isValidLatitude:false,
        isValidLongitude:false,

        listIdVessel: [0],
        lastIdVessel: 0,

        listIdContent: [0],
        lastIdContent: 0,
    }

    constructor(props) {
        super(props);
    }

    getIncidentStore() {
        return this.props.store.incidentStore
    };

    // Incident Type
    handleChangeType = (event) => {

        this.setState({incidentType: event.target.value})

        //  this.setState((prevState) => {return {incidentType: event.target.value};});

        this.getIncidentStore().getIncidentInputInfo().incidentType =  event.target.value;
    }

    getSelectType() {
        const list = this.getIncidentStore().labelIncidentType
        return <IncidentSelect
            title="Incident"
            listValueLabel={list}
            change={this.handleChangeType}
        />
    }

    // Sub Type
    handleChangeSubType = (event) => {
        this.getIncidentStore().getIncidentInputInfo().subType =  event.target.value;
    }

    getSelectSubType() {
        let list =[];
        if (this.state.incidentType) {
            list = this.getIncidentStore().labelIncidentSubTypeList[this.state.incidentType];
        }
        else {
            list[0] = {value:'empty', label:'Select Incident'};
        }

        return <IncidentSelect
            title="Sub Type"
            listValueLabel={list}
            change={this.handleChangeSubType}
        />

    }

    // Latitude
    handleChangeLatitude = (event) => {
        const isValid = isFinite(event.target.value) && Math.abs(event.target.value) <= 90;
        if (isValid) {
            this.getIncidentStore().getIncidentInputInfo().latitude =  event.target.value;
        }
        this.setState({isValidLatitude:isValid});
    }

    getLatitudineInput() {

        return  <Tooltip title={"Insert the Latitude value"} >
            <TextField
                name="latitudeId"
                label="Latitude"
                fullWidth={true}
                color="primary"
                variant="outlined"
                // value={this.state.latitude}
                onChange={this.handleChangeLatitude}
                error={!this.state.isValidLatitude}
            />
        </Tooltip>
    }

    // Longitude
    handleChangeLongitude = (event) => {

        const isValid = isFinite(event.target.value) && Math.abs(event.target.value) <= 180;
        if (isValid) {
            this.getIncidentStore().getIncidentInputInfo().longitude =  event.target.value;
        }
        this.setState({isValidLongitude:isValid})
    }

    getLongitudeInput() {
        return  <Tooltip title={"Insert the Longitude value"} >
            <TextField
                name="longitudeId"
                label="Longitude"
                fullWidth={true}
                color="primary"
                variant="outlined"
                // value={this.state.longitude}
                onChange={this.handleChangeLongitude}
                error={!this.state.isValidLongitude}
            />
        </Tooltip>
    }


    render() {

        const {classes} = this.props;

        return (
            <TableContainer component={Paper} >

                <Typography variant="h6" component="h6" align={"left"}>
                    Incident main info
                </Typography>

                <Table size="small" aria-label="a dense table">
                    <TableBody>

                        <TableRow>

                            <TableCell>
                                {this.getSelectType()}
                            </TableCell>

                            <TableCell>
                                {this.getSelectSubType()}
                            </TableCell>

                        </TableRow>

                        <TableRow>
                            <TableCell>
                                {this.getLatitudineInput()}
                            </TableCell>

                            <TableCell>
                                {this.getLongitudeInput()}
                            </TableCell>

                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }
}

export default withStyles(styles)(IncidentMainInfoInput);