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
import {Box, Grid} from '@material-ui/core';
import {observer} from 'mobx-react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import MessageIdField from './MessageIdField';
import CorrelationIdField from './CorrelationIdField';
import TemplateSelect from './TemplateSelect';
import RequiresAckCheck from './RequiresAckCheck';
import SendButton from './SendButton';
import PreviewMessage from "./PreviewMessage";
import SendFormHeader from "./SendFormHeader";
import {boxSizeHeight, boxSizeWidth} from "../../layouts/Font";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 16,
        margin: '16px auto',
        maxWidth: boxSizeWidth,
        maxHeight: boxSizeHeight
    },
});

@observer
class CreateMessageForm extends Component {

    constructor(props) {
        super(props);
    }

    getTemplateStore() {
        return this.props.store.templateStore
    };

    getAllStores() {
        return this.props.store
    };


    render() {
        const {classes} = this.props;

        return (
                <Box bgcolor="white" className={classes.root} id='CreateMessageForm'>

                    <Grid container alignItems="flex-start" spacing={3} >

                        <Grid item xs={12} style={{padding:0}}>
                            <SendFormHeader onclose={this.props.onclose}/>
                        </Grid>

                        <Grid item xs={12}>
                            <TemplateSelect store={this.getTemplateStore()}/>
                        </Grid>

                        <Grid item xs={6}>
                            <MessageIdField store={this.getTemplateStore()}/>
                        </Grid>

                        <Grid item xs={6}>
                            <CorrelationIdField store={this.getTemplateStore()}/>
                        </Grid>

                        <Grid item xs={6} style={{paddingBottom:0}}>
                            <RequiresAckCheck store={this.getTemplateStore()}/>
                        </Grid>

                        <Grid item xs={6} style={{paddingBottom:0}}>
                            <Grid container  alignItems="flex-end" justify="flex-end" direction="row">
                                <SendButton store={this.getAllStores()}/>
                            </Grid>
                        </Grid>

                        <Grid item xs={12} style={{paddingTop:0}}>
                            <PreviewMessage store={this. getAllStores()}/>
                        </Grid>

                    </Grid>
                </Box>
        )
    }
}

CreateMessageForm.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(CreateMessageForm);

