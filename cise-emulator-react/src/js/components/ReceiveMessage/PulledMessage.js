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
import {withStyles} from '@material-ui/core';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Highlight from 'react-highlight.js';
import PropTypes from 'prop-types';

const styles = () => ({
    root: {
        flexGrow: 1,
        flexBasis: '99.0%'
    },
    pullPanel_tab_heading: {
        fontSize: '16px',
        flexGrow: 1,
        flexBasis: '98.0%'
    },
    pullPanel: {
        padding: 16,
        margin: 'auto',
        maxWidth: 1000,
        flexGrow: 1,
        flexBasis: '97.0%'
    },
    pullPanel_paper: {
        fontSize: '11px',
        width: "100%",
        minWidth: '300px'
    },
    pullPanel_tabs: {
        flexGrow: 1,
        fontSize: '9px',
        flexBasis: '95.0%'
    },
    pullPanel_tab: {},
    textfieldStyle: {
        width: "100%",
        borderLeft: `6px solid 4`,
        padding: `4px 6px`
    }
});

@observer
class PulledMessage extends Component {
    constructor(props) {
        super(props);
    }

    @observable
    tabPullState = {
        value: 0
    };

    handleChange = (event, newValue) => {
        this.tabPullState.value = newValue
    };

    render() {
        const {classes} = this.props;
        return (
            <div className={classes.root}>
                <ExpansionPanel
                    disabled={(this.props.messageReceived.body === "") && (this.props.messageReceived.acknowledgement === "")}>
                    <ExpansionPanelSummary
                        expandIcon={<ExpandMoreIcon/>}
                        aria-controls="receiveMessagecontent"
                        id="ReceivedMessage">
                        <Typography className={classes.pullPanel_tab_heading}>
                            Message Received
                        </Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails className={classes.pullPanel_tabs}>
                        <Paper className={classes.pullPanel_paper}>

                            <Tabs className={classes.pullPanel_tabs}
                                  value={this.tabPullState.value}
                                  onChange={this.handleChange}
                                  aria-label="simple-tabpanel"
                                  indicatorColor="primary"
                                  textColor="primary">
                                <Tab className={classes.pullPanel_tab}
                                     label="message"
                                     id='simple-tab-1'
                                     aria-controls='simple-tabpanel-1'/>
                                <Tab label="acknowledgement"
                                     id='simple-tab-2'
                                     aria-controls='simple-tabpanel-2'/>
                            </Tabs>

                            <div hidden={this.props.messageReceived.body === "" || this.tabPullState.value === 1}
                                 className={classes.textfieldStyle}>
                                <Highlight language={"xml"}>
                                    {format(this.props.messageReceived.body, {stripComments: true, collapseContent: true})}
                                </Highlight>
                            </div>
                            <div
                                hidden={this.props.messageReceived.acknowledgement === "" || this.tabPullState.value === 0}
                                className={classes.textfieldStyle}>
                                <Highlight language={"xml"}>
                                    {format(this.props.messageReceived.acknowledgement, {stripComments: true, collapseContent: true})}
                                </Highlight>
                            </div>
                        </Paper>
                    </ExpansionPanelDetails>
                </ExpansionPanel>
            </div>
        );
    }
}

PulledMessage.propTypes = {
    classes: PropTypes.object.isRequired
};

export default withStyles(styles)(PulledMessage)



