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

import {Button, withStyles} from '@material-ui/core';
import SendRoundedIcon from '@material-ui/icons/SendRounded';
import React from 'react';
import PropTypes from 'prop-types';
import {observer} from 'mobx-react';
import {withSnackbar} from 'notistack';

const styles = theme => ({
    button: {
        margin: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },
});

@observer
class SendButton extends React.Component {

    isDisabled() {
        return (!this.props.store.templateStore.isTemplateSelected
        || this.props.store.templateStore.template.errorCode !== undefined);
    }

    async send() {
        const response = await this.props.store.messageStore.send(
            this.props.store.templateStore.selected,
            this.props.store.templateStore.messageId, 
            this.props.store.templateStore.correlationId, 
            this.props.store.templateStore.requiresAck
        );
        if(response.errorCode){
            this.props.enqueueSnackbar(response.errorMessage, {
                variant: 'error',
                persist: true,
                action: (key) => (
                    <Button onClick={() => { this.props.closeSnackbar(key) }}>
                        {'Dismiss'}
                    </Button>
                ),
            })
        } else {
            this.props.enqueueSnackbar('New message has been sent.', {variant: 'success',});
            this.props.store.templateStore.createNewMessageId();
          //  this.props.store.templateStore.resetPreview();
        }

    }

    render() {
        const {classes} = this.props;

        return (
            <Button
                id="send"
                color="primary"
                variant="contained"
                className={classes.button}
                onClick={() => this.send()}
                disabled={this.isDisabled()}
                size={"small"}
            >
                Send
                <SendRoundedIcon className={classes.rightIcon}/>
            </Button>
        )
    }
}

SendButton.propTypes = {
    store: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(withSnackbar(SendButton))