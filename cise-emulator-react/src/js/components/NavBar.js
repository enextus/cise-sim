import React, {Component} from "react";
import {AppBar, Button, Toolbar, Typography} from "@material-ui/core";
import {observer} from "mobx-react";
import DirectionsBoatIcon from '@material-ui/icons/DirectionsBoat';
import Chip from "@material-ui/core/Chip";
import Avatar from "@material-ui/core/Avatar";
import withStyles from "@material-ui/core/styles/withStyles";
import PropTypes from "prop-types";
import IconButton from "@material-ui/core/IconButton";

const styles = theme => ({
    root: {
        display: 'flex',
        justifyContent: 'center',
        flexWrap: 'wrap',
    },
    chip: {
        marginRight: theme.spacing(2),
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
        fontWeight: "bold",
    },
});

@observer
class NavBar extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        const {classes} = this.props;
        return (
            <AppBar position="static" className={classes.root}>
                <Toolbar>
                    <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
                        <DirectionsBoatIcon/>
                    </IconButton>
                    <Typography variant="h6" className={classes.title}> CISE Emu </Typography>
                    <div>
                        <Chip
                            avatar={<Avatar>ID</Avatar>}
                            label={this.getServiceId()}
                            className={classes.chip}
                            color="secondary"
                        />
                        <Button
                            variant="contained"
                            // disabled={!this.isConnected()}
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