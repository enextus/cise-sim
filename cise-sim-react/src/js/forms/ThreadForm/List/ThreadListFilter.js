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

import React, {Component} from "react";
import {Grid, withStyles} from "@material-ui/core";
import {withSnackbar} from "notistack";
import ButtonGroup from "@material-ui/core/ButtonGroup";
import Button from "@material-ui/core/Button";
import {buttonSizeSmall, fontSizeUltraSmall} from "../../../layouts/Font";


const styles = theme => ({
    button: {
        margin: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },
    palette: {
        primary: "pink",
        secondary: "black",
    },
});


class ThreadListFilter extends Component {

    state = {
        variantAll:'contained',
        variantFail:''
    }

    constructor(props) {
        super(props);
    }

    handleAllMessages = () => {
        this.getMessageStore().updateThreadFilter(null);
        this.setState({variantAll:'contained', variantFail:''});
    }

    handleFailedMessages = () => {
        this.getMessageStore().updateThreadFilter('fail');
        this.setState({variantAll:'', variantFail:'contained'});
    }

    render() {
        const {classes} = this.props;
        return (
            <Grid container   alignItems="flex-start" justify="flex-start" direction="row">

                <Grid item xs={12} >
                    <Grid container   alignItems="flex-start" justify="flex-start" direction="row">
                        <ButtonGroup color="primary" aria-label="outlined primary button group" style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeUltraSmall}}>
                            <Button variant={this.state.variantAll} onClick={this.handleAllMessages}  style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeUltraSmall}}>All messages</Button>
                            <Button variant={this.state.variantFail} onClick={this.handleFailedMessages}  style={{ maxHeight: buttonSizeSmall,fontSize:fontSizeUltraSmall}}>Errors</Button>
                        </ButtonGroup>
                    </Grid>
                </Grid>
            </Grid>
        )
    }

    getMessageStore() {
        return this.props.store.messageStore;
    }
}

export default withStyles(styles)(withSnackbar(ThreadListFilter))