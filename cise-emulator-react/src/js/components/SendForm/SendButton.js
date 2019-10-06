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
        return !this.props.store.isTemplateSelected;
    }

    send() {
        this.props.store.send();
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