import React, {Component} from 'react';
import SendMessage from './SendMessage/SendMessage';
import {Grid} from '@material-ui/core';
import {observer} from 'mobx-react';
import messageCandidate from "../../models/message/MessageCandidate";
import MessagePushAPI from "../../models/message/MessagePushAPI";

@observer
export default class Panels extends Component {

    render() {
        const messagePreview = new MessagePushAPI();

        return (
            <React.Fragment>
                <Grid container spacing={2}>
                    <Grid item xs={12}>
                        <SendMessage
                            store={this.props.store}
                            messageCandidate={messageCandidate}
                            messagePreview={messagePreview}/>
                    </Grid>
                </Grid>
            </React.Fragment>
        );
    }
}



