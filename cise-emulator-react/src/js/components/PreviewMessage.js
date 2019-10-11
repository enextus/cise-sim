import React, {Component} from "react";
import {observer} from "mobx-react";
import {observable} from "mobx";
import {
    ExpansionPanel,
    ExpansionPanelDetails,
    ExpansionPanelSummary,
    Paper,
    Typography
} from '@material-ui/core';
import {makeStyles, withStyles} from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Highlight from 'react-highlight.js';
import PropTypes from 'prop-types';
import ShowXmlMessage from "./common/ShowXmlMessage";

const previewMessageStyle = makeStyles(theme => ({
    root: {
        margin: 'auto',
        padding: '10px'
    },
    pullPanel_tab_heading: {
        fontSize: '16px'
    },
    pullPanel: {
        maxWidth: 1000,
        flexGrow: 1,
        flexBasis: '97.0%'
    },
    pullPanel_paper: {
        width: "100%",
        fontSize: '11px',
        minWidth: '300px',
    },
    pullPanel_tabs: {
        fontSize: '9px'
    },
    pullPanel_expDetail: {
        maxWidth: '1000px',
        flexGrow: 1,
        flexBasis: '97.0%'
    }, pullPanel_tab: {
        fontSize: '9px'
    },
    textfieldStyle: {
        width: "100%",
        borderLeft: `4px solid 2`,
        padding: `3px 4px`
    }
}));

@observer
class PreviewMessage extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        const {classes} = this.props;
        this.value = 0;
        return (
            <div className={classes.root}>
                 <ExpansionPanel disabled={(this.props.store.templateStore.template.content === "")}>                        
                     <ExpansionPanelSummary
                            expandIcon={<ExpandMoreIcon/>}
                            aria-controls="PreviewMessagecontent"
                            id="PreviewMessage">
                        <Typography className={classes.pullPanel_tab_heading}>
                            Preview
                        </Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails className={classes.pullPanel_expDetail}>
                        <Paper className={classes.pullPanel_paper}>
                            <ShowXmlMessage content = {this.props.store.templateStore.template.content} 
                                            hidden = 'false' 
                                            textfieldStyle = {classes.textfieldStyle} />
                        </Paper>
                    </ExpansionPanelDetails>
                </ExpansionPanel>
            </div>

        );
    }
}

PreviewMessage.propTypes = {
    classes: PropTypes.object.isRequired
};

export default withStyles(previewMessageStyle)(PreviewMessage)



