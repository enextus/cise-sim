import React, {Component} from "react";
import {withStyles} from "@material-ui/core/styles";
import TableContainer from "@material-ui/core/TableContainer";
import {Button, Paper} from "@material-ui/core";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import SelectInfo from "../CommonComponents/SelectorInfo";
import SendRoundedIcon from "@material-ui/icons/SendRounded";
import CloseRoundedIcon from "@material-ui/icons/CloseRounded";


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

    // --------- Submit button ---------
    handleSubmit = () => {
        this.getStore().sendDiscoveryMessage();
    }

    getButtonSubmit(classes) {

        return (
            <Button
                id="clearMsg"
                color="primary"
                variant="contained"
                className={classes.button}
                onClick={this.handleSubmit}
                type="submit"
                size="small"
            >
                Send Message
                <SendRoundedIcon className={classes.rightIcon}/>

            </Button>
        )
    }

    // --------- End button ---------
    handleEnd = () => {
        this.getStore().cleanResources();
        this.props.onclose();
    }

    getButtonEnd(classes) {

        return (
            <Button
                id="clearMsg"
                color="primary"
                variant="contained"
                className={classes.button}
                onClick={this.handleEnd}
                type="submit"
                size="small"
            >
                Close
                <CloseRoundedIcon className={classes.rightIcon}/>

            </Button>
        )
    }


    render() {

        const {classes} = this.props;

        return (
            <TableContainer component={Paper} >
                <Table size="small" aria-label="a dense table">
                    <TableBody>

                        <TableRow>
                            <TableCell>
                                {this.getSelectCountry()}
                            </TableCell>

                            <TableCell>
                                {this.getSelectSeaBasin()}
                            </TableCell>

                            <TableCell>
                                {this.getSelectServiceType()}
                            </TableCell>
                        </TableRow>

                        <TableRow>
                            <TableCell>
                                {this.getButtonEnd(classes)}
                            </TableCell>

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

export default withStyles(styles)(DiscoveryForm);