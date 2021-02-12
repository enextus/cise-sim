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