import React, {Component} from "react";
import {TextField} from "@material-ui/core";
import {observer} from "mobx-react";
import format from "xml-formatter";
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';
import {makeStyles} from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';

@observer
export default class PulledMessage extends Component {
    formattedXmlMessageContent;
    constructor(props) {
        super(props);
    }

    render() {
        const useStyles = makeStyles(theme => ({
            root: {
                width: '100%',
            },
            heading: {
                fontSize: theme.typography.pxToRem(15),
                fontWeight: theme.typography.fontWeightRegular,
            },
        }));

        const rootStyle = {
            width: "99%",
            alignContent: "center",
            fontFamily: 'Liberation Sans',
            font: 'Liberation'
        };
        const textfieldStyle = {
            width: "100%",
            borderLeft: `6px solid 4`,
            padding: `4px 4px`,
            fontFamily: 'Liberation Sans'
        };

        this.formattedXmlMessageContent = format(this.props.messageReceived.body);
        return (
            <div style={rootStyle}>
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
                            style={textfieldStyle}
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




