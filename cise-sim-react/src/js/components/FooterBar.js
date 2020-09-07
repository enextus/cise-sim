import React, {Component} from 'react';
import {AppBar, Toolbar, Typography} from '@material-ui/core';
import {observer} from 'mobx-react';
import DirectionsBoatIcon from '@material-ui/icons/DirectionsBoat';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import {grey} from "@material-ui/core/colors";
import TableCell from "@material-ui/core/TableCell";

const styles = theme => ({
  root: {
    display: 'flex',
    justifyContent: 'center',
    flexWrap: 'wrap',
    top: 'auto',
    bottom: 0,
    backgroundColor: "#dbdada",
    height: "40px",
    color: "#737373",
  },

  title: {
    fontWeight: "bold",
    flexGrow: 1,
  },
  protocol1: {
    fontSize: "10pt",
    textAlign: "right",
    flexGrow: 1,
    flexDirection: "row-reverse",

  },
  protocol: {
    fontWeight: "bold",
    fontSize: "10pt",
    textAlign: "right",
    flexGrow: 1,
    flexDirection: "row-reverse",

  },
});

class FooterBar extends Component {

  constructor(props) {
    super(props);
  }

  render() {
    const {classes} = this.props;
    return (
        <React.Fragment>
        <AppBar position="fixed" className={classes.root}>
          <Toolbar>

            <Typography variant="subtitle1"  className={classes.protocol}>
              Developed by:
              <span> &nbsp; </span>
            </Typography>


            <a  href="https://ec.europa.eu/jrc/en" style={{textDecoration:"none"}}>
              <Typography variant="subtitle1"  className={classes.protocol}>
                European Comission, Joint Research Center
              </Typography>
            </a>

          </Toolbar>
        </AppBar>
        </React.Fragment>
    )
  }
}

FooterBar.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(FooterBar)