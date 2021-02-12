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

import {FormControl, InputLabel, Select, withStyles} from '@material-ui/core';
import MenuItem from '@material-ui/core/MenuItem';
import React from 'react';
import PropTypes from 'prop-types';
import {observer} from 'mobx-react';
import Tooltip from '@material-ui/core/Tooltip';

import {fontSizeSmall} from "../../layouts/Font";

const styles = () => ({
    formControlInicial: {
        minWidth: 120,
        color:'secondary'
    },
    formControl: {
        minWidth: 120,
        color:'lightgray'
    },

    menuItem : {
        fontSize: fontSizeSmall,
    }
});

@observer
class TemplateSelect extends React.Component {


    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    async preview() {
        const response = await this.props.store.preview();
    }

    handleChange(event) {
        this.props.store.selected = event.target.value;
        if (event.target.value === 'empty') {
            this.props.store.resetPreview();
        }
        else {
            this.preview();
        }
    }

    componentDidMount() {
        this.props.store.loadTemplateList();
    }

    componentWillUnmount() {
        this.props.store.selected = "empty";
    }

    isSelected() {
        return !(this.props.store.selected === null)
    }

    render() {
        const {classes} = this.props;
        const formControlState= ((this.isSelected())?classes.formControl:classes.formControlInicial)
        return (
            <FormControl className={formControlState} fullWidth={true} required={true}>
                <Tooltip title={"[Required] Select the template from the list"}>
                    <InputLabel htmlFor="templateSelect">Message Template</InputLabel>
                </Tooltip>
                <Select
                    label="Message Template"
                    value={this.props.store.selected}
                    onChange={this.handleChange}
                    inputProps={{
			            name: 'templateSelect',
                        id: 'templateSelect',
                    }}
                    style={{fontSize:fontSizeSmall}}
                >
                    <MenuItem selected={true} value="empty" className={classes.menuItem}>
                        <em>Select Template</em>
                    </MenuItem>
                    {this.getMessageTemplateItems(classes)}
                </Select>
            </FormControl>
        )
    }

    getMessageTemplateItems(classes) {
        return this.props.store.templateOptions.map((item, idx) => (
            <MenuItem key={idx} value={item.value} className={classes.menuItem}>{item.label}</MenuItem>)
        )
    }
}

TemplateSelect.propTypes = {
    classes: PropTypes.object.isRequired,
    store: PropTypes.object.isRequired,
};

export default withStyles(styles)(TemplateSelect)
