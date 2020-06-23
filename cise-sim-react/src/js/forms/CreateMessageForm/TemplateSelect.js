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
