import React, {Component} from 'react';
import {Box, Button, Grid, Paper, TextField} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';

import IncidentSelect from "./IncidentSelectorInfo";
import Tooltip from "@material-ui/core/Tooltip";
import SendRoundedIcon from "@material-ui/icons/SendRounded";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Input from "@material-ui/core/Input";
import FormHelperText from "@material-ui/core/FormHelperText";

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
        subType: undefined,
        vesselType: undefined,
        role: undefined,
        latitude: '',
        longitude: '',
        imoNumber: undefined,
        mmsi: undefined,

        isValidLatitude:false,
        isValidLongitude:false,

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
    }

    getSelectType() {
        const list = this.getIncidentStore().labelIncidentType
        return <IncidentSelect
            title="Incident Type"
            listValueLabel={list}
            change={this.handleChangeType}
        />
    }

    // Sub Type
    handleChangeSubType = (event) => {
        console.log("IncidentForm handleChangeSubType " +event.target.value);
        this.setState({subType: event.target.value})
    }

    getSelectSubType() {
        let select;
        if (this.state.incidentType) {
            const list = this.getIncidentStore().labelIncidentSubTypeList[this.state.incidentType];
            select = <IncidentSelect
                title="Incident Sub Type"
                listValueLabel={list}
                change={this.handleChangeSubType}
            />
        }
        return select;
    }

    // Vessel Type
    handleChangeVesselType = (event) => {
        console.log("IncidentForm handleChangeVesselType " +event.target.value);
        this.setState({vesselType: event.target.value})
    }

    getSelectVessel() {
        let select;
        if (this.state.subType) {
            const list = this.getIncidentStore().labelVesselTypeList;
            select = <IncidentSelect
                title="Vessel Type"
                listValueLabel={list}
                change={this.handleChangeVesselType}
            />
        }
        return select;
    }

    // Role
    handleChangeRole = (event) => {
        console.log("IncidentForm handleChangeRole " +event.target.value);
        this.setState({role: event.target.value})
    }

    getSelectRole() {
        let select;
        if (this.state.vesselType) {
            const list = this.getIncidentStore().labelRoleList;
            select = <IncidentSelect
                title="Role Type"
                listValueLabel={list}
                change={this.handleChangeRole}
            />
        }
        return select;
    }

    // Latitude
    handleChangeLatitude = (event) => {
        console.log("IncidentForm handleChangeLatitude " +event.target.value);
        this.setState({
            latitude: event.target.value,
            isValidLatitude:isFinite(event.target.value) && Math.abs(event.target.value) <= 90});
    }

    getLatitudineInput() {

        return  <Tooltip title={"[Optional] Use this field to override the CorrelationId."} >
            <TextField
                name="latitudeId"
                label="Latitude"
                fullWidth={true}
                color="primary"
                variant="outlined"
                value={this.state.latitude}
                onChange={this.handleChangeLatitude}
                error={!this.state.isValidLatitude}
            />
        </Tooltip>
    }

    // Longitude
    handleChangeLongitude = (event) => {
        console.log("IncidentForm handleChangeLongitude " +event.target.value);
        this.setState({
            longitude:event.target.value,
            isValidLongitude:isFinite(event.target.value) && Math.abs(event.target.value) <= 180
        })
    }

    getLongitudeInput() {
        return  <Tooltip title={"[Optional] Use this field to override the CorrelationId."} >
            <TextField
                name="longitudeId"
                label="Longitude"
                fullWidth={true}
                color="primary"
                variant="outlined"
                value={this.state.longitude}
                onChange={this.handleChangeLongitude}
                error={!this.state.isValidLongitude}
            />
        </Tooltip>
    }

    // imoNumber
    handleChangeImonumber = (event) => {
        console.log("IncidentForm handleChangeImonumber " +event.target.value);
        this.setState({imoNumber: event.target.value});
    }

    getImonumberInput() {
        return  <Tooltip title={"[Optional] Use this field to override the CorrelationId."} >
            <TextField
                name="imonumberId"
                label="Imo number"
                fullWidth={true}
                color="primary"
                variant="outlined"
                value={this.state.imoNumber}
                onChange={this.handleChangeImonumber}
            />
        </Tooltip>
    }

    // imoNumber
    handleChangeMmsi = (event) => {
        console.log("IncidentForm handleChangeMmsi " +event.target.value);
        this.setState({mmsi: event.target.value});
    }

    getMmsiInput() {
        return  <Tooltip title={"[Optional] Use this field to override the CorrelationId."} >
            <TextField
                name="mmsiId"
                label="mmsi"
                fullWidth={true}
                color="primary"
                variant="outlined"
                value={this.state.mmsi}
                onChange={this.handleChangeMmsi}
            />
        </Tooltip>
    }


    // Submit button
    handleSubmit = () => {
        console.log("IncidentForm handleSubmit ");
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



    render() {

        const {classes} = this.props;

        console.log("Render IncidentForm");

       return (
            <Box p="8px" mt="20px" mx="20px" bgcolor="#eeeeee">
                <Paper  className={classes.root} >
                    <Grid container alignItems="flex-start" spacing={3}>

                        <Grid item xs={3}>
                            {this.getSelectType()}
                        </Grid>

                        <Grid item xs={3}>
                            {this.getSelectSubType()}
                        </Grid>

                        <Grid item xs={3}>
                            {this.getSelectVessel()}
                        </Grid>

                        <Grid item xs={3}>
                            {this.getSelectRole()}
                        </Grid>

                        <Grid item xs={3}>
                            {this.getLatitudineInput()}
                        </Grid>

                        <Grid item xs={3}>
                            {this.getLongitudeInput()}
                        </Grid>

                        <Grid item xs={3}>
                            {this.getImonumberInput()}
                        </Grid>

                        <Grid item xs={3}>
                            {this.getMmsiInput()}
                        </Grid>

                        <Grid item xs={12}>
                            {this.getSubmitButton(classes)}
                        </Grid>
                        <Grid item xs={12}>
                        <FormControl>
                            <InputLabel htmlFor="my-input">Email address</InputLabel>
                            <Input id="my-input" aria-describedby="my-helper-text" />
                            <FormHelperText id="my-helper-text">We'll never share your email.</FormHelperText>
                        </FormControl>
                        </Grid>
                    </Grid>
                </Paper>
            </Box>
        )
    }
}

export default withStyles(styles)(IncidentForm);

