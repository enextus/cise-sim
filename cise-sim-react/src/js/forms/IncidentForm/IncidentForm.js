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
import {Box, Button, Grid, Paper} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';

import SendRoundedIcon from "@material-ui/icons/SendRounded";
import IncidentVesselInput from "./components/IncidentVesselInput";
import {AddBoxRounded, IndeterminateCheckBoxRounded} from "@material-ui/icons";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import IncidentMessageDto from "./dto/IncidentMessageDto";
import CloseRoundedIcon from '@material-ui/icons/CloseRounded';
import IconButton from "@material-ui/core/IconButton";
import IncidentContentInput from "./components/IncidentContentInput";
import IncidentMainInfoInput from "./components/IncidentMainInfoInput";
import {buttonSizeSmall, fontSizeSmall} from "../../layouts/Font";

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
        maxHeight: buttonSizeSmall,
        fontSize:fontSizeSmall
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

//@observer
class IncidentForm extends Component {

    state = {

        incidentType: undefined,

        isValidLatitude:false,
        isValidLongitude:false,

        listIdVessel: [0],
        lastIdVessel: 0,

        listIdContent: [],
        lastIdContent: 0,
    }

    constructor(props) {
        super(props);
    }

    getIncidentStore() {
        return this.props.store.incidentStore
    };


    // Add Vessel Button
    handleAddVessel = () => {

        let i = this.state.lastIdVessel;
        let newListIdVessel = [...this.state.listIdVessel];
        i++;
        newListIdVessel.push(i);

        this.setState({
            lastIdVessel:i,
            listIdVessel:newListIdVessel
        });
    }

    getAddVesselButton(classes) {

        return (
            <Button
                id="clearMsg"
                color="secondary"
                variant="contained"
                className={classes.button}
                onClick={this.handleAddVessel}
                type="submit"
            >
                Vessels <AddBoxRounded className={classes.rightIcon}/>

            </Button>
        )
    }

    // Add Content Button
    handleAddContent = () => {

        let i = this.state.lastIdContent;
        let newListIdContent = [...this.state.listIdContent];
        i++;
        newListIdContent.push(i);

        this.setState({
            lastIdContent:i,
            listIdContent:newListIdContent
        });
    }

    getAddContentButton(classes) {

        return (
            <Button
                id="clearMsg"
                color="secondary"
                variant="contained"
                className={classes.button}
                onClick={this.handleAddContent}
                type="submit"
            >
                Attach documents <AddBoxRounded className={classes.rightIcon}/>

            </Button>
        )
    }

    // Submit button
    handleSubmit = () => {
        this.sendMessage();
    }

    getSubmitButton(classes) {

        return (
            <Button
                id="clearMsg"
                color="secondary"
                variant="contained"
                className={classes.button}
                onClick={this.handleSubmit}
                type="submit"
            >
                Send Message
                <SendRoundedIcon className={classes.rightIcon}/>

            </Button>
        )
    }

    // Submit button
    handleEnd = () => {

        this.getIncidentStore().cleanResources();
        this.props.onclose();
    }

    getEndButton(classes) {

        return (
            <Button
                id="clearMsg"
                color="secondary"
                variant="contained"
                className={classes.button}
                onClick={this.handleEnd}
                type="submit"
            >
                Close
                <CloseRoundedIcon className={classes.rightIcon}/>

            </Button>
        )
    }

    getVesselInput(classes) {
        let vesselList = [];
        for (let i of this.state.listIdVessel) {
            vesselList.push( this.getVesselLine(classes, i));
        }
        return vesselList;
    }

    // Remove vessel button
    handleRemoveVessel = (idx) => {

        let newListIdVessel = this.state.listIdVessel.filter(value => value !== idx);

        this.setState({
            listIdVessel:newListIdVessel
        });
    }

    // Content
    getContentInput(classes) {
        let contentList = [];
        for (let i of this.state.listIdContent) {
            contentList.push( this.getContentLine(classes, i));
        }
        return contentList;
    }

    // Remove content button
    handleRemoveContent = (idx) => {

        let newListIdContent = this.state.listIdContent.filter(value => value !== idx);

        this.setState({
            listIdContent:newListIdContent
        });
    }

    getVesselLine(classes, idx) {

        return  (
            <TableContainer component={Paper} key={idx} id={'vessel_'+idx}>
                <Table size="small" aria-label="a dense table">
                    <TableBody>
                        <TableRow>
                            <TableCell className={classes.cellicon}>
                                <IconButton
                                    variant="contained"
                                    className={classes.icon}
                                    onClick={() => this.handleRemoveVessel(idx)}
                                    disabled={this.state.listIdVessel.length===1}
                                    size="medium"
                                >
                                  <IndeterminateCheckBoxRounded  size="large"/>
                                </IconButton>
                            </TableCell>
                            <TableCell>
                                <IncidentVesselInput store={this.props.store} id={idx}/>
                            </TableCell>
                    </TableRow>
                </TableBody>
            </Table>
        </TableContainer>
        )
    }

    getContentLine(classes, idx) {

        return  (
            <TableContainer component={Paper} key={idx} id={'content_'+idx}>
                <Table size="small" aria-label="a dense table">
                    <TableBody>
                        <TableRow>
                            <TableCell className={classes.cellicon}>
                                <IconButton
                                    variant="contained"
                                    className={classes.icon}
                                    onClick={() => this.handleRemoveContent(idx)}
                                    size="medium"
                                >
                                    <IndeterminateCheckBoxRounded  size="large"/>
                                </IconButton>
                            </TableCell>
                            <TableCell align={"left"}>
                                <IncidentContentInput store={this.props.store} id={idx}/>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }

    getBottomButtons(classes) {

        return  (
            <TableContainer component={Paper} >
                <Table size="small" aria-label="a dense table">
                    <TableBody>
                        <TableRow>
                            <TableCell align={"left"}>
                                {this.getEndButton(classes)}
                            </TableCell>
                            <TableCell align={"right"}>
                                {this.getSubmitButton(classes)}
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }

    render() {

        const {classes} = this.props;

        return (
            <Box p="8px" mt="20px" mx="20px" bgcolor="#eeeeee">
                <Paper  className={classes.root} >
                    <Grid container alignItems="flex-start" spacing={3}>

                        <Grid item xs={12}>
                            <IncidentMainInfoInput store={this.props.store} />
                        </Grid>

                         <Grid item xs={12}>
                            {this.getAddVesselButton(classes)}
                            {this.getVesselInput(classes)}
                        </Grid>

                        <Grid item xs={12}>
                            {this.getAddContentButton(classes)}
                            {this.getContentInput(classes)}
                        </Grid>

                        <Grid item xs={12}>
                            {this.getBottomButtons(classes)}
                        </Grid>

                    </Grid>
                </Paper>
            </Box>
        )
    }

    sendMessage() {
        const incidentMsg = new IncidentMessageDto();

        incidentMsg.incident = this.getIncidentStore().getIncidentInputInfo();
        for (let id of this.state.listIdVessel) {
            const item = this.getIncidentStore().getVesselInputArrayItem(id);
            if (  item.vesselType && item.role && item.imoNumber && item.mmsi) {
                incidentMsg.vesselList.push(item)
            }
        }

        for (let id of this.state.listIdContent) {
            const item = this.getIncidentStore().getContentInputArrayItem(id);
            if ( item.content && item.mediaType) {
                incidentMsg.contentList.push(item);
            }
        }

        const result = this.getIncidentStore().sendIncidentMessage(incidentMsg);
    }
}

export default withStyles(styles)(IncidentForm);

