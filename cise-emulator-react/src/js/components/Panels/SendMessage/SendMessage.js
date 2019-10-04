import React, {Component} from "react";
import {Button, Checkbox, FormControl, FormControlLabel, Grid, InputLabel, Select, TextField} from "@material-ui/core";
import {observer} from "mobx-react";
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Paper from "@material-ui/core/Paper";
import SendRoundedIcon from "@material-ui/icons/SendRounded";
import DescriptionIcon from '@material-ui/icons/Description';
import MenuItem from "@material-ui/core/MenuItem";

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formControl: {
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
    button: {
        margin: theme.spacing(1),
    },
    leftIcon: {
        marginRight: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },
    iconSmall: {
        fontSize: 20,
    },
});


@observer
class SendMessage extends Component {
    messagePreview;
    state = {
        selectedOption: null,
    };

    constructor(props) {
        super(props);
        this.updateProperty = this.updateProperty.bind(this);
        this.onChange = this.onChange.bind(this);
        this.props.messageCandidate.messageId = this.getId();
    }

    updateProperty(key, value) {
        this.props.messageCandidate[key] = value
    }

    onChange(event) {
        this.updateProperty(event.target.name, event.target.value)
    }

    onMessageTemplateChange = messageTemplate => {
        this.props.messageCandidate.templateService = messageTemplate.target.value;
        console.log(`Option selected:`, messageTemplate);
    };


    onAsyncAcknowledgeChange = asyncAcknowledge => {
        this.props.messageCandidate.asyncAcknowledge = !this.props.messageCandidate.asyncAcknowledge;
        console.log(`Option handleChangeAsyncAcknowledge:`, this.props.messageCandidate.asyncAcknowledge);
    };


    send() {
        this.props.messagePreview.send(this.props.messageCandidate);
        this.props.messageCandidate.messageId = this.getId();
    }

    render() {
        const {classes} = this.props;
        return (
            <div style={{padding: 16, margin: 'auto', maxWidth: 800}}>
                <Paper style={{padding: 16}}>
                    <Grid container alignItems="flex-start" spacing={2}>

                        <Grid item xs={6}>
                            <TextField
                                name="messageId"
                                label="Message Id"
                                fullWidth={true}
                                color="primary"
                                value={this.props.messageCandidate.messageId}
                                onChange={this.onChange}
                            />
                        </Grid>
                        <Grid item xs={6}>
                            <TextField
                                name="correlationId"
                                label="correlation Id"
                                color="Primary"
                                fullWidth={true}
                                value={this.props.messageCandidate.correlationId}
                                onChange={this.onChange}
                            />
                        </Grid>

                        <Grid item xs={5}>
                            <FormControl className={classes.formControl} fullWidth={true}>
                                    <FormControl className={classes.formControl} fullWidth={true}>
                                        <InputLabel htmlFor="templateService">Message Template</InputLabel>
                                        <Select
                                            label="Message Template"
                                            placeholder="select the template"
                                            value={this.props.messageCandidate.templateService}
                                            onChange={this.onMessageTemplateChange}
                                            inputProps={{
                                                name: 'templateService',
                                                id: 'templateService'
                                            }}>
                                            {this.getMessageTemplateItems().map(this.buildMenuItem)}
                                        </Select>
                                    </FormControl>
                            </FormControl>
                        </Grid>

                        <Grid item xs={12}>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        id="asyncAcknowledge"
                                        name="asyncAcknowledge"
                                        onChange={this.onAsyncAcknowledgeChange}
                                        checked={this.props.messageCandidate.asyncAcknowledge}
                                        value={this.props.messageCandidate.asyncAcknowledge.valueOf()}/>
                                }
                                label="Require Async Ack"/>
                        </Grid>
                        <Grid item style={{marginTop: 16}}>
                            <Button id="preview"
                                    variant="contained"
                                    onClick={() => this.preview()}
                                    color="primary"
                                    className={classes.button}
                                    disabled={this.isDisabled()}>
                                Preview
                                <DescriptionIcon className={classes.rightIcon}/>
                            </Button>

                        </Grid>
                        <Grid item style={{marginTop: 16}}>
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
                        </Grid>
                    </Grid>
                </Paper>
            </div>
        );
    }

    getMessageTemplateItems() {
        return this.props.store.appStore.templateOptions;
    }

    buildMenuItem(item) {
        return (
            <MenuItem key={item.value} value={item.value}>{item.label}</MenuItem>
        );
    }

    isDisabled() {
        return this.props.messageCandidate.templateService === "#none";
    }

    getId() {
        let d = new Date().getTime();
        //TODO extend uuid to form "ba6f94e3-2004-439b-a09f-a6f1f96ea34c"
        //TODO validate "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxyx"
        return 'xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxyx'.replace(/[xy]/g, this.getReplacer(d))
    }

    getReplacer(d) {
        return function (c) {
            const r = ((d + Math.random() * 16) % 16) | 0;
            d = Math.floor(d / 16);
            return (c === 'x' ? r : (r & 0x3) | 0x8).toString(16)
        };
    };

}

SendMessage.propTypes = {
    classes: PropTypes.object.isRequired,
};


export default withStyles(styles)(SendMessage)
