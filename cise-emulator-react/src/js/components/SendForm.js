import React, {Component} from "react";
import {Grid, Paper, Box} from "@material-ui/core";
import {observer} from "mobx-react";
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import MessageIdField from "./SendForm/MessageIdField";
import CorrelationIdField from "./SendForm/CorrelationIdField";
import TemplateSelect from "./SendForm/TemplateSelect";
import RequiresAckCheck from "./SendForm/RequiresAckCheck";
import PreviewButton from "./SendForm/PreviewButton";
import SendButton from "./SendForm/SendButton";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap'
    }
});

@observer
class SendForm extends Component {

    constructor(props) {
        super(props);
    }

    getTemplateStore() {
        return this.props.store.templateStore
    };

    getAllStores() {
        return this.props.store
    };


    render() {
        const {classes} = this.props;

        return (
                <Box p="4px" mt="68px" mx="100px" bgcolor="background.paper">
                    <Paper  className={classes.root}>
                    <Grid container alignItems="flex-start" spacing={2} >
                        <Grid item xs={6}>
                            <TemplateSelect store={this.getTemplateStore()}/>
                        </Grid>
                        <Grid item xs={6}>
                            <MessageIdField store={this.getTemplateStore()}/>
                        </Grid>
                        <Grid item xs={6}>
                            <CorrelationIdField store={this.getTemplateStore()}/>
                        </Grid>

                        <Grid item xs={6}>
                            <RequiresAckCheck store={this.getTemplateStore()}/>
                        </Grid>

                        <Grid item>
                            <PreviewButton store={this.getTemplateStore()}/>
                        </Grid>

                        <Grid item>
                            <SendButton store={this.getAllStores()}/>
                        </Grid>
                    </Grid>
                </Paper>
                </Box>
        );
    }
}

SendForm.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SendForm);

