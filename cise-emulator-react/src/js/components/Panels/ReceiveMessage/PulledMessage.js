import React, {Component} from "react";
import {TextField} from "@material-ui/core";
import {observer} from "mobx-react";
import format from "xml-formatter";
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';

import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

@observer
export default class PulledMessage extends Component {
    formattedXmlMessageContent;
    constructor(props) {
        super(props);
    }
    render() {
        this.formattedXmlMessageContent = format(this.props.messageReceived.body);
        return (
            <div >
                <ExpansionPanel disabled={this.formattedXmlMessageContent===""} >
                    <ExpansionPanelSummary
                        expandIcon={<ExpandMoreIcon/>}
                        aria-controls="panel2a-content"
                        id="ReceivedMessage"
                    >
                        <Typography>
                            Received Message(s) : {(this.formattedXmlMessageContent !== "")? this.props.messageReceived.counter + ", last since ":"none in"}  {this.props.messageReceived.timer * 3} seconds
                        </Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        <TextField
                            id="ReceivedMessageContent"
                            multiline
                            value={this.formattedXmlMessageContent}
                            margin="none"
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




