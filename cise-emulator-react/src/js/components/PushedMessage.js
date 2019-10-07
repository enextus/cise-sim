import React, {Component} from "react";
import {observer} from "mobx-react";
import {observable} from "mobx";
import format from "xml-formatter";
import {
    ExpansionPanel,
    ExpansionPanelDetails,
    ExpansionPanelSummary,
    Paper,
    Tab,
    Tabs,
    Typography
} from '@material-ui/core';
import {makeStyles, withStyles} from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Highlight from 'react-highlight.js';
import PropTypes from 'prop-types';
import ShowXmlMessage from "./common/ShowXmlMessage";

const pushedMessageStyle = makeStyles(theme => ({
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
class PushedMessage extends Component {

    constructor(props) {
        super(props);
    }

    @observable  tabPushState = {
        value: 1
    };

    handleChange = (event, newValue) => {
        this.tabPushState.value = newValue
    };

    render() {
        const {classes} = this.props;
        this.value = 0;
        console.log("Props for PushedMessage: ", this.props.store.templateStore);
        console.log("templateContent: ", this.props.store.templateStore.template);
        return (
            <div className={classes.root}>
                 <ExpansionPanel disabled={(this.props.store.templateStore.template.content === "")}>                        
                     <ExpansionPanelSummary
                            expandIcon={<ExpandMoreIcon/>}
                            aria-controls="SentMessagecontent"
                            id="SentMessage">
                        <Typography className={classes.pullPanel_tab_heading}>
                            Message Sent
                        </Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails className={classes.pullPanel_expDetail}>
                        <Paper className={classes.pullPanel_paper}>

                            <Tabs className={classes.pullPanel_tabs}
                                  value={this.tabPushState.value}
                                  onChange={this.handleChange}
                                  aria-label="simple-tabpanel"
                                  indicatorColor="primary"
                                  textColor="primary">
                                <Tab className={classes.pullPanel_tab}
                                     label="message"
                                     id='simple-tab-1'
                                     aria-controls='simple-tabpanel-1'
                                />
                                <Tab label="acknowledgement"
                                     id='simple-tab-2'
                                     aria-controls='simple-tabpanel-2'
                                />
                            </Tabs>

                            <ShowXmlMessage content = {this.props.store.templateStore.template.content} 
                                            hidden = {this.tabPushState.value === 1} 
                                            textfieldStyle = {classes.textfieldStyle} />
{/* 
                            <div hidden={(this.props.store.templateStore.template.content === "") || this.tabPushState.value === 1}
                                 className={classes.textfieldStyle}>
                                <Highlight language={"xml"}> 
                                    {format(
                                        this.props.store.templateStore.template.content,
                                        {stripComments: true})}
                                  </Highlight>
                            </div> 
 */}
                            <div hidden={(this.props.messagePreview.acknowledgeContent === "") || this.tabPushState.value === 0}
                                 className={classes.textfieldStyle}>
                                <Highlight language={"xml"}>
                                    {format(this.props.messagePreview.acknowledgeContent,
                                        {stripComments: true})}
                                </Highlight>
                            </div>

                        </Paper>
                    </ExpansionPanelDetails>
                </ExpansionPanel>
            </div>

        );
    }
}

PushedMessage.propTypes = {
    classes: PropTypes.object.isRequired
};

export default withStyles(pushedMessageStyle)(PushedMessage)



