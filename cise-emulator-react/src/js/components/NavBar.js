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
    fontWeight: 'bold',
    borderBottom: "1px solid white",
  },
  protocol: {
    color: "#f7931e",
    fontWeight: "bold",
    fontSize: "9pt",
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
    color: "black",
    fontWeight: "600",
    fontSize: "10pt",
    textAlign: "right",
  },
  nodeAddrLabel: {
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
                className={classes.menuButton}/>
            <Typography variant="h4" className={classes.title}>CISE Sim</Typography>

            <div>
              <Typography
                  className={classes.participantId}>{this.getServiceId()}</Typography>
              <Typography
                  className={classes.protocol}>Protocol {this.getServiceMode()}</Typography>
            </div>
          </Toolbar>

          <div className={classes.subAppNav}>
            <Typography className={classes.nodeAddr}>
              <span className={classes.nodeAddrLabel}>node address: </span>
              {this.getServiceId()}
            </Typography>
          </div>

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