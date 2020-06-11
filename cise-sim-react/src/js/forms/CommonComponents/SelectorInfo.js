import {FormControl, InputLabel, Select, withStyles} from '@material-ui/core';
import MenuItem from '@material-ui/core/MenuItem';
import React from 'react';

const styles = theme => ({
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
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

    const {classes} = props;

    const handleChange = (event) => {
        setMyValue(event.target.value);
        props.change(event);
    }

    return (
        <FormControl className={classes.formControl}>
            <InputLabel>{props.title}</InputLabel>
            <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                onChange={handleChange}
                value={myValue}
            >
                <MenuItem selected={true} value="empty">
                    <em>Please choice</em>
                </MenuItem>
                {props.listValueLabel.map((item, idx) => <MenuItem key={idx} value={item.value}>{item.label}</MenuItem>)}

            </Select>
        </FormControl>
    )
}

export default withStyles(styles)(selectorInfo);

