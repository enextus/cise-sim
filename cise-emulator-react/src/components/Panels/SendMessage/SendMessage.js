import React, {Component} from "react";
import {Button, Checkbox, FormControl, FormControlLabel, InputLabel, Select, TextField} from "@material-ui/core";
import {observer} from "mobx-react";
import {makeStyles, withStyles} from '@material-ui/core/styles';
import MenuItem from "@material-ui/core/MenuItem";
import CssBaseline from "@material-ui/core/CssBaseline";
import PropTypes from 'prop-types';
import Paper from "@material-ui/core/Paper";
import Grid from "@material-ui/core/Grid";
import SendRoundedIcon from "@material-ui/icons/SendRounded";
import DescriptionIcon from '@material-ui/icons/Description';

const styles = makeStyles(theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
    },
    formControl: {
        margin: theme.spacing(1),
        minWidth: 120,
    },
    selectEmpty: {
        marginTop: theme.spacing(2),
    },
    button: {
        margin: theme.spacing(1),
        width: 200,
    },
    leftIcon: {
        marginRight: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: 100,
    },
    iconSmall: {
        fontSize: 20,
    },
}));

@observer
class SendMessage extends Component {

    state = {
        selectedOption: null,
    };

    constructor(props) {
        super(props);
        this.updateProperty = this.updateProperty.bind(this);
        this.onChange = this.onChange.bind(this);
    }

    updateProperty(key, value) {
        this.props.messageCandidate[key] = value
    }

    onChange(event) {
        this.updateProperty(event.target.name, event.target.value)
    }

    handleChangeTemplateService = templateservice => {
        this.props.messageCandidate.templateService = templateservice;
        console.log(`Option selected:`, templateservice);
    };

    handleChangeAsyncAcknowledge = atemplatepayload => {
        this.props.messageCandidate.asyncAcknowledge = !this.props.messageCandidate.asyncAcknowledge;
        console.log(`Option handleChangeAsyncAcknowledge:`, this.props.messageCandidate.asyncAcknowledge);
    };

    send() {
        this.props.messageCandidate.messageId = this.getId();
        this.props.messagePreview.send(this.props.messageCandidate);
    }

    preview() {
        this.props.messageCandidate.messageId = this.getId();
        this.props.messagePreview.preview(this.props.messageCandidate);
    }

    render() {
        const {classes} = this.props;
        return (
            <div style={{padding: 16, margin: 'auto', maxWidth: 800}}>
                <CssBaseline/>
                <Paper style={{padding: 16}}>
                    <Grid container alignItems="flex-start" spacing={2}>
                        <Grid item xs={6}>
                            <TextField
                                name="messageId"
                                label="Message Id"
                                fullWidth={true}
                                color="primary"
                                value={this.props.messageCandidate.messageId}/>
                        </Grid>
                        <Grid item xs={6}>
                            <TextField name="correlationId"
                                       label="Correlation Id"
                                       fullWidth={true}
                                       color="primary"
                                       value={this.props.messageCandidate.correlationId}
                                       onChange={this.onChange}/>
                        </Grid>
                        <Grid item xs={6}>
                            <FormControl className={classes.formControl} fullWidth={true}>
                                <InputLabel htmlFor="templateService">Message Template</InputLabel>
                                <Select
                                    label="Message Template"
                                    placeholder="select the template"
                                    className={classes.selectEmpty}
                                    // options={this.props.store.appStore.templateOptions}
                                    value={this.props.messageCandidate.templateService}
                                    onChange={this.handleChangeTemplateService}
                                    inputProps={{
                                        name: 'templateService',
                                        id: 'templateService'
                                    }}>
                                    <MenuItem value="">
                                        <em>None</em>
                                    </MenuItem>
                                    <MenuItem value={10}>Ten</MenuItem>
                                    <MenuItem value={20}>Twenty</MenuItem>
                                    <MenuItem value={30}>Thirty</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={6}>
                            <FormControl className={classes.formControl}
                                         fullWidth={true}
                                         disabled={true}>
                                <InputLabel htmlFor="payloadService">Payload Template</InputLabel>
                                <Select
                                    label="Payload Template"
                                    // placeholder="XML Payload Template"
                                    className={classes.selectEmpty}
                                    // options={this.props.store.appStore.templateOptions}
                                    value={this.props.messageCandidate.templateService}
                                    onChange={this.handleChangeTemplateService}
                                    inputProps={{
                                        name: 'payloadService',
                                        id: 'payloadService'
                                    }}>
                                    <MenuItem value="">
                                        <em>None</em>
                                    </MenuItem>
                                    <MenuItem value={10}>Ten</MenuItem>
                                    <MenuItem value={20}>Twenty</MenuItem>
                                    <MenuItem value={30}>Thirty</MenuItem>
                                </Select>
                            </FormControl>
                        </Grid>
                        <Grid item xs={12}>
                            <FormControlLabel
                                control={
                                    <Checkbox
                                        id="asyncAcknowledge"
                                        name="asyncAcknowledge"
                                        onChange={this.handleChangeAsyncAcknowledge}
                                        checked={this.props.messageCandidate.asyncAcknowledge}
                                        value={this.props.messageCandidate.asyncAcknowledge}/>
                                }
                                label="Require Async Ack"/>
                        </Grid>
                        <Grid item style={{marginTop: 16}}>
                            <Button id="preview"
                                    onClick={() => this.preview()}
                                    color="primary"
                                    className={classes.button}
                                    variant="contained"
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
    }
}

SendMessage.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(SendMessage)