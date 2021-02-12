/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

import React, {Component} from 'react';
import {AppBar, Toolbar, Typography} from '@material-ui/core';
import {observer} from 'mobx-react';
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
  appVersion: {
    margin: "0 5px",
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
              <Typography variant="overline" display="inline" className={classes.appVersion} gutterBottom>
                (<b>{this.getAppVersion()})</b>
              </Typography>

            </Typography>

            <div>
              <Typography className={classes.participantId}>
                {this.getParticipantId()}
              </Typography>

              <Typography className={classes.nodeAddr}>
                <span className={classes.label}>Protocol:</span>
                <span className={classes.value}>{this.getServiceMode()}</span>
                <span className={classes.label}>Destination URL:</span>
                <span className={classes.value}>{this.getEndpointUrl()}</span>
              </Typography>

            </div>
          </Toolbar>
        </AppBar>
    )
  }

  getParticipantId() {
    return this.props.store.serviceStore.serviceSelf.serviceParticipantId;
  }

  getServiceMode() {
    return this.props.store.serviceStore.serviceSelf.serviceTransportMode;
  }

  getEndpointUrl() {
    return this.props.store.serviceStore.serviceSelf.endpointUrl;
  }

  getAppVersion() {
    return this.props.store.serviceStore.serviceSelf.appVersion;
  }

}

NavBar.propTypes = {
  classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(NavBar)