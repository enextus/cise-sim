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