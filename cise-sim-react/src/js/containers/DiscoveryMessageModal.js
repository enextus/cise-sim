import React from 'react';
import {withStyles} from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import {Button} from "@material-ui/core";
import Slide from "@material-ui/core/Slide";
import DiscoveryForm from "../forms/DiscoveryForm/DiscoveryForm";
import TrackChangesRoundedIcon from '@material-ui/icons/TrackChangesRounded';
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


const discoveryMessageModal = (props) => {

    const {classes} = props;
    const [open, setOpen] = React.useState(false);

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const openButton = (props) => {

        return (
            <Button
                id="clearMsg"
                color="primary"
                variant="contained"
                className={classes.button}
                onClick={handleOpen}
            >
                Discover services
                <TrackChangesRoundedIcon className={classes.rightIcon}/>

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
                }}>

                <Slide direction="down" in={open} mountOnEnter unmountOnExit timeout={2000}>
                    <div id="create-discovery-message">

                   <DiscoveryForm store={props.store} id="create-and-send-discovery-message" onclose={handleClose}/>
                    </div>
                </Slide>


            </Modal>
        </div>
    );
}

export default withStyles(styles)(discoveryMessageModal);