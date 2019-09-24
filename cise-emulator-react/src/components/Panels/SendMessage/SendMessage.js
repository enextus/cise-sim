import React, {Component} from "react";
import {
    Button,
    Checkbox,
    FormControlLabel,
    FormControl,
    FormGroup,
    Grid,
    TextField,
    Select,
    InputLabel
} from "@material-ui/core";
import {observer} from "mobx-react";
import {makeStyles} from '@material-ui/core/styles';
import withStyles from "@material-ui/styles/withStyles/withStyles";

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
        width: 200,
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
            <form className={classes.root} autoComplete="off">
                <FormGroup row>
                    <Grid container
                          spacing={2}
                          direction="row"
                          justify="space-evenly"
                          alignContent="stretch"
                          alignItems="center">

                        <Grid item xs={4}>
                            <TextField
                                name="messageId"
                                label="Message Id"
                                color="primary"
                                value={this.props.messageCandidate.messageId}/>
                        </Grid>

                        <Grid item xs={4}>
                            <TextField name="correlationId"
                                       label="Correlation Id"
                                       color="primary"
                                       value={this.props.messageCandidate.correlationId}
                                       onChange={this.onChange}/>
                        </Grid>

                        <Grid item xs={9}>
                            <FormControl className={classes.formControl}>
                                <InputLabel forHtml="templateService">XML Message Template</InputLabel>
                                <Select
                                    label="XML Message Template"
                                    placeholder="XML Message Template"
                                    className={classes.selectEmpty}
                                    options={this.props.store.appStore.templateOptions}
                                    value={this.props.messageCandidate.templateService}
                                    onChange={this.handleChangeTemplateService}
                                    inputProps={{
                                        name: 'templateService',
                                        id: 'templateService'
                                    }}
                                    />
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
                                label="Require Async Ack"
                            />
                        </Grid>

                        <Grid item xs={6}>
                            <Button id="preview"
                                    onClick={() => this.preview()}
                                    color="primary"
                                    variant="contained"
                                    disabled={this.isDisabled()}>
                                Preview
                            </Button>
                        </Grid>
                        <Grid item xs={6}>
                            <Button
                                id="send"
                                color="secondary"
                                variant="contained"
                                onClick={() => this.send()}
                                disabled={this.isDisabled()}>
                                Send
                            </Button>
                        </Grid>
                    </Grid>
                </FormGroup>
            </form>
        );
    }

    isDisabled() {
        return this.props.messageCandidate.templateService === "#none";
    }

    getId() {
        let d = new Date().getTime();
        //TODO extend uuid to form "ba6f94e3-2004-439b-a09f-a6f1f96ea34c"
        //TODO validate "xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxyx"
        const uuid = 'xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxyx'.replace(/[xy]/g, function (c) {
            const r = ((d + Math.random() * 16) % 16) | 0;
            d = Math.floor(d / 16);
            return (c === 'x' ? r : (r & 0x3) | 0x8).toString(16)
        });
        return uuid
    }
}

export default withStyles(styles)(SendMessage)