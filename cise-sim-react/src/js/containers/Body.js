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
import {Grid} from '@material-ui/core';
import Paper from "@material-ui/core/Paper";
import ButtonsPanel from "./ButtonsPanel";
import ThreadMessageList from "../forms/ThreadForm/ThreadMessageList";
import ThreadMessageDetails from "../forms/ThreadForm/ThreadMessageDetails";
import {withStyles} from "@material-ui/core/styles";
import ThreadListHeader from "../forms/ThreadForm/List/ThreadListHeader";
import ThreadDetailHeader from "../forms/ThreadForm/Details/ThreadDetailHeader";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 16,
        margin: '16px auto',
        maxWidth: 800
    },
});

class Body extends Component {

    render() {
        const {classes} = this.props;

        return (
                <Grid container spacing={2} style={{backgroundColor:"white"}}>

                    <Grid item xs={12}>
                        <Paper elevation={3}>
                            <h3>Here nav bar</h3>
                        </Paper>
                    </Grid>

                    <Grid item xs={12}>
                        <ButtonsPanel store={this.props.store} />
                    </Grid>

                    <Grid item xs={3} style={{paddingRight:0}}>
                        <ThreadListHeader store={this.props.store}/>
                        <ThreadMessageList  store={this.props.store} />
                    </Grid>

                    <Grid item xs={9}>
                        <ThreadDetailHeader store={this.props.store} />
                        <ThreadMessageDetails  store={this.props.store} />
                    </Grid>

                </Grid>
        )
    }
}

export default withStyles(styles)(Body)