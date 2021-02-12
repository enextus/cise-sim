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
import {observer} from 'mobx-react';
import {ExpansionPanel, ExpansionPanelDetails, Typography} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import XmlContent from '../../components/common/XmlContent';
import {fontSizeNormal, fontSizeSmall, xmlContentHeightSize} from "../../layouts/Font";
import ExpansionPanelSummary from "@material-ui/core/ExpansionPanelSummary";

const styles = (theme) => ({
  root: {
  //  padding: theme.spacing(1)
  },
  title: {
    fontSize:fontSizeSmall
  },
  icon: {
    marginRight: "5px",
    color: "#6da0b3",
  },
});


@observer
class PreviewMessage extends Component {
  constructor(props) {
    super(props);
  }

  handleUpdate() {

  }

  render() {
    const {classes} = this.props;

    return (
          <ExpansionPanel expanded={!this.isTemplateEmpty()} elevation={0} >

            <ExpansionPanelSummary
              onClick={this.handleUpdate}
              aria-controls="previewMessageContent"
              id="previewMessage"
              style={{margin:0, padding:0}}
            >

              <Typography className={classes.title}><strong>Message Preview</strong></Typography>

            </ExpansionPanelSummary>

            <ExpansionPanelDetails style={{margin:0, padding:0,fontSize:fontSizeNormal, overflow:"auto"}}>
              <XmlContent size={xmlContentHeightSize}>
                {this.templateStore().template.errorCode === undefined ?
                  this.templateStore().template.content :
                    '<ErrorDetail>' + this.templateStore().template.errorMessage + '</ErrorDetail>'
                }
              </XmlContent>
            </ExpansionPanelDetails>
          </ExpansionPanel>
    )
  }

  isTemplateEmpty() {
    return this.templateStore().template.content === "";
  }

  templateStore() {
    return this.props.store.templateStore;
  }
}

PreviewMessage.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(PreviewMessage);
