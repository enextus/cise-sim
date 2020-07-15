import {TextField} from '@material-ui/core';
import Tooltip from '@material-ui/core/Tooltip';
import React from 'react';
import PropTypes from 'prop-types';
import {observer} from 'mobx-react';

import {fontSizeSmall} from "../../layouts/Font";
import Box from "@material-ui/core/Box";

@observer
export default class MessageIdField extends React.Component {

    constructor(props) {
        super(props);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.props.store.messageId = event.target.value;
    }

    render() {
        return (
            <Tooltip title={"[Required] Message identifier (UUID)"} >
            <TextField
                name="messageId"
                required={true}
                fullWidth={true}
                color="primary"
                variant="outlined"
                value={this.props.store.messageId}
                onChange={this.handleChange}
                inputProps={{
                    style: {fontSize: fontSizeSmall}
                }}
                size={"small"}
                label={<Box component="div" fontSize={fontSizeSmall}>Message Id</Box>}
            />
            </Tooltip>
        )
    }
}

MessageIdField.propTypes = {
    store: PropTypes.object.isRequired,
};
