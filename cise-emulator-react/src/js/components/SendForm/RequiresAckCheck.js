import {Checkbox, FormControlLabel} from "@material-ui/core";
import React from "react";
import PropTypes from "prop-types";
import {observer} from "mobx-react";

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
            <FormControlLabel
                control={
                    <Checkbox
                        id="asyncAcknowledge"
                        name="asyncAcknowledge"
                        onChange={this.handleChange}
                        checked={this.props.store.requiresAck}
                        value={this.props.store.requiresAck}/>
                }
                label="Requires Async Ack"/>
        );
    }
}
RequiresAckCheck.propTypes = {
    store: PropTypes.object.isRequired,
};
