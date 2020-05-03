import {Component} from 'react';
import {observer} from 'mobx-react';
import {ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Tab, Tabs, Typography} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import PropTypes from 'prop-types';
import XmlContent from './common/XmlContent';
import TabPanel from './common/TabPanel';
import SendIcon from '@material-ui/icons/Send'

const style = (theme) => ({
    root: {
        padding: theme.spacing(1),
    },
    title: {
        fontSize: "12pt",
    },
    icon: {
        marginRight: "5px",
        color: theme.palette.secondary.main,
    },
});

@observer
class PushedMessage extends Component {

    constructor(props) {
        super(props);
        this.state = {tabValue: 'one'};
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event, newValue) {
        this.setState({tabValue: newValue})
    };

    render() {
        const {classes} = this.props;
        return (
            <div className={classes.root}>
                <ExpansionPanel expanded={this.getMessageStore().sentMessage.body !== ""}>
                    <ExpansionPanelSummary
                        expandIcon={<ExpandMoreIcon/>}
                        aria-controls="SentMessageContent"
                        id="SentMessage">
                        <SendIcon className={classes.icon} />
                        <Typography className={classes.title}>message <b>sent</b></Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        <Tabs
                            value={this.state.tabValue}
                            onChange={this.handleChange}
                            aria-label="simple-tabpanel"
                            indicatorColor="primary"
                            textColor="primary">
                            <Tab value="one"
                                 label="message"
                                 id='simple-tab-2'
                                 aria-controls='simple-tabpanel-2'/>
                            <Tab value="two"
                                 label="ack received"
                                 id='simple-tab-3'
                                 aria-controls='simple-tabpanel-3'/>
                        </Tabs>
                        <TabPanel index="one" value={this.state.tabValue}>
                            <XmlContent>
                                {this.getMessageStore().sentMessage.body}
                            </XmlContent>
                        </TabPanel>
                        <TabPanel index="two" value={this.state.tabValue}>
                            <XmlContent>
                                {this.getMessageStore().sentMessage.acknowledge}
                            </XmlContent>
                        </TabPanel>
                    </ExpansionPanelDetails>
                </ExpansionPanel>
            </div>
        )
    }

    getMessageStore() {
        return this.props.store.messageStore;
    }
}

PushedMessage.propTypes = {
    classes: PropTypes.object.isRequired
};

export default withStyles(style)(PushedMessage)



