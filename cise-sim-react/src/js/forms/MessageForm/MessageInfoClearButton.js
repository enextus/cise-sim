import {Button, withStyles} from '@material-ui/core';
import React, {Component} from 'react';
import {observer} from 'mobx-react';
import VisibilityOffRoundedIcon from '@material-ui/icons/VisibilityOffRounded';

const styles = theme => ({
    button: {
        margin: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },
    leftIcon: {
        marginRight: theme.spacing(1),
    },
});

@observer
class MsgClearButton extends Component {

    isDisabled() {
        return !this.getMessageStore() .historyMsgList.length > 0;
    }

    clear () {
        this.getMessageStore().clearHistory();
        this.getMessageStore().updateThreadWithBody([]);

    }

    render() {
        const {classes} = this.props;

        return (
            <Button
                id="clearMsg"
                color="secondary"
                variant="contained"
                className={classes.button}
                onClick={() => this.clear()}
                disabled={this.isDisabled()}
                size={"small"}
            >

                    <VisibilityOffRoundedIcon className={classes.leftIcon}/>
                    Clear List

                </Button>
        )
    }

    getMessageStore() {
        return this.props.messageStore;
    }
}

export default withStyles(styles)(MsgClearButton)