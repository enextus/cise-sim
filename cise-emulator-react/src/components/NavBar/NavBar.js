import React, {Component} from "react";
import {AppBar, Button, Toolbar, Typography} from "@material-ui/core";
import {observer} from "mobx-react";
import DirectionsBoatIcon from '@material-ui/icons/DirectionsBoat';
import Chip from "@material-ui/core/Chip";
import Avatar from "@material-ui/core/Avatar";
import withStyles from "@material-ui/core/styles/withStyles";
import PropTypes from "prop-types";

const styles = theme => ({
    root: {
        display: 'flex',
        justifyContent: 'center',
        flexWrap: 'wrap',
    },
    chip: {
        marginRight: theme.spacing(2),
    },
    logoIcon: {
        marginRight: theme.spacing(1),
    }
});

@observer
class NavBar extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        const {classes} = this.props;
        return (
            <AppBar className={classes.root}>
                <Toolbar>
                    <Typography variant="h5" type="title" color="inherit" style={{flex: 1, fontWeight: "bold"}}>
                        <DirectionsBoatIcon className={classes.logoIcon}/>CISE Emu
                    </Typography>
                    <div>
                        <Chip
                            avatar={<Avatar>ID</Avatar>}
                            label={this.getServiceId()}
                            className={classes.chip}
                            color="secondary"
                        />
                        <Button
                            variant="contained"
                            disabled={!this.isConnected()}
                            color="secondary"> REST
                        </Button>
                    </div>
                </Toolbar>
            </AppBar>
        );
    }

    getServiceId() {
        return this.props.store.appStore.memberId;
    }

    isConnected() {
        return this.props.store.appStore.connected;
    }
}

NavBar.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(NavBar)