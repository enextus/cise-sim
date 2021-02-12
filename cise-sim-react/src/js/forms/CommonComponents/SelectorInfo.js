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
import {fontSizeSmall} from "../../layouts/Font";

const styles = theme => ({
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },

    menuItem : {
        fontSize: fontSizeSmall,
    }
});

/**
 *
 *  title: Title of the selector
 *  listValueLabel: List of couple label and value
 *  change: notification function of the selected value like :
 *              handleChange = (event) => {
 *                  value = event.target.value;
 *              }
 *
 * @param props
 *
 * @returns {*}
 */
const selectorInfo = (props)  => {


    const [myValue, setMyValue] = React.useState('empty');
    const [currentList, setCurrList] = React.useState('');

    const {classes} = props;

    const handleChange = (event) => {
        setMyValue(event.target.value);
        setCurrList(props.listValueLabel[0]);
        event.target.value = event.target.value.localeCompare('empty') === 0 ? undefined : event.target.value;
        props.change(event);
    }

    return (
        <FormControl className={classes.formControl}>
            <InputLabel id={"selectorinfo_" + props.title} style={{minWidth:"max-content"}}>{props.title}</InputLabel>
            <Select
                labelId={"selectorinfo_" + props.title}
                id={"selector_" + props.title}
                onChange={handleChange}
                value={currentList === props.listValueLabel[0] ? myValue:'empty'}
                style={{fontSize:fontSizeSmall}}
            >
                <MenuItem selected={true} value="empty" className={classes.menuItem}>
                    <em>Select one</em>
                </MenuItem>
                {props.listValueLabel.map((item, idx) => <MenuItem key={idx} value={item.value} className={classes.menuItem}>{item.label}</MenuItem>)}

            </Select>
        </FormControl>
    )
}

export default withStyles(styles)(selectorInfo);

