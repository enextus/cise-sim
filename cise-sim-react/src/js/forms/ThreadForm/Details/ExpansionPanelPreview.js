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
import {withStyles} from "@material-ui/core/styles";
import {ExpansionPanel, ExpansionPanelDetails} from "@material-ui/core";
import XmlContent from "../../../components/common/XmlContent";
import {fontSizeNormal, xmlContentHeightSize} from "../../../layouts/Font";
import {CompactExpansionPanelSummary} from "../../../components/common/CompactExpansionPanelSummary";
import Typography from "@material-ui/core/Typography";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import AspectRatioRoundedIcon from '@material-ui/icons/AspectRatioRounded';

const styles = theme => ({
    root: {
        width: "100%"
    },
    heading: {
        fontSize: theme.typography.pxToRem(15),
        fontWeight: theme.typography.fontWeightRegular
    },
});


class ExpansionPanelPreview extends Component {

    state = {
        isPreview: true,
        currentBody: ''
    }

    body;
    preview;

    constructor(props) {
        super(props);
        this.body = props.body;
        this.preview = props.body.split('\n').slice(0, props.numLines-1).join('\n');
        this.state.isPreview = props.expanded === undefined || props.expanded !== true;
        this.state.currentBody = this.state.isPreview ? this.preview : this.body;
    }

    handleChange = (event, expanded) => {
        let newState = this.state.isPreview ?
                {isPreview:false, currentBody: this.body}:
                {isPreview:true,  currentBody: this.preview};

        this.setState(newState);
    }

    render() {
        const {classes} = this.props;


        return (
            <ExpansionPanel onChange={this.handleChange} elevation={0} id="ExpPannel" expanded style={{margin:0}} >

                <CompactExpansionPanelSummary id="ExpSummary" expandIcon={<AspectRatioRoundedIcon/>}>

                    <TableContainer>
                        <Table size="small" aria-label="a dense table">
                            <TableBody>
                                <TableRow >
                                    <TableCell component="th" scope="row" style={{ borderBottom: 0, padding: 0, textAlign:"right"}}>
                                        <Typography style={{textAlign:'right'}}>Expand</Typography>
                                    </TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </TableContainer>

                </CompactExpansionPanelSummary>

                <ExpansionPanelDetails id="ExpDetails" style={{fontSize:fontSizeNormal}}>
                    <XmlContent size={xmlContentHeightSize}>{this.state.currentBody}</XmlContent>
                </ExpansionPanelDetails>

            </ExpansionPanel>
        )
    }
}

export default withStyles(styles)(ExpansionPanelPreview);