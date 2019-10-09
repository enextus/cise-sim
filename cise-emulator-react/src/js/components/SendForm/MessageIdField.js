import {TextField} from "@material-ui/core";
import React from "react";
import PropTypes from "prop-types";
import {observer} from "mobx-react";

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
            <TextField
                name="messageId"
                label="Message Id"
                fullWidth={true}
                color="primary"
                value={this.props.store.messageId}
                onChange={this.handleChange}
            />
        );
    }
}

MessageIdField.propTypes = {
    store: PropTypes.object.isRequired,
};
