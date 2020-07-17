import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import SendForm from "../forms/CreateMessageForm/CreateMessageForm";
import {Button} from "@material-ui/core";
import SendRoundedIcon from '@material-ui/icons/SendRounded';
import {buttonSizeSmall, fontSizeExtraSmall} from "../layouts/Font";

const styles = theme => ({
    modal: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
    },

    paper: {
        backgroundColor: theme.palette.background.paper,
        border: '2px solid #000',
        boxShadow: theme.shadows[5],
        padding: theme.spacing(2, 4, 3),
    },

    button: {
        margin: theme.spacing(1),
        maxHeight: buttonSizeSmall,
        fontSize:fontSizeExtraSmall
    },

    rightIcon: {
        marginLeft: theme.spacing(1),
    },

});


const createMessageModal = (props) => {

    const {classes} = props;
    const [open, setOpen] = React.useState(false);

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
        props.store.templateStore.resetPreview();
    };

    const openButton = (props) => {

        return (
            <Button
                id="createMsg"
                color="primary"
                variant="contained"
                className={classes.button}
                onClick={handleOpen}>
                Create Message
                <SendRoundedIcon className={classes.rightIcon}/>

            </Button>
        )
    }

    return (
        <div>
            {openButton(props)}

            <Modal

                aria-labelledby="create-message"
                aria-describedby="create-and-send-cise-message"
                className={classes.modal}
                open={open}
                onClose={handleClose}
                closeAfterTransition
                disableBackdropClick
                BackdropComponent={Backdrop}
                BackdropProps={{
                    timeout: 500,
                }}
                size={"small"}
            >

                <Fade in={open}>
                    <div id="create-message">
                        <SendForm store={props.store} id="create-and-send-cise-message" onclose={handleClose}/>
                    </div>
                </Fade>

            </Modal>
        </div>
    );
}

export default withStyles(styles)(createMessageModal);