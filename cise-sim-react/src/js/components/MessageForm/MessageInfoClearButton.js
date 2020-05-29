import {Button, withStyles} from '@material-ui/core';
import ClearAllRoundedIcon from '@material-ui/icons/ClearAllRounded';
import React, {Component} from 'react';
import {observer} from 'mobx-react';

const styles = theme => ({
    button: {
        margin: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
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
                disabled={this.isDisabled()}>
                Clear Msg
                <ClearAllRoundedIcon className={classes.rightIcon}/>

            </Button>
        )
    }

    getMessageStore() {
        return this.props.messageStore;
    }
}

export default withStyles(styles)(MsgClearButton)