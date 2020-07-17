import React from 'react';
import {observer} from 'mobx-react';
import {TextField} from '@material-ui/core';
import Tooltip from '@material-ui/core/Tooltip';
import PropTypes from 'prop-types';
import {fontSizeSmall} from "../../layouts/Font";
import Box from "@material-ui/core/Box";

@observer
export default class CorrelationIdField extends React.Component {

    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.props.store.correlationId = event.target.value;
    }

    render() {
        return (
            <Tooltip title={"[Optional] Use this field to override the CorrelationId."} >
                <TextField
                    name="correlationId"
                    fullWidth={true}
                    color="primary"
                    variant="outlined"
                    value={this.props.store.correlationId}
                    onChange={this.handleChange}
                    inputProps={{
                        style: {fontSize: fontSizeSmall}
                    }}
                    size={"small"}
                    label={<Box component="div" fontSize={fontSizeSmall}>Correlation Id</Box>}
                />
            </Tooltip>
        )
    }
}

CorrelationIdField.propTypes = {
    store: PropTypes.object.isRequired,
};
