import React, {Component} from "react";
import {AppBar, Button, Toolbar, Typography} from "@material-ui/core";
import {observer} from "mobx-react";
import DirectionsBoatIcon from '@material-ui/icons/DirectionsBoat';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';

const styles = theme => ({
  root: {
    display: 'flex',
    justifyContent: 'center',
    flexWrap: 'wrap',
  },
  participantId: {
    fontSize: '12pt',
    fontWeight: 'bold'
  },
  participant: {
    fontSize: '11px',
    textTransform: 'capitalize',
    backgroundColor: 'secondary',
    color: 'white',
    height: 25,
    padding: '0 3px'
  },
  menuButton: {
    marginRight: theme.spacing(2),
    fontsize: 32,
  },
  title: {
    fontWeight: "bold",
    flexGrow: 1,
  },
  grow: {},
});

@observer
class NavBar extends Component {

  constructor(props) {
    super(props);
  }

  render() {
    const {classes} = this.props;
    return (
        <AppBar position="fixed" className={classes.root}>
          <Toolbar>
            <DirectionsBoatIcon
                fontSize="large"
                edge="start"
                className={classes.menuButton}/>
            <Typography variant="h4" className={classes.title}>
              CISE Sim
            </Typography>

            <div>
              <Typography className={classes.participantId}>{this.getServiceId()}</Typography>
              <Button
                  variant="contained"
                  color="secondary"> {this.getServiceMode()}
              </Button>
            </div>
          </Toolbar>
        </AppBar>
    );
  }

  getServiceId() {
    return this.props.store.serviceStore.serviceSelf.serviceParticipantId;
  }

  getServiceMode() {
    return this.props.store.serviceStore.serviceSelf.serviceTransportMode;
  }
}

NavBar.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(NavBar)