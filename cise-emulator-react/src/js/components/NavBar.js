import React, {Component} from "react";
import {AppBar, Button, ButtonGroup, Toolbar, Typography} from "@material-ui/core";
import {observer} from "mobx-react";
import DirectionsBoatIcon from '@material-ui/icons/DirectionsBoat';
import withStyles from "@material-ui/core/styles/withStyles";
import PropTypes from "prop-types";
import IconButton from "@material-ui/core/IconButton";

const styles = theme => ({
    root: {
        display: 'flex',
        justifyContent: 'center',
        flexWrap: 'wrap',
    },
    participantChip: {
        fontsize: '8px',
        textTransform: 'capitalize',
        backgroundColor: 'secondary',
        color: 'white',
        height: 23,
        padding: '0 3px'
    },
    participant: {
        fontsize: '11px',
        textTransform: 'capitalize',
        backgroundColor: 'primary',
        color: 'secondary',
        height: 23,
        padding: '0 3px'
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
                    <Typography variant="h6" className={classes.title}> CISE Sim (beta) </Typography>
                    <div>
                        <ButtonGroup  size="small" color="secondary" aria-label="small secondary button group">
                            <Button className={classes.participantChip} >{"ParticipantId"}</Button>
                            <Button className={classes.participant} >{this.getServiceId()}</Button>
                        </ButtonGroup>
                        <span > </span>

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
        return this.props.store.serviceStore.serviceSelf.serviceParticipantId;
    }

    isConnected() {
        return this.props.store.appStore.connected;
    }
}

NavBar.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(NavBar)