import React, {Component} from "react";
import {ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, TextField, Typography} from "@material-ui/core";
import {ExpandMore} from "@material-ui/icons";
import {observer} from "mobx-react";
import format from "xml-formatter";

@observer
export default class ReceiveMessage extends Component {
    messagePreview;
    formattedXmlpreviewContent;
    formattedXmlAcknowledgeContent;

    constructor(props) {
        super(props);
        //{appStore,messageStore} props.store;
        //messageCandidate
        //messagePreview
        console.log("messagePreview : " + props.messagePreview.previewContent + "/" + props.messagePreview.acknowledgeContent + "/" + props.messagePreview.errorContent);
        console.log("messageCandidate : " + props.messageCandidate.messageState + "/" + props.messageCandidate.messageType);
    }

    render() {


        const rootStyle = {
            width: "100%",
            fontFamily: 'Liberation Sans',
            font: 'Liberation'
        };
        const headingStyle = {
            fontSize: "18px",
            margin: "5px auto",
            backgroundColor: "#cdcdcd"
        };
        const chipStyle = {
            verticalAlign: "bottom",
            height: 20,
            width: 20
        };
        const detailsStyle = {
            alignItems: "center"
        };
        const subdetailsStyle = {
            alignItems: "center"
        };

        const contentStyle = {
            flexBasis: "93.33%"
        };
        const textfieldStyle = {
            width: "100%",
            borderLeft: `4px solid 2`,
            padding: `2px 4px`,
            fontFamily: 'Liberation Sans'
        };
        this.formattedXmlpreviewContent = format(this.props.messagePreview.previewContent);
        this.formattedXmlAcknowledgeContent = format(this.props.messagePreview.acknowledgeContent);
        return (
            <div style={rootStyle}>
                <ExpansionPanel   defaultExpanded={this.props.messagePreview.status == "preview"} >
                    <ExpansionPanelSummary >
                        Received ({""}) <ExpandMore />
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails style={subdetailsStyle}>
                        <TextField
                            id="previewContent"
                            multiline
                            value={""}
                            margin="none"
                            variant="filled"
                            InputProps={{
                                readOnly: true
                            }}
                        />

                    </ExpansionPanelDetails>
                </ExpansionPanel>
                <ExpansionPanel defaultExpanded={this.props.messagePreview.status!="SUCCESS"}>
                    <ExpansionPanelSummary >
                        Message response (acknowledgement contents) <ExpandMore />
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails style={subdetailsStyle} >
                        <TextField
                            id="acknowledgeContent"
                            multiline
                            value={""}
                            margin="normal"
                            variant="filled"
                            InputProps={{
                                readOnly: true
                            }}
                        />
                    </ExpansionPanelDetails>
                </ExpansionPanel>
            </div>
        );
    }


}




