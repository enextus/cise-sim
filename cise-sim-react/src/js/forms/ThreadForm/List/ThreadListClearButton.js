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
import React, {Component} from 'react';
import {observer} from 'mobx-react';
import VisibilityOffRoundedIcon from '@material-ui/icons/VisibilityOffRounded';
import {buttonSizeSmall, fontSizeUltraSmall} from "../../../layouts/Font";

const styles = theme => ({
    button: {
        margin: theme.spacing(1),
        maxHeight: buttonSizeSmall,
        fontSize: fontSizeUltraSmall,
        padding:0
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },
    leftIcon: {
        marginRight: theme.spacing(1),
        width:"20%",
        marginLeft: theme.spacing(0)
    },
});

@observer
class MsgClearButton extends Component {

    constructor(props) {
        super(props);
    }

    isDisabled() {
        return !this.getMessageStore().historyMsgList.length > 0;
    }

    clear () {
        this.getMessageStore().clearHistory();
        this.getMessageStore().updateThreadWithBody([]);
    }

    render() {
        const {classes} = this.props;

        let disabled;
        let visibility;
        if (this.getMessageStore()){
            disabled =this.isDisabled();
            visibility = null;
        }
        else {
            disabled = null;
            visibility = 'hidden';
        }

        return (
            <Button
                id="clearMsg"
                color="secondary"
                variant="contained"
                className={classes.button}
                onClick={() => this.clear()}
                disabled={disabled}
                style={{visibility:visibility}}
            >
                <VisibilityOffRoundedIcon className={classes.leftIcon}/>
                Clear
            </Button>
        )
    }

    getMessageStore() {
        return this.props.messageStore;
    }
}

export default withStyles(styles)(MsgClearButton)