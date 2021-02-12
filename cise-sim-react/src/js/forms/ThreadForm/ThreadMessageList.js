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
import {Box} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import ThreadMsgInfo from './List/ThreadListMessageInfo';
import {observer} from "mobx-react";
import Typography from "@material-ui/core/Typography";
import {fontSizeNormal} from "../../layouts/Font";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 8,
        margin: '16px auto',
        maxWidth: 800,
        overflowY: 'scroll',
    },
    button: {
        align: "right"
    }

});


@observer
class ThreadMessageList extends Component {


    selectThread = (correlationId) =>  {
        const msgRcv = this.getMessageStore().historyMsgList;

        const result = msgRcv.filter(item => item.correlationId === correlationId);

        // Do the ordering by timestamp
        result.sort(function(a,b) {return b.dateTime-a.dateTime});

        this.getMessageStore().getThreadWithBody(result);
        this.getMessageStore().updateThreadIdSelected(correlationId);
    }

    buildThreadCards = (msgList) => {

        let group = [];
        let counter = [];
        let mostRecentTimestamp = [];
        let ackSuccess = [];
        let rcvAckSynch = [];

        let msg;
        for (msg of msgList) {
            if (group[msg.correlationId] === undefined) {
                group[msg.correlationId] = {...msg};
                counter[msg.correlationId] = 1;
                mostRecentTimestamp[msg.correlationId] = msg.dateTime;
                ackSuccess[msg.correlationId] = Boolean('true');
                rcvAckSynch[msg.correlationId] = Boolean('false');
            } else {
                counter[msg.correlationId]++;
                if (mostRecentTimestamp[msg.correlationId] <  msg.dateTime) {
                    mostRecentTimestamp[msg.correlationId] =  msg.dateTime;
                }
                if (msg.messageType !== 'Sync Ack' && (group[msg.correlationId].messageType === 'Sync Ack' || group[msg.correlationId].dateTime >= msg.dateTime)) {
                    group[msg.correlationId]  =  {...msg};

                }
            }
            if (msg.messageType === 'Sync Ack') {
                ackSuccess[msg.correlationId] = ackSuccess[msg.correlationId] && msg.ackResult.includes('Success');
                rcvAckSynch[msg.correlationId] = Boolean('true');
            }
        }

        let result = []
        let item;
        for (item in group) {
            group[item].numTh = counter[item];
            group[item].mostRecentTimestamp = mostRecentTimestamp[item];
            group[item].ackSuccess =  rcvAckSynch[msg.correlationId] ? ackSuccess[item]: Boolean('false');
            result.push(group[item]);
        }

        // Do the ordering by timestamp
        result.sort(function(a,b) {return b.mostRecentTimestamp-a.mostRecentTimestamp});


        return result;
    }

    renderList = (threadCards) => {
        return ( <Box hidden={threadCards.length === 0}  >

            {threadCards.map((msg) =>
                <ThreadMsgInfo
                    key={msg.id}
                    msgInfo={msg}
                    selectThread={() => this.selectThread(msg.correlationId)}
                    selected={this.getMessageStore().threadIdSelected === msg.correlationId}
                />)}

        </Box>)
    }

    renderEmptyList = () => {

        return ( <Box>

                <Typography style={{paddingTop:60, fontSize:fontSizeNormal}} variant="h4" component="h1" align={"center"}>
                    Empty Thread List
                </Typography>
            </Box>

        )
    }
    render() {

        const {classes} = this.props;

        // Set the max number of messages to be shown
        const maxShow = this.getServiceStore().serviceSelf.messageHistoryMaxLength;
        this.getMessageStore().setThreadMaxCapacity(maxShow);

        // Manage the message and create the thread groups
        const msgRcv = this.getMessageStore().historyMsgList;
        let threadCards = this.buildThreadCards(msgRcv);

        const msgFilter = this.getMessageStore().threadFilter;
        if (msgFilter) {
            if(msgFilter === 'fail') {
                threadCards = threadCards.filter((msg) => !msg.ackSuccess);
            }
        }

        return threadCards.length ?  this.renderList(threadCards) : this.renderEmptyList();

    }

    getMessageStore() {
        return this.props.store.messageStore;
    }

    getServiceStore() {
        return this.props.store.serviceStore;
    }
}


export default withStyles(styles)(ThreadMessageList);