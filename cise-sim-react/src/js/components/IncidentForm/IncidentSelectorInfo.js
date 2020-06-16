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


// title
// listValueLabel
const incidentSelectorInfo = (props)  => {

    const [myValue, setMyValue] = React.useState('');

    const {classes} = props;


    const handleChange = (event) => {
        console.log("incidentSelectorInfo select " +event.target.value);
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


export default withStyles(styles)(incidentSelectorInfo)
