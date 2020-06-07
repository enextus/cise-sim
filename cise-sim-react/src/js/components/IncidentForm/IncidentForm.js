import React, {Component} from 'react';
import {Box, Button, Grid, Paper, TextField} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';

import IncidentSelect from "./IncidentSelectorInfo";
import Tooltip from "@material-ui/core/Tooltip";
import SendRoundedIcon from "@material-ui/icons/SendRounded";
import IncidentVesselInput from "./IncidentVesselInput";
import {AddBoxRounded, IndeterminateCheckBoxRounded} from "@material-ui/icons";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import IncidentMessageDto from "./dto/IncidentMessageDto";
import CloseRoundedIcon from '@material-ui/icons/CloseRounded';

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

//@observer
class IncidentForm extends Component {

    state = {

        incidentType: undefined,

        isValidLatitude:false,
        isValidLongitude:false,

        listIdVessel: [0],
        lastIdVessel: 0,
    }

    constructor(props) {
        super(props);
    }

    getIncidentStore() {
        return this.props.store.incidentStore
    };

    // Incident Type
    handleChangeType = (event) => {
        console.log("IncidentForm handleChangeType " +event.target.value);

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
        console.log("IncidentForm handleChangeSubType " +event.target.value);
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
        console.log("IncidentForm handleChangeLatitude " +event.target.value);
        const isValid = isFinite(event.target.value) && Math.abs(event.target.value) <= 90;
        if (isValid) {
            this.getIncidentStore().getIncidentInputInfo().latitude =  event.target.value;
        }
        this.setState({isValidLatitude:isValid});
    }

    getLatitudineInput() {

        return  <Tooltip title={"[Optional] Use this field to override the CorrelationId."} >
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
        console.log("IncidentForm handleChangeLongitude " +event.target.value);
        const isValid = isFinite(event.target.value) && Math.abs(event.target.value) <= 180;
        if (isValid) {
            this.getIncidentStore().getIncidentInputInfo().longitude =  event.target.value;
        }
        this.setState({isValidLongitude:isValid})
    }

    getLongitudeInput() {
        return  <Tooltip title={"[Optional] Use this field to override the CorrelationId."} >
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



    // Add Vessel Button
    handleAddVessel = () => {
        console.log("IncidentForm handleAddVessel ");
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
               Add Vessel
                <AddBoxRounded className={classes.rightIcon}/>

            </Button>
        )
    }


    // Submit button
    handleSubmit = () => {
        console.log("IncidentForm handleSubmit ");
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
        console.log("IncidentForm handleSubmit ");
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
                Had enough
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
        console.log("IncidentForm handleRemoveVessel idx "+idx);
        let newListIdVessel = this.state.listIdVessel.filter(value => value !== idx);

        this.setState({
            listIdVessel:newListIdVessel
        });
    }

    getIncidentLine(classes) {

        return  (
            <TableContainer component={Paper} >
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
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }

    getPositionLine(classes) {

        return  (
            <TableContainer component={Paper} >
                <Table size="small" aria-label="a dense table">
                    <TableBody>
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

    getVesselLine(classes, idx) {

        return  (
            <TableContainer component={Paper} key={idx} id={'vessel_'+idx}>
                <Table size="small" aria-label="a dense table">
                    <TableBody>
                        <TableRow>
                            <TableCell>
                                <Button
                                    id="clearMsg"
                                    color="secondary"
                                    variant="contained"
                                    className={classes.button}
                                    onClick={() => this.handleRemoveVessel(idx)}
                                    type="submit"
                                    disabled={idx===0}
                                >
                                  <IndeterminateCheckBoxRounded className={classes.rightIcon}/>
                                </Button>
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

    render() {

        const {classes} = this.props;

        console.log("Render IncidentForm");

       return (
            <Box p="8px" mt="20px" mx="20px" bgcolor="#eeeeee">
                <Paper  className={classes.root} >
                    <Grid container alignItems="flex-start" spacing={3}>

                        <Grid item xs={12}>
                            {this.getIncidentLine(classes)}
                        </Grid>

                        <Grid item xs={12}>
                            {this.getPositionLine(classes)}
                        </Grid>

                        <Grid item xs={12}>
                            {this.getAddVesselButton(classes)}
                        </Grid>


                        <Grid item xs={12}>
                            {this.getVesselInput(classes)}
                        </Grid>

                        <Grid item xs={4}>
                            {this.getEndButton(classes)}
                        </Grid>
                        <Grid item xs={4}>

                        </Grid>
                        <Grid item xs={4}>
                            {this.getSubmitButton(classes)}
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
            incidentMsg.vesselList.push(this.getIncidentStore().getVesselInputArrayItem(id))
        }

        console.log("sendMessage " + incidentMsg);
        // todo incidentMsg.contentList;

        const result = this.getIncidentStore().sendIncidentMessage(incidentMsg);

        console.log("sendMessage result " + result);

    }
}

export default withStyles(styles)(IncidentForm);

