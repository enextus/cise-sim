import React, {Component} from "react";
import {
    Button,
    Checkbox,
    FormControl,
    FormGroup,
    Grid,
    TextField
    } from "@material-ui/core";
import Select from 'react-select';
import {observer} from "mobx-react";



@observer
export default class SendMessage extends Component {

    state =  {
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
        this.props.messageCandidate.templateService=templateservice;
        console.log(`Option selected:`, templateservice);
    };

    handleChangeTemplatePayload = atemplatepayload => {
        this.props.messageCandidate.templatePayload=atemplatepayload;
        console.log(`Option selected:`, atemplatepayload);
    };

    handleChangeAsyncAcknowledge = atemplatepayload => {
        this.props.messageCandidate.asyncAcknowledge=!this.props.messageCandidate.asyncAcknowledge;
        console.log(`Option handleChangeAsyncAcknowledge:`, this.props.messageCandidate.asyncAcknowledge);
    };


    send () {
        this.props.messageCandidate.messageId=this.getId();
        this.props.messagePreview.send(this.props.messageCandidate);
    }

    preview () {
        this.props.messageCandidate.messageId= this.getId();
        this.props.messagePreview.preview(this.props.messageCandidate);
    }



    render() {
        // always refer to first message in list

        const customStyles = {
            option: (provided, state) => ({
                ...provided,
                font: 'Liberation Sans',
                fontFamily: 'Liberation Sans',
                borderBottom: '1px dotted grey',
                color: state.isSelected ? 'grey' : 'blue',
                padding: 5,
            }),
            control: () => ({
                // none of react-select's styles are passed to <Control />
                width: 200,
                font: 'Liberation Sans',
                fontFamily: 'Liberation Sans',
            }),
            singleValue: (provided, state) => {
                const opacity = state.isDisabled ? 0.5 : 1;
                const transition = 'opacity 300ms';
                return { ...provided, opacity, transition };
            }
        };



        const fieldStyle = {
            width: "100%",
            font: "Liberation Sans",
            fontFamily: "Liberation Sans"
        };
        const relativeButtonTableStyle = {
            margin: '2px auto' ,
            position:'relative' ,
            left:'100px',
            font: "Liberation Sans",
        };
        const buttonStyle = {
            margin: '10px auto' ,
            backgroundColor: "rgb(205,205,205)",
            left:'160px',
            font: "Liberation Sans",
        };
        const formstyle = {
            width: "90%",
            height: "50%",
            top: "20",
            margin: "20px auto",
            color: "#555555",
            font: "Liberation Sans"
        };

        const field = [
            {
                name: 'asyncAcknowledge',
                label: 'asyncAcknowledge: ',
                type: 'checkbox',
                rules: 'boolean',
                fieldStyle: "font: \"Liberation Sans\""
            },
        ];


        return (
            <FormGroup style={formstyle} >
                <Grid container spacing={3}>
                    <Grid item xs={6}>
                        <TextField
                            name="messageId"
                            label="messageId"
                            style={fieldStyle}
                            margin="normal"
                            variant="filled"
                            placeholder="set at first sending intent"
                            value={this.props.messageCandidate.messageId}
                            InputLabelProps={{
                                shrink: true,
                            }}
                        /> </Grid>
                    <Grid item xs={6}>
                        <TextField  name="correlationId"
                            label="correlationId"
                            style={fieldStyle}
                            placeholder="Identifier you can used to follow related messages"
                            margin="normal"
                            variant="filled"
                            value={this.props.messageCandidate.correlationId}
                            onChange={this.onChange}
                            InputLabelProps={{
                                shrink: true,
                            }}
                        />
                    </Grid>

                    <Grid item xs={6}>
                            <Select
                                styles={customStyles}
                                name="templateService"
                                label="templateService"
                                placeholder={"message template * (required)"}
                                options={this.props.store.appStore.templateOptions}
                                value={this.props.messageCandidate.templateService}
                                onChange={this.handleChangeTemplateService}
                            />

                    </Grid>

                    <Grid item xs={6}>
                            <Select
                                styles={customStyles}
                                name="templatePayload"
                                label="templatePayload"
                                placeholder={"... the optional payload to include into."}
                                options={this.props.store.appStore.payloadOptions}
                                value={this.props.messageCandidate.templatePayload}
                                onChange={this.handleChangeTemplatePayload}
                            />
                    </Grid>
                    <Grid item xs={5}>

                        <FormControl>
                            <table><tbody>
                            <tr><td> <Checkbox name={"asyncAcknowledge"} style={fieldStyle} value={this.props.messageCandidate.asyncAcknowledge} checked={this.props.messageCandidate.asyncAcknowledge} onChange={this.handleChangeAsyncAcknowledge}/></td>
                                <td style={fieldStyle}>Asynchronous Acknoledgement</td></tr></tbody></table>


                        </FormControl>

                    </Grid>
                    <Grid item xs={7}>
                        <span>   </span>
                        <Button disabled={this.props.messageCandidate.templateService=="#none"}
                                style={buttonStyle} id="preview"
                                onClick={() => this.preview() }>
                            Preview
                        </Button>
                        <span>   </span>

                        <Button  disabled={this.props.messageCandidate.templateService=="#none"}
                                 style={buttonStyle} id="send"
                                 onClick={() => this.send() }>
                            Send
                        </Button>
                    </Grid>
                </Grid>
            </FormGroup>
        );
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



