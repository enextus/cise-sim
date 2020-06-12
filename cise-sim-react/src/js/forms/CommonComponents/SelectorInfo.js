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
    const [currentList, setCurrList] = React.useState('');

    const {classes} = props;

    const handleChange = (event) => {
        setMyValue(event.target.value);
        setCurrList(props.listValueLabel[0]);
        props.change(event);
    }

    return (
        <FormControl className={classes.formControl}>
            <InputLabel id={"selectorinfo_" + props.title}>{props.title}</InputLabel>
            <Select
                labelId={"selectorinfo_" + props.title}
                id={"selector_" + props.title}
                onChange={handleChange}
                value={currentList === props.listValueLabel[0] ? myValue:'empty'}
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

