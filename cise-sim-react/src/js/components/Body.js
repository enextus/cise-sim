import React, {Component} from 'react';
import SendForm from './SendForm';
import PushedMessage from './PushedMessage';
import PreviewMessage from './PreviewMessage';
import PulledMessage from './PulledMessage';
import {Grid} from '@material-ui/core';
import {observer} from 'mobx-react';
import HistoryMessage from "./HistoryMessage";

@observer
export default class Body extends Component {


    render() {
        return (
            <Grid container spacing={2}>
                <Grid item xs={12}>
                    <SendForm
                        store={this.props.store}/>
                    <PreviewMessage
                        store={this.props.store}/>
                    <PushedMessage
                        store={this.props.store}/>
                    <PulledMessage
                        store={this.props.store}/>
                    <HistoryMessage
                        store={this.props.store}/>
                </Grid>
            </Grid>
        )
    }
}