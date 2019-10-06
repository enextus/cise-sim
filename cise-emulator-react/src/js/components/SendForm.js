import React, {Component} from "react";
import {Button, Grid} from "@material-ui/core";
import {observer} from "mobx-react";
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Paper from "@material-ui/core/Paper";
import SendRoundedIcon from "@material-ui/icons/SendRounded";
import MessageIdField from "./SendForm/MessageIdField";
import CorrelationIdField from "./SendForm/CorrelationIdField";
import TemplateSelect from "./SendForm/TemplateSelect";
import RequiresAckCheck from "./SendForm/RequiresAckCheck";
import PreviewButton from "./SendForm/PreviewButton";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formControl: {
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
    button: {
        margin: theme.spacing(1),
    },
    leftIcon: {
        marginRight: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },
    iconSmall: {
        fontSize: 20,
    },
});

@observer
class SendForm extends Component {

    messagePreview;

    constructor(props) {
        super(props);
    }

    send() {
        this.props.messagePreview.send(this.props.messageCandidate);
        this.props.messageCandidate.messageId = this.getStore().generateMessageId();
    }

    render() {
        const {classes} = this.props;

        return (
            <div style={{padding: 16, margin: 'auto', maxWidth: 800}}>
                <Paper style={{padding: 16}}>
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
                            <Button
                                id="send"
                                color="secondary"
                                variant="contained"
                                className={classes.button}
                                onClick={() => this.send()}
                                disabled={this.isDisabled()}>
                                Send
                                <SendRoundedIcon className={classes.rightIcon}/>
                            </Button>
                        </Grid>
                    </Grid>
                </Paper>
            </div>
        );
    }

    getStore() {
        return this.props.store.templateStore
    };

    isDisabled() {
        return this.props.messageCandidate.templateService === "#none";
    }
}

SendForm.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SendForm)
