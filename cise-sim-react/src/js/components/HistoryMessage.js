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
import {
    ExpansionPanel,
    ExpansionPanelDetails,
    ExpansionPanelSummary,
    Tab,
    Tabs,
    Typography,
    withStyles
} from '@material-ui/core';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import PropTypes from 'prop-types';
import TabPanel from './common/TabPanel';
import EmailIcon from '@material-ui/icons/Email';

import MessageTable from './messages/MessageInfoTable'


const styles = (theme) => ({
    root: {
        padding: theme.spacing(1),
    },
    title: {
        fontSize: "12pt",
    },
    icon: {
        marginRight: "5px",
        color: "#f7931e",
    },
    hide: {
        visibility: 'hidden',
    },
    table: {
        minWidth: 250,
        maxWidth: 650
    }
});

@observer
class HistoryMessage extends Component {

    constructor(props) {
        super(props);
        this.state = {tabValue: "one"};
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event, newValue) {
        this.setState({tabValue: newValue})
        console.log("newValue "+newValue);
    };

    render() {
        const {classes} = this.props;

        const msgRcv = this.getMessageStore().historyMsgList;
        const maxShow = this.getServiceStore().serviceSelf.messageHistoryMaxLength;
        this.getMessageStore().setThreadMaxCapacity(maxShow);

        return (
            <div className={classes.root}>
                <ExpansionPanel
                    expanded={this.getMessageStore().historyMsgList.length}>

                    <ExpansionPanelSummary
                        expandIcon={<ExpandMoreIcon/>}
                        aria-controls="receiveMessageContent"
                        id="HistoryMessage">
                        <EmailIcon className={classes.icon}/>
                        <Typography className={classes.title}>Message <b>History</b></Typography>
                    </ExpansionPanelSummary>

                    <ExpansionPanelDetails>
                        <Tabs
                            value={this.state.tabValue}
                            onChange={this.handleChange}
                            aria-label="simple-tabpanel"
                            indicatorColor="primary"
                            textColor="primary">

                            <Tab label="message 1"
                                 id='simple-tab-1'
                                 value="one"
                                 aria-controls='simple-tabpanel-1'>
                            </Tab>
                        </Tabs>

                        <TabPanel value={this.state.tabValue} index="one" >
                            <MessageTable msgRcv={msgRcv} messageStore={this.getMessageStore()}/>
                        </TabPanel>

                    </ExpansionPanelDetails>
                </ExpansionPanel>
            </div>
        )
    }

    getMessageStore() {
        return this.props.store.messageStore;
    }

   getServiceStore() {
        return this.props.store.serviceStore;
    }

}

HistoryMessage.propTypes = {
  store: PropTypes.object.isRequired,
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(HistoryMessage);