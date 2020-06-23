import React, {Component} from "react";
import {withStyles} from "@material-ui/core/styles";
import {ExpansionPanel, ExpansionPanelDetails} from "@material-ui/core";
import MoreHorizRoundedIcon from '@material-ui/icons/MoreHorizRounded';
import XmlContent from "../../components/common/XmlContent";
import {fontSizeSmall} from "../../layouts/Font";
import {CompactExpansionPanelSummary} from "../../components/common/CompactExpansionPanelSummary";

const styles = theme => ({
    root: {
        width: "100%"
    },
    heading: {
        fontSize: theme.typography.pxToRem(15),
        fontWeight: theme.typography.fontWeightRegular
    },
});


class ExpansionPanelPreview extends Component {

    state = {
        isPreview: true,
        currentBody: ''
    }

    body;
    preview;

    constructor(props) {
        super(props);
        this.body = props.body;
        this.preview = props.body.split('\n').slice(0, props.numLines-1).join('\n');
        this.isPreview = true;
        this.state.currentBody = this.preview;
    }

    handleChange = (event, expanded) => {
        let newState = this.state.isPreview ?
                {isPreview:false, currentBody: this.body}:
                {isPreview:true,  currentBody: this.preview};

        this.setState(newState);
    }

    render() {
        const {classes} = this.props;


        return (
            <ExpansionPanel onChange={this.handleChange} elevation={0} id="ExpPannel" expanded style={{margin:0}}>

                <CompactExpansionPanelSummary id="ExpSummary" >
                    <MoreHorizRoundedIcon/>
                </CompactExpansionPanelSummary>

                <ExpansionPanelDetails id="ExpDetails" style={{fontSize:fontSizeSmall}}>
                    <XmlContent size='normal'>{this.state.currentBody}</XmlContent>
                </ExpansionPanelDetails>

            </ExpansionPanel>
        )
    }
}

export default withStyles(styles)(ExpansionPanelPreview);