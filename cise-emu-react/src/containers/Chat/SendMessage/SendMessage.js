import React, { Component } from "react";
import {
  FormGroup,
  TextField,
  Button,
  FormControl,
  InputLabel,
  Select,
  Checkbox,
  FormControlLabel,
  Grid
} from "@material-ui/core";
import Singleton from "../../../socket";
import MessageType from "./MessageType";
import { connect } from "react-redux";

class SendMessage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      inputValue: ""
    };
  }

  render() {
    if (!this.props.thisUser) return ""; // clean pre-condition

    //const [personName, setPersonName] = React.useState([]);

    // function handleChange(event) {
    //   setPersonName(event.target.value);
    // }

    // function handleChangeMultiple(event) {
    //   const { options } = event.target;
    //   const value = [];
    //   for (let i = 0, l = options.length; i < l; i += 1) {
    //     if (options[i].selected) {
    //       value.push(options[i].value);
    //     }
    //   }
    //   setPersonName(value);
    // }
    

    const fieldStyle = {
      width: "60%"
    };
    const btnStyles = {
      margin: "5%",
      backgroundColor: "#217233",
    }
    const formstyle = {
      width: "100%",
      height: "50%",
      top: 60,
      margin: "20px auto",
      color: "#217233"
    };

    return (
      <FormGroup style={formstyle}>
        <Grid container spacing={2} >
        <Grid item xs={12}>
        <TextField
    id="filled-full-width"
    label="Message Raw Format"
    fullWidth={true}
    style={fieldStyle}
    placeholder="As it is to Be Sent"
    helperText="format used to order execution to the simulator service"
    fullWidth
    margin="normal"
    variant="filled"
    value={this.state.inputValue}
    autoFocus
    onChange={this.updateInputValue.bind(this)}
    onKeyPress={this.handleKeyPress}
    InputLabelProps={{
      shrink: true,
    }}
    />
        </Grid>
          <Grid item xs={6}>
          <FormControl >
          <InputLabel shrink variant="outlined" htmlFor="select-multiple-native">
        Xml Template
    </InputLabel>
    <Select
    native
    value={this.state.OptionX}
        >
        <option value="" />
        <option value="AcknowledgementTemplate.xml">AcknowledgementTemplate</option>
        <option value="FeedbackTemplate.xml">FeedbackTemplate</option>
        <option value="Pull_discoverTemplate.xml">Pull_discoverTemplate</option>
        <option value="Pull_get_subscribersTemplate.xml">Pull_get_subscribersTemplate</option>
        <option value="Pull_requestTemplate.xml">Pull_requestTemplate</option>
        <option value="Pull_responseTemplate.xml">Pull_responseTemplate</option>
        <option value="Pull_subscribeTemplate.xml">Pull_subscribeTemplate</option>
        <option value="Pull_unsubscribeTemplate.xml">Pull_unsubscribeTemplate</option>
        <option value="nautilu">PushTemplate</option>
        <option value="SubscribeTemplate.xml">SubscribeTemplate</option>


        </Select>
        </FormControl>
    </Grid>
        <Grid item xs={6}>

        <FormControl >
        <InputLabel shrink htmlFor="select-multiple-native">
        Payload
    </InputLabel>
    <Select
    native
    value={this.state.OptionX}
        >
        <option value="" />
        <option value="Action.xml">Action.xml</option>
        <option value="Agent.xml">Agent.xml</option>
        <option value="Aircraft.xml">Aircraft.xml</option>
        <option value="Anomaly.xml">Anomaly.xml</option>
        <option value="AnomalyMessage.xml">AnomalyMessage.xml</option>
        <option value="AnomalyMessage%5BCISE.ext%5D.xml">AnomalyMessage%5BCISE.ext%5D.xml</option>
    <option value="Cargo.xml">Cargo.xml</option>
        <option value="CargoDocument.xml">CargoDocument.xml</option>
        <option value="CertificateDocument.xml">CertificateDocument.xml</option>
        <option value="CrisisIncident.xml">CrisisIncident.xml</option>
        <option value="Document.xml">Document.xml</option>
        <option value="EventDocument.xml">EventDocument.xml</option>
        <option value="FormalOrganization.xml">FormalOrganization.xml</option>
        <option value="Incident.xml">Incident.xml</option>
        <option value="IncidentMessage.xml">IncidentMessage.xml</option>
        <option value="IrregularMigrationIncident.xml">IrregularMigrationIncident.xml</option>
        <option value="LandVehicle.xml">LandVehicle.xml</option>
        <option value="LawInfringementIncident.xml">LawInfringementIncident.xml</option>
        <option value="Location.xml">Location.xml</option>
        <option value="LocationDocument.xml">LocationDocument.xml</option>
        <option value="LocationMessage.xml">LocationMessage.xml</option>
        <option value="MaritimeSafetyIncident.xml">MaritimeSafetyIncident.xml</option>
        <option value="MeteoOceanographicCondition.xml">MeteoOceanographicCondition.xml</option>
        <option value="Movement.xml">Movement.xml</option>
        <option value="OperationalAsset.xml">OperationalAsset.xml</option>
        <option value="Organization.xml">Organization.xml</option>
        <option value="OrganizationalCollaboration.xml">OrganizationalCollaboration.xml</option>
        <option value="OrganizationalUnit.xml">OrganizationalUnit.xml</option>
        <option value="OrganizationDocument.xml">OrganizationDocument.xml</option>
        <option value="Person.xml">Person.xml</option>
        <option value="PersonDocument.xml">PersonDocument.xml</option>
        <option value="PersonMessage.xml">PersonMessage.xml</option>
        <option value="PortOrganization.xml">PortOrganization.xml</option>
        <option value="PRF_01_Vessel_Push_05.xml">PRF_01_Vessel_Push_05.xml</option>
        <option value="PRF_01_Vessel_Push_06.xml">PRF_01_Vessel_Push_06.xml</option>
        <option value="PRF_01_Vessel_Push_07.xml">PRF_01_Vessel_Push_07.xml</option>
        <option value="PRF_01_Vessel_Push_08.xml">PRF_01_Vessel_Push_08.xml</option>
        <option value="PRF_04_Vessel_Push_05.xml">PRF_04_Vessel_Push_05.xml</option>
        <option value="Risk.xml">Risk.xml</option>
        <option value="RiskDocument.xml">RiskDocument.xml</option>
        <option value="USC_01_Anomaly_push_02.xml">USC_01_Anomaly_push_02.xml</option>
        <option value="USC_01_AnomalyService_subscribe_01.xml">USC_01_AnomalyService_subscribe_01.xml</option>
        <option value="USC_01_PersonService_Pull_03.xml">USC_01_PersonService_Pull_03.xml</option>
        <option value="USC_01_PersonService_Pull_04.xml">USC_01_PersonService_Pull_04.xml</option>
        <option value="USC_01_VesselService_Cargo_Pull_05.xml">USC_01_VesselService_Cargo_Pull_05.xml</option>
        <option value="USC_01_VesselService_Cargo_Pull_06.xml">USC_01_VesselService_Cargo_Pull_06.xml</option>
        <option value="USC_02_LocationDocument_Pull_02.xml">USC_02_LocationDocument_Pull_02.xml</option>
        <option value="USC_02_LocationDocument_Pull_06.xml">USC_02_LocationDocument_Pull_06.xml</option>
        <option value="USC_02_MaritimeSafetyIncident_Push_01.xml">USC_02_MaritimeSafetyIncident_Push_01.xml</option>
        <option value="USC_02_OperationalAsset_Pull_04.xml">USC_02_OperationalAsset_Pull_04.xml</option>
        <option value="USC_02_OperationalAsset_Pull_07.xml">USC_02_OperationalAsset_Pull_07.xml</option>
        <option value="USC_02_Vessel_Pull_03.xml">USC_02_Vessel_Pull_03.xml</option>
        <option value="USC_02_Vessel_Pull_05.xml">USC_02_Vessel_Pull_05.xml</option>
        <option value="USC_03_ActionService_Push_06.xml">USC_03_ActionService_Push_06.xml</option>
        <option value="USC_03_IncidentService_Pull_03.xml">USC_03_IncidentService_Pull_03.xml</option>
        <option value="USC_03_IncidentService_Pull_04.xml">USC_03_IncidentService_Pull_04.xml</option>
        <option value="USC_03_RiskService_Push_05.xml">USC_03_RiskService_Push_05.xml</option>
        <option value="USC_03_VesselService_Location_Pull_01.xml">USC_03_VesselService_Location_Pull_01.xml</option>
        <option value="USC_03_VesselService_Location_Pull_02.xml">USC_03_VesselService_Location_Pull_02.xml</option>
        <option value="Vessel.xml">Vessel.xml</option>
        <option value="VesselDocument.xml">VesselDocument.xml</option>
        <option value="VesselMessage.xml">VesselMessage.xml</option>

        </Select>
        </FormControl>
        </Grid>
        <Grid item xs={6}>
        <formControl >
        <FormControlLabel    control={
        <Checkbox value="false" />
      }    label="With Async Acknowledgement :"        />
        </formControl>
        </Grid>
        <Grid item xs={6}>
        <Button style={btnStyles} onClick={this.sendMessage.bind(this)}>
          Send
        </Button>
        </Grid>
        </Grid>
        </FormGroup>
    );
  }

  handleKeyPress = event => {
    if (event.key === "Enter") {
      this.sendMessage();
    }
  };

  sendMessage() {
    const socket = Singleton.getInstance();
    let messageDto = JSON.stringify({
      user: this.props.thisUser,
      data: this.state.inputValue,
      type: MessageType.TEXT_MESSAGE
    });
    socket.send(messageDto);
    this.setState({ inputValue: "" });
  }

  updateInputValue(evt) {
    this.setState({
      inputValue: evt.target.value
    });
  }
}

// Whatever is returned is going to show up as props inside UserList
function mapStateToProps(state) {
  return {
    messages: state.messages,
    thisUser: state.thisUser,
    OptionX: state.OptionX
  };
}

// Promote component to container
export default connect(mapStateToProps)(SendMessage);
