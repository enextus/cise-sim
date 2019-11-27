import React, {Component} from "react";
import {AppBar, Toolbar, Typography} from "@material-ui/core";
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
    fontWeight: 'bold',
    borderBottom: "2px solid #f7931e",
    // borderBottom: "2px solid " + theme.palette.secondary.main,
    paddingBottom: "3px",
    textAlign: "right",
  },
  protocol: {
    color: "#f7931e",
    fontWeight: "bold",
    fontSize: "10pt",
    textAlign: "right",
  },
  menuButton: {
    marginRight: theme.spacing(2),
    fontsize: 32,
  },
  title: {
    fontWeight: "bold",
    flexGrow: 1,
  },
  subAppNav: {
    backgroundColor: "#6da0be",
    paddingBottom: "8px",
    paddingTop: "5px",
    paddingRight: "24px",
  },
  nodeAddr: {
    fontWeight: "600",
    fontSize: "10pt",
    textAlign: "right",
  },
  label: {
    color: "#9bd1f1",
    margin: "0 3px",
  },
  value: {
    color: "white",
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
        <AppBar position="fixed" className={classes.root}>
          <Toolbar>
            <DirectionsBoatIcon
                fontSize="large"
                edge="start"
                className={classes.menuButton} />

            <Typography variant="h4" className={classes.title}>
              CISE Sim
            </Typography>

            <div>
              <Typography className={classes.participantId}>
                {this.getServiceId()}
              </Typography>

              <Typography className={classes.nodeAddr}>
                <span className={classes.label}>Endpoint:</span>
                <span className={classes.value}>{this.getServiceMode()}</span>
                <span className={classes.label}>Endpoint URL:</span>
                <span className={classes.value}>{this.getEndpointUrl()}</span>
              </Typography>

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

  getEndpointUrl() {
    return this.props.store.serviceStore.serviceSelf.endpointUrl;
  }

}

NavBar.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(NavBar)