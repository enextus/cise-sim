import React, {Component} from "react";
import {Grid} from "@material-ui/core";
import {observer} from "mobx-react";
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Paper from "@material-ui/core/Paper";
import MessageIdField from "./SendForm/MessageIdField";
import CorrelationIdField from "./SendForm/CorrelationIdField";
import TemplateSelect from "./SendForm/TemplateSelect";
import RequiresAckCheck from "./SendForm/RequiresAckCheck";
import PreviewButton from "./SendForm/PreviewButton";
import SendButton from "./SendForm/SendButton";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 16,
        margin: '16px auto',
        maxWidth: 800
    },
});

@observer
class SendForm extends Component {

    constructor(props) {
        super(props);
    }

    getStore() {
        return this.props.store.templateStore
    };

    render() {
        const {classes} = this.props;

        return (
            <Paper className={classes.root}>
                <Grid container alignItems="flex-start" spacing={2}>
                    <Grid item xs={6}>
                        <MessageIdField store={this.getStore()}/>
                    </Grid>

                    <Grid item xs={6}>
                        <CorrelationIdField store={this.getStore()}/>
                    </Grid>

                    <Grid item xs={6}>
                        <TemplateSelect store={this.getStore()}/>
                    </Grid>

                    <Grid item xs={12}>
                        <RequiresAckCheck store={this.getStore()}/>
                    </Grid>

                    <Grid item>
                        <PreviewButton store={this.getStore()}/>
                    </Grid>

                    <Grid item>
                        <SendButton store={this.getStore()}/>
                    </Grid>
                </Grid>
            </Paper>
        );
    }
}

SendForm.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SendForm);

