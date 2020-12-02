import {FormControlLabel} from '@material-ui/core';
import React from 'react';
import PropTypes from 'prop-types';
import {observer} from 'mobx-react';
import Tooltip from '@material-ui/core/Tooltip';
import Switch from '@material-ui/core/Switch';
import {fontSizeSmall} from "../../layouts/Font";
import Box from "@material-ui/core/Box";

@observer
export default class RequiresAckCheck extends React.Component {

    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange() {
        this.props.store.toggleRequiresAck();
    }

    render() {
        return (
            <Tooltip title={"[Optional] Require asynchronous acknowledgement from destination node"}>
                <FormControlLabel
                    control={
                        <Switch
                            id="asyncAcknowledge"
                            name="asyncAcknowledge"
                            onChange={this.handleChange}
                            checked={this.props.store.requiresAck}
                            value={this.props.store.requiresAck}
                            size={"small"}
                        />
                    }

                    label={<Box component="div" fontSize={fontSizeSmall}>Require Async Ack</Box>}
                />
            </Tooltip>
        )
    }
}
RequiresAckCheck.propTypes = {
    store: PropTypes.object.isRequired,
};
