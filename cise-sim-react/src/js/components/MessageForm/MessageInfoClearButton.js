import {Button, withStyles} from '@material-ui/core';
import ClearAllRoundedIcon from '@material-ui/icons/ClearAllRounded';
import React from 'react';
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
class MsgClearButton extends React.Component {

    isDisabled() {
        return !this.props.messageStore.historyMsgList.length > 0;
    }

    clear () {
        this.props.messageStore.historyMsgList = [];
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
}



export default withStyles(styles)(MsgClearButton)