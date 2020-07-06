import React, {Component} from 'react';
import {observer} from 'mobx-react';
import {
    ExpansionPanel,
    ExpansionPanelDetails,
    ExpansionPanelSummary,
    Tab,
    Tabs,
    Typography,
    withStyles
} from '@material-ui/core';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import PropTypes from 'prop-types';
import TabPanel from './common/TabPanel';
import EmailIcon from '@material-ui/icons/Email';

import MessageTable from './messages/MessageInfoTable'


const styles = (theme) => ({
    root: {
        padding: theme.spacing(1),
    },
    title: {
        fontSize: "12pt",
    },
    icon: {
        marginRight: "5px",
        color: "#f7931e",
    },
    hide: {
        visibility: 'hidden',
    },
    table: {
        minWidth: 250,
        maxWidth: 650
    }
});

@observer
class HistoryMessage extends Component {

    constructor(props) {
        super(props);
        this.state = {tabValue: "one"};
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event, newValue) {
        this.setState({tabValue: newValue})
        console.log("newValue "+newValue);
    };

    render() {
        const {classes} = this.props;

        const msgRcv = this.getMessageStore().historyMsgList;
        const maxShow = this.getServiceStore().serviceSelf.messageHistoryMaxLength;
        this.getMessageStore().setThreadMaxCapacity(maxShow);

        return (
            <div className={classes.root}>
                <ExpansionPanel
                    expanded={this.getMessageStore().historyMsgList.length}>

                    <ExpansionPanelSummary
                        expandIcon={<ExpandMoreIcon/>}
                        aria-controls="receiveMessageContent"
                        id="HistoryMessage">
                        <EmailIcon className={classes.icon}/>
                        <Typography className={classes.title}>Message <b>History</b></Typography>
                    </ExpansionPanelSummary>

                    <ExpansionPanelDetails>
                        <Tabs
                            value={this.state.tabValue}
                            onChange={this.handleChange}
                            aria-label="simple-tabpanel"
                            indicatorColor="primary"
                            textColor="primary">

                            <Tab label="message 1"
                                 id='simple-tab-1'
                                 value="one"
                                 aria-controls='simple-tabpanel-1'>
                            </Tab>
                        </Tabs>

                        <TabPanel value={this.state.tabValue} index="one" >
                            <MessageTable msgRcv={msgRcv} messageStore={this.getMessageStore()}/>
                        </TabPanel>

                    </ExpansionPanelDetails>
                </ExpansionPanel>
            </div>
        )
    }

    getMessageStore() {
        return this.props.store.messageStore;
    }

   getServiceStore() {
        return this.props.store.serviceStore;
    }

}

HistoryMessage.propTypes = {
  store: PropTypes.object.isRequired,
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(HistoryMessage);