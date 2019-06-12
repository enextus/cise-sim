import React, { Component } from "react";
import {
  Typography,
  ExpansionPanel,
  ExpansionPanelDetails,
  ExpansionPanelSummary,
  Chip,
  TextField,
  Grid,
  Stepper,
  Step,
  StepLabel
} from "@material-ui/core";
import {call_made, call_received,ExpandMore} from "@material-ui/icons";
//import Singleton from "../../../socket";
//import MessageType from "./MessageType";

import { connect } from "react-redux";

class ReceiveMessage extends Component {
    activeStep;
  constructor(props) {
    super(props);

    this.state = {
      inputValue: ""
    };
  }

  render() {
    if (!this.props.thisUser) return "";

    const rootStyle = {
      width: "100%"
    };

    const headingStyle = {
      fontSize: "18px",
      margin: "15px auto",
      backgroundColor: "#adeac2"
    };
    const chipStyle = {
      verticalAlign: "bottom",
      height: 20,
      width: 20
    };
    const detailsStyle = {
      alignItems: "center",
    };
    const subdetailsStyle = {
      alignItems: "center",
    };

    const contentStyle = {
        flexBasis: "93.33%"
    };
    const textfieldStyle = {
       width: '100%',
      borderLeft: `4px solid 2`,
      padding: `2px 4px`
    };

    const steps = [
        {idStep: 0, label: 'Merge Content'},
        {idStep: 1, label: 'Validate Result'},
        {idStep: 3, label: 'Sign Result'},
        {idStep: 4, label: 'Send'}
        ]
      const activeStepValue =  3;
    return (
      <div style={rootStyle}>
      <ExpansionPanel>
      <ExpansionPanelSummary>
<div>  <Stepper activeStep={1}>
          {steps.map((idStep,label) => {
                  const idStepProps = {};
                  const labelProps = {};
                  if (isStepFailed(idStep)) {
                      labelProps.error = true;
                  }
                  if (isStepSkipped(idStep)) {
                      idStepProps.completed = false;
                  }
                  return (
                      <Step key={idStep} {...idStepProps}>
              <StepLabel {...idStepProps}>{label}
              </StepLabel>
                  </Step>
              );
              })}
          </Stepper></div>
      <div style={contentStyle}>
          <Typography style={detailsStyle}>Click for received log details</Typography>

      <ExpandMore/>
      </div>
      </ExpansionPanelSummary>

      <ExpansionPanelDetails style={subdetailsStyle}>
      <Typography> Produce following outgoing message</Typography>
      <TextField
      id="filled-read-only-input"
      placeholder="sent message"
      multiline
      label="xml format"
      defaultValue={[" <1> Merging template + payload : OK  </1> \n <2> Validating result (template + payload):  IN PROGRESS </2>"]}
      style={textfieldStyle}
      margin="normal"
      InputProps={{
          readOnly: true
      }}
      variant="filled"
          />

      </ExpansionPanelDetails>
       </ExpansionPanel>
   
        <ExpansionPanel >
          <ExpansionPanelSummary expandIcon={<call_made />}>
            <div style={rootStyle}>
              <Typography style={headingStyle}>  Sent Message  </Typography>
            </div>
            <div style={contentStyle}>
              <Typography style={detailsStyle}>Click to see the XML sent </Typography>
    <ExpandMore/>
            </div>
          </ExpansionPanelSummary>
          <ExpansionPanelDetails style={subdetailsStyle}>
          <Grid container  style={contentStyle} spacing={2}>
          <Grid item xs={1} >
          <Chip label="emu.egn.it" style={chipStyle} />
          </Grid>
          <Grid item xs={11}>
          <TextField
            id="filled-read-only-input"
            placeholder="sent message"
    multiline
            label="xml format"
            defaultValue="<This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this>"
            style={textfieldStyle}
            margin="normal"
            InputProps={{
              readOnly: true
            }}
            variant="filled"
          />
          </Grid>
          </Grid>
          </ExpansionPanelDetails>
        </ExpansionPanel>

        <ExpansionPanel>
          <ExpansionPanelSummary expandIcon={<call_received />}>
            
            <div style={contentStyle}>
              <Typography style={detailsStyle}>Click to see the XML received</Typography>

    <ExpandMore/>
    </div>
            <div style={rootStyle}>
            <Typography style={headingStyle}>
              Acknowledgement Message   
            </Typography>
          </div>
          </ExpansionPanelSummary>

          <ExpansionPanelDetails style={subdetailsStyle}>
          <Grid container  style={contentStyle} spacing={2}>
          <Grid item xs={11}>
          <TextField
            id="filled-read-only-input"
            label="xml format"
            placeholder="sent message"
            multiline
            defaultValue="<This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this><This> is where we will put the content </this>"
            style={textfieldStyle}
            margin="normal"
            InputProps={{
              readOnly: true
            }}
            variant="filled"
          />
          </Grid>
          <Grid item xs={1} >
          <Chip label="emu.lsa.it" style={chipStyle} />
          </Grid>
          </Grid>
          </ExpansionPanelDetails>
        </ExpansionPanel>
      </div>
    );
  }

  handleKeyPress = event => {
    if (event.key === "Enter") {
      this.ReceiveMessage();
    }
  };
}


function getSteps() {
    return ['Select campaign settings', 'Create an ad group', 'Create an ad'];
}

function getStepContent(step) {
    switch (step) {
        case 0:
            return 'Select campaign settings...';
        case 1:
            return 'What is an ad group anyways?';
        case 2:
            return 'This is the bit I really care about!';
        default:
            return 'Unknown step';
    }
}
function isStepOptional(step) {
    return step === 3;
}

function isStepFailed(step) {
    return step === 3;
}

function isStepSkipped(step) {
    return step === 4;
}

// Whatever is returned is going to show up as props inside UserList
function mapStateToProps(state) {
  return {
    messages: state.messages,
    thisUser: state.thisUser
  };
}



// Promote component to container
export default connect(mapStateToProps)(ReceiveMessage);
