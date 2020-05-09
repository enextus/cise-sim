import {FormControl, InputLabel, Select, withStyles} from '@material-ui/core';
import MenuItem from '@material-ui/core/MenuItem';
import React from 'react';
import PropTypes from 'prop-types';
import {observer} from 'mobx-react';
import Tooltip from '@material-ui/core/Tooltip';

const styles = () => ({
    formControlInicial: {
        minWidth: 120,
        color:'secondary'
    },
    formControl: {
        minWidth: 120,
        color:'grey'
    }
});

@observer
class TemplateSelect extends React.Component {


    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.props.store.selected = event.target.value;
    }

    componentDidMount() {
        this.props.store.loadTemplateList();
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
                        id: 'templateSelect'
                    }}>
                    <MenuItem selected={true} value="empty">
                        <em>select template</em>
                    </MenuItem>
                    {this.getMessageTemplateItems()}
                </Select>
            </FormControl>
        )
    }

    getMessageTemplateItems() {
        return this.props.store.templateOptions.map((item, idx) => (
            <MenuItem key={idx} value={item.value}>{item.label}</MenuItem>)
        )
    }
}

TemplateSelect.propTypes = {
    classes: PropTypes.object.isRequired,
    store: PropTypes.object.isRequired,
};

export default withStyles(styles)(TemplateSelect)
