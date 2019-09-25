import React, {Component} from "react";
import {Grid, TextField} from "@material-ui/core";
import {observer} from "mobx-react";
import format from "xml-formatter";
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
@observer
export default class PushedMessage extends Component {
    messagePreview;
    formattedXmlpreviewContent;
    formattedXmlAcknowledgeContent;
    constructor(props) {
        super(props);
        console.log("messagePreview : " + props.messagePreview.previewContent + "/" + props.messagePreview.acknowledgeContent + "/" + props.messagePreview.errorContent);
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
        function SimpleExpansionPanel() {
            const classes = useStyles();
        }

        const rootStyle = {
            width: "99%",
            alignContent: "center",
            fontFamily: 'Liberation Sans',
            font: 'Liberation'
        };
        const textfieldStyle = {
            width: "100%",
            borderLeft: `6px solid 4`,
            padding: `4px 6px`,
            fontFamily: 'Liberation Sans'
        };
        this.formattedXmlpreviewContent = format(this.props.messagePreview.previewContent);
        this.formattedXmlAcknowledgeContent = format(this.props.messagePreview.acknowledgeContent);
        return (
            <div style={rootStyle}>
                <ExpansionPanel disabled={this.formattedXmlAcknowledgeContent===""}>
                <ExpansionPanelSummary
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel2a-content"
                id="SentMessageAcknowledge"
                >
                <Typography>
                    Sent Message (Acknowledgement):
                </Typography>
                </ExpansionPanelSummary>
                <ExpansionPanelDetails>
                    <TextField
                        id="acknowledgeContent"
                        style={textfieldStyle}
                        multiline
                        value={this.formattedXmlAcknowledgeContent}
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




