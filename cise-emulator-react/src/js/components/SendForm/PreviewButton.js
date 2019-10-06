import {Button, withStyles} from "@material-ui/core";
import DescriptionIcon from "@material-ui/icons/Description";
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
class PreviewButton extends React.Component {

    isDisabled() {
        return false;
    }

    preview() {
        this.props.store.preview();
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
        );
    }
}

PreviewButton.propTypes = {
    store: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(PreviewButton)