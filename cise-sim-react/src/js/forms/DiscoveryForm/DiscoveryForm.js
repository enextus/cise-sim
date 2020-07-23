import React, {Component} from "react";
import {withStyles} from "@material-ui/core/styles";
import TableContainer from "@material-ui/core/TableContainer";
import {Button, Paper} from "@material-ui/core";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import SelectInfo from "../CommonComponents/SelectorInfo";
import CloseRoundedIcon from "@material-ui/icons/CloseRounded";
import Typography from "@material-ui/core/Typography";
import IconButton from "@material-ui/core/IconButton";
import TrackChangesRoundedIcon from "@material-ui/icons/TrackChangesRounded";
import {withSnackbar} from "notistack";


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

class DiscoveryForm extends Component {

    constructor(props) {
        super(props);
    }

    getStore() {
        return this.props.store.discoveryStore;
    };

    // --------- Country ---------
    handleChangeCountry = (event) => {
        this.getStore().getDiscoveryInputInfo().country = event.target.value;
   }

    getSelectCountry() {
        const list = this.getStore().labelCountryList;
        return <SelectInfo
            title="Country"
            listValueLabel={list}
            change={this.handleChangeCountry}
        />
    }

    // --------- SeaBasin ---------
    handleChangeSeaBasin = (event) => {
        this.getStore().getDiscoveryInputInfo().seaBasin = event.target.value;
    }

    getSelectSeaBasin() {
        const list = this.getStore().labelSeaBasinList;
        return <SelectInfo
            title="Sea Basin"
            listValueLabel={list}
            change={this.handleChangeSeaBasin}
        />
    }

    // --------- ServiceType ---------
    handleChangeServiceType = (event) => {
        this.getStore().getDiscoveryInputInfo().serviceType = event.target.value;
    }

    getSelectServiceType() {
        const list = this.getStore().labelServiceTypeList;
        return <SelectInfo
            title="Service Type"
            listValueLabel={list}
            change={this.handleChangeServiceType}
        />
    }


    // --------- ServiceOperation ---------
    handleChangeServiceOperation = (event) => {
        const newValue = event.target.value === "empty" ? undefined : event.target.value;
        this.getStore().getDiscoveryInputInfo().serviceOperation = newValue;
    }

    getSelectServiceOperation() {
        const list = this.getStore().labelServiceOperationList;
        return <SelectInfo
            title="Service Operation"
            listValueLabel={list}
            change={this.handleChangeServiceOperation}
        />
    }


    // --------- ServiceRole ---------
    handleChangeServiceRole = (event) => {
        this.getStore().getDiscoveryInputInfo().serviceRole = event.target.value;
    }

    getSelectServiceRole() {
        const list = this.getStore().labelServiceRoleList;
        return <SelectInfo
            title="Service Role"
            listValueLabel={list}
            change={this.handleChangeServiceRole}
        />
    }


    // --------- Submit button ---------
   /* handleSubmit = () => {
        this.getStore().sendDiscoveryMessage();
    }
*/
    async handleSubmit() {

        const response = await this.getStore().sendDiscoveryMessage(this.props.sender);

        // Manage snackbar for message delivery notification
        if(response.errorCode){
            this.props.enqueueSnackbar(response.errorMessage, {
                variant: 'error',
                persist: true,
                action: (key) => (
                    <Button onClick={() => { this.props.closeSnackbar(key) }}>
                        {'Dismiss'}
                    </Button>
                ),
            })
        } else {
            this.props.enqueueSnackbar('Discovery message has been sent.', {variant: 'success',});
           }

    }

    getButtonSubmit(classes) {

        return (
            <Button
                id="clearMsg"
                color="primary"
                variant="contained"
                className={classes.button}
                onClick={() => this.handleSubmit()}
                type="submit"
                size="small"
            >
                Discover services
                <TrackChangesRoundedIcon className={classes.rightIcon}/>

            </Button>
        )
    }

    // --------- End button ---------
    handleEnd = () => {
        this.getStore().cleanResources();
        this.props.onclose();
    }


    render() {

        const {classes} = this.props;

        return (
            <TableContainer component={Paper} >
                <Table size="small" aria-label="a dense table">
                    <TableBody>

                        <TableRow>
                            <TableCell>
                                <Typography variant="h6" component="h1" align={"left"} style={{fontWeight: "bold",}}>
                                    Discovery profile
                                </Typography>
                            </TableCell>
                            <TableCell/>
                            <TableCell align={"right"} style={{paddingRight:0}}>
                                <IconButton
                                    data-dismiss="create-message"
                                    onClick={this.handleEnd}
                                    fontSize="inherit">
                                    <CloseRoundedIcon/></IconButton>
                            </TableCell>
                        </TableRow>

                        <TableRow>
                            <TableCell>
                                {this.getSelectCountry()}
                            </TableCell>

                            <TableCell>
                                {this.getSelectSeaBasin()}
                            </TableCell>
                            <TableCell/>
                        </TableRow>
                        <TableRow>
                            <TableCell>
                                {this.getSelectServiceType()}
                            </TableCell>

                            <TableCell>
                                {this.getSelectServiceOperation()}
                            </TableCell>

                            <TableCell>
                                {this.getSelectServiceRole()}
                            </TableCell>

                        </TableRow>

                        <TableRow>


                            <TableCell/>
                            <TableCell/>

                            <TableCell>
                                {this.getButtonSubmit(classes)}
                            </TableCell>
                        </TableRow>


                    </TableBody>
                </Table>
            </TableContainer>
        )
    }
}

export default withStyles(styles)(withSnackbar(DiscoveryForm));