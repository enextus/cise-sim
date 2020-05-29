import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import SendForm from "../components/SendFormV2";
import {Button} from "@material-ui/core";
import SendRoundedIcon from '@material-ui/icons/SendRounded';

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
    },

    rightIcon: {
        marginLeft: theme.spacing(1),
    },

});


const sendMessageModal = (props) => {

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
                id="clearMsg"
                color="secondary"
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
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                className={classes.modal}
                open={open}
                onClose={handleClose}
                closeAfterTransition
                BackdropComponent={Backdrop}
                BackdropProps={{
                    timeout: 500,
                }}>

                <Fade in={open}>
                    <div>
                   <SendForm store={props.store}/>
                    </div>
                </Fade>


            </Modal>
        </div>
    );
}

export default withStyles(styles)(sendMessageModal);