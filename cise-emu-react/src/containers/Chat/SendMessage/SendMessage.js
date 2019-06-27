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
import Singleton from "../../../transport/socket";
import MessageType from "./MessageType";
//init import { connect } from "react-redux";

export default class SendMessage extends Component {
  constructor(props) {
    super(props);

    this.state = {
      inputValue: "",
      xmlTemplate:"",
      payload:""
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
    label="Message Context Id"
    fullWidth={true}
    style={fieldStyle}
    placeholder="Id used to group actions"
    helperText="used to order execution to the simulator service"
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
    value={this.state.xmlTemplate}
        >
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
    value={this.state.payload}
        >
        <option value="Action.xml">Action</option>
        <option value="Agent.xml">Agent</option>
        <option value="Aircraft.xml">Aircraft</option>
        <option value="Anomaly.xml">Anomaly</option>
        <option value="AnomalyMessage.xml">AnomalyMessage</option>
        <option value="AnomalyMessage%5BCISE.ext%5D.xml">AnomalyMessage%5BCISE.ext%5D</option>
    <option value="Cargo.xml">Cargo</option>
        <option value="CargoDocument.xml">CargoDocument</option>
        <option value="CertificateDocument.xml">CertificateDocument</option>
        <option value="CrisisIncident.xml">CrisisIncident</option>
        <option value="Document.xml">Document</option>
        <option value="EventDocument.xml">EventDocument</option>
        <option value="FormalOrganization.xml">FormalOrganization</option>
        <option value="Incident.xml">Incident</option>
        <option value="IncidentMessage.xml">IncidentMessage</option>
        <option value="IrregularMigrationIncident.xml">IrregularMigrationIncident</option>
        <option value="LandVehicle.xml">LandVehicle</option>
        <option value="LawInfringementIncident.xml">LawInfringementIncident</option>
        <option value="Location.xml">Location</option>
        <option value="LocationDocument.xml">LocationDocument</option>
        <option value="LocationMessage.xml">LocationMessage</option>
        <option value="MaritimeSafetyIncident.xml">MaritimeSafetyIncident</option>
        <option value="MeteoOceanographicCondition.xml">MeteoOceanographicCondition</option>
        <option value="Movement.xml">Movement</option>
        <option value="OperationalAsset.xml">OperationalAsset</option>
        <option value="Organization.xml">Organization</option>
        <option value="OrganizationalCollaboration.xml">OrganizationalCollaboration</option>
        <option value="OrganizationalUnit.xml">OrganizationalUnit</option>
        <option value="OrganizationDocument.xml">OrganizationDocument</option>
        <option value="Person.xml">Person</option>
        <option value="PersonDocument.xml">PersonDocument</option>
        <option value="PersonMessage.xml">PersonMessage</option>
        <option value="PortOrganization.xml">PortOrganization</option>
        <option value="PRF_01_Vessel_Push_05.xml">PRF_01_Vessel_Push_05</option>
        <option value="PRF_01_Vessel_Push_06.xml">PRF_01_Vessel_Push_06</option>
        <option value="PRF_01_Vessel_Push_07.xml">PRF_01_Vessel_Push_07</option>
        <option value="PRF_01_Vessel_Push_08.xml">PRF_01_Vessel_Push_08</option>
        <option value="PRF_04_Vessel_Push_05.xml">PRF_04_Vessel_Push_05</option>
        <option value="Risk.xml">Risk</option>
        <option value="RiskDocument.xml">RiskDocument</option>
        <option value="USC_01_Anomaly_push_02.xml">USC_01_Anomaly_push_02</option>
        <option value="USC_01_AnomalyService_subscribe_01.xml">USC_01_AnomalyService_subscribe_01</option>
        <option value="USC_01_PersonService_Pull_03.xml">USC_01_PersonService_Pull_03</option>
        <option value="USC_01_PersonService_Pull_04.xml">USC_01_PersonService_Pull_04</option>
        <option value="USC_01_VesselService_Cargo_Pull_05.xml">USC_01_VesselService_Cargo_Pull_05</option>
        <option value="USC_01_VesselService_Cargo_Pull_06.xml">USC_01_VesselService_Cargo_Pull_06</option>
        <option value="USC_02_LocationDocument_Pull_02.xml">USC_02_LocationDocument_Pull_02</option>
        <option value="USC_02_LocationDocument_Pull_06.xml">USC_02_LocationDocument_Pull_06</option>
        <option value="USC_02_MaritimeSafetyIncident_Push_01.xml">USC_02_MaritimeSafetyIncident_Push_01</option>
        <option value="USC_02_OperationalAsset_Pull_04.xml">USC_02_OperationalAsset_Pull_04</option>
        <option value="USC_02_OperationalAsset_Pull_07.xml">USC_02_OperationalAsset_Pull_07</option>
        <option value="USC_02_Vessel_Pull_03.xml">USC_02_Vessel_Pull_03</option>
        <option value="USC_02_Vessel_Pull_05.xml">USC_02_Vessel_Pull_05</option>
        <option value="USC_03_ActionService_Push_06.xml">USC_03_ActionService_Push_06</option>
        <option value="USC_03_IncidentService_Pull_03.xml">USC_03_IncidentService_Pull_03</option>
        <option value="USC_03_IncidentService_Pull_04.xml">USC_03_IncidentService_Pull_04</option>
        <option value="USC_03_RiskService_Push_05.xml">USC_03_RiskService_Push_05</option>
        <option value="USC_03_VesselService_Location_Pull_01.xml">USC_03_VesselService_Location_Pull_01</option>
        <option value="USC_03_VesselService_Location_Pull_02.xml">USC_03_VesselService_Location_Pull_02</option>
        <option value="Vessel.xml">Vessel</option>
        <option value="VesselDocument.xml">VesselDocument</option>
        <option value="VesselMessage.xml">VesselMessage</option>

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
    const id=getId();
    let messageDto = JSON.stringify({
      user: this.props.thisUser,
      data: this.state.inputValue+"@"+id+"\n"+this.state.xmlTemplate+"#"+this.state.payload,
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
//init function mapStateToProps(state) {
//init return {
//init messages: state.messages,
//init thisUser: state.thisUser,
//init xmlTemplate: state.xmlTemplate,
//init payload: state.payload
//init };
//init }

export function getId() {
  let d = new Date().getTime()
  const uuid = 'xxxxx-xxxxyx'.replace(/[xy]/g, function(c) {
    const r = ((d + Math.random() * 16) % 16) | 0
    d = Math.floor(d / 16)
    return (c === 'x' ? r : (r & 0x3) | 0x8).toString(16)
  })
  return uuid
}

// Promote component to container
//init export default connect(mapStateToProps)(SendMessage);
