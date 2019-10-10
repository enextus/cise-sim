import {Button, withStyles} from "@material-ui/core";
import SendRoundedIcon from "@material-ui/icons/SendRounded";
import React from "react";
import PropTypes from "prop-types";
import {observer} from "mobx-react";

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
        const response = await this.props.store.messageStore.send(this.props.store.templateStore.selected);
        if(response.errorCode){
            this.props.enqueueSnackbar(response.message, {
                variant: 'error',
                persist: true,
                action: (key) => (
                    <Button onClick={() => { this.props.closeSnackbar(key) }}>
                        {'Dismiss'}
                    </Button>
                ),
            });
        } else {
            this.props.enqueueSnackbar('New message has been sent.', {variant: 'success',});
        }

    }

    render() {
        const {classes} = this.props;

        return (
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
        );
    }
}

SendButton.propTypes = {
    store: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SendButton)