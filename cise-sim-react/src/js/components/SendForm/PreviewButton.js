import {Button, withStyles} from "@material-ui/core";
import DescriptionIcon from "@material-ui/icons/Description";
import React from "react";
import PropTypes from "prop-types";
import {observer} from "mobx-react";
import {withSnackbar} from 'notistack';


const styles = theme => ({
    button: {
        margin: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },
});

const action = (key) => (
    <Button onClick={() => {
        props.closeSnackbar(key)
    }}>
        {'Dismiss'}
    </Button>
)

@observer
class PreviewButton extends React.Component {

    isDisabled() {
        return !this.props.store.isTemplateSelected;
    }

    async preview() {
        const response = await this.props.store.preview();
        console.log("TemplateStore.preview response: ", response);
        if (response.errorCode) {
            this.props.enqueueSnackbar(response.errorMessage, {
                variant: 'error',
                persist: true,
                action: (key) => (
                    <Button onClick={() => {
                        this.props.closeSnackbar(key)
                    }}>
                        {'Dismiss'}
                    </Button>
                ),
            })
        } else {
            this.props.enqueueSnackbar('New preview has been generated.', {variant: 'success',});
        }
    }

    render() {
        const {classes} = this.props;

        return (
            <Button id="preview"
                    variant="contained"
                    onClick={() => this.preview()}
                    color="primary"
                    className={classes.button}
                    disabled={this.isDisabled()}>
                Preview
                <DescriptionIcon className={classes.rightIcon}/>
            </Button>
        )
    }

}


PreviewButton.propTypes = {
    store: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(withSnackbar(PreviewButton))
