import {Button, withStyles} from '@material-ui/core';
import SendRoundedIcon from '@material-ui/icons/SendRounded';
import React from 'react';
import PropTypes from 'prop-types';
import {observer} from 'mobx-react';
import {withSnackbar} from 'notistack';

const styles = theme => ({
    button: {
        margin: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },
});

@observer
class SendButton extends React.Component {

    isDisabled() {
        return !this.props.store.templateStore.isTemplateSelected;
    }

    async send() {
        const response = await this.props.store.messageStore.send(
            this.props.store.templateStore.selected,
            this.props.store.templateStore.messageId, 
            this.props.store.templateStore.correlationId, 
            this.props.store.templateStore.requiresAck
        );
        if(response.errorCode){
            this.props.enqueueSnackbar(response.errorMessage, {
                variant: 'error',
                persist: true,
                action: (key) => (
                    <Button onClick={() => { this.props.closeSnackbar(key) }}>
                        {'Dismiss'}
                    </Button>
                ),
            })
        } else {
            this.props.enqueueSnackbar('New message has been sent.', {variant: 'success',});
            this.props.store.templateStore.createNewMessageId();
            this.props.store.templateStore.resetPreview();
        }

    }

    render() {
        const {classes} = this.props;

        return (
            <Button
                id="send"
                color="primary"
                variant="contained"
                className={classes.button}
                onClick={() => this.send()}
                disabled={this.isDisabled()}>
                Send
                <SendRoundedIcon className={classes.rightIcon}/>
            </Button>
        )
    }
}

SendButton.propTypes = {
    store: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(withSnackbar(SendButton))