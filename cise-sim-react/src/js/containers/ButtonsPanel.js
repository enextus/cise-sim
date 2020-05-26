import React, {Component} from 'react';
import {Grid} from '@material-ui/core';
import TransitionsModal from "./SendMessageModal";
import MsgClearButton from "../components/MessageForm/MessageInfoClearButton";

// Set of utility/functional Buttons
export default class ButtonsPanel extends Component {

    render() {

        return (
            <Grid container  alignItems="flex-start" justify="flex-start" direction="row">

                    <TransitionsModal store={this.props.store} />
                    <MsgClearButton messageStore={this.props.store.messageStore} />
            </Grid>
        )
    }
}
