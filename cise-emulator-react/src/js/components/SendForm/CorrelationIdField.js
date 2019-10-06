import {TextField} from "@material-ui/core";
import React from "react";
import PropTypes from "prop-types";
import {observer} from "mobx-react";

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
            <TextField
                name="correlationId"
                label="Correlation Id"
                fullWidth={true}
                color="primary"
                value={this.props.store.correlationId}
                onChange={this.handleChange}
            />
        );
    }
}

CorrelationIdField.propTypes = {
    store: PropTypes.object.isRequired,
};
