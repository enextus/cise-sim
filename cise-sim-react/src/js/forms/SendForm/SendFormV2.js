import React, {Component} from 'react';
import {Box, Grid} from '@material-ui/core';
import {observer} from 'mobx-react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import MessageIdField from './MessageIdField';
import CorrelationIdField from './CorrelationIdField';
import TemplateSelect from './TemplateSelect';
import RequiresAckCheck from './RequiresAckCheck';
import SendButton from './SendButton';
import PreviewMessage from "./PreviewMessage";
import SendFormHeader from "./SendFormHeader";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 16,
        //margin: '16px auto',
        margin: 'auto',
        maxWidth: 800
    },
});

@observer
class SendFormV2 extends Component {

    constructor(props) {
        super(props);
    }

    getTemplateStore() {
        return this.props.store.templateStore
    };

    getAllStores() {
        return this.props.store
    };


    render() {
        const {classes} = this.props;

        return (
                <Box bgcolor="white">

                    <Grid container alignItems="flex-start" spacing={3} className={classes.root}>

                        <Grid item xs={12}>
                            <SendFormHeader onclose={this.props.onclose}/>
                        </Grid>

                        <Grid item xs={12}>
                            <TemplateSelect store={this.getTemplateStore()}/>
                        </Grid>

                        <Grid item xs={6}>
                            <MessageIdField store={this.getTemplateStore()}/>
                        </Grid>

                        <Grid item xs={6}>
                            <CorrelationIdField store={this.getTemplateStore()}/>
                        </Grid>

                        <Grid item xs={6}>
                            <RequiresAckCheck store={this.getTemplateStore()}/>
                        </Grid>

                        <Grid item xs={6}>
                            <Grid container  alignItems="flex-end" justify="flex-end" direction="row">
                                <SendButton store={this.getAllStores()}/>
                            </Grid>
                        </Grid>

                        <Grid item xs={12}>
                            <PreviewMessage store={this. getAllStores()}/>
                        </Grid>

                    </Grid>
                </Box>
        )
    }
}

SendFormV2.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SendFormV2);

