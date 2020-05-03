import {Component} from 'react';
import {observer} from 'mobx-react';
import {
    Button,
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
import {withSnackbar} from 'notistack';
import Error from '../../errors/Error';
import XmlContent from '../../components/common/XmlContent';
import TabPanel from '../../components/common/TabPanel';
import EmailIcon from '@material-ui/icons/Email';


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
  }
});

@observer
class PulledMessage extends Component {

  lastReceivedSuccessCheckSum = 0;
  prevReceivedMessageError = new Error("", "");

  constructor(props) {
    super(props);
    this.state = {tabValue: "one"};
    this.handleChange = this.handleChange.bind(this);
  }

  handleChange(event, newValue) {
    this.setState({tabValue: newValue})
  };

  isDisabled() {
    return !this.props.store.templateStore.isTemplateSelected;
  }

  showSuccessMessage(event, newValue) {
    if (this.props.store.messageStore.receivedMessage &&
        this.props.store.messageStore.receivedMessage.checksum
        !== this.lastReceivedSuccessCheckSum) {
      this.props.enqueueSnackbar("Message was received", {
        variant: 'info',
        innerRef: instance => {
          this.props.store.messageStore.receivedMessage.checksum
        }
      });
    }
    this.lastReceivedSuccessCheckSum = this.props.store.messageStore.receivedMessage.checksum;
  };

  showErrorMessage(event, newValue) {
    if (this.props.store.messageStore.receivedMessageError) {

      if (this.props.store.messageStore.receivedMessageError.errorMessage !==
          this.prevReceivedMessageError.errorMessage) {

        this.prevReceivedMessageError = this.props.store.messageStore.receivedMessageError;
        this.props.enqueueSnackbar(
            this.props.store.messageStore.receivedMessageError.errorMessage, {
              variant: 'error',
              persist: true,
              action: (key) => (
                  <Button onClick={() => {
                    this.props.closeSnackbar(key)
                  }}>
                    {'Dismiss'}
                  </Button>
              ),
            })
      }
    }
  };

  render() {
    const {classes} = this.props;
    return (
        <div className={classes.root}>
          <ExpansionPanel
              expanded={this.getMessageStore().receivedMessage.body !== ""}>
            <ExpansionPanelSummary
                expandIcon={<ExpandMoreIcon/>}
                aria-controls="receiveMessageContent"
                id="ReceivedMessage">
              <EmailIcon className={classes.icon}/>

              <Typography className={classes.title}>message <b>received</b></Typography>
            </ExpansionPanelSummary>
            <ExpansionPanelDetails>
              <Tabs
                  value={this.state.tabValue}
                  onChange={this.handleChange}
                  aria-label="simple-tabpanel"
                  indicatorColor="primary"
                  textColor="primary">
                <Tab label="message"
                     id='simple-tab-1'
                     value="one"
                     aria-controls='simple-tabpanel-1'/>
                <Tab label="ack sent"
                     value="two"
                     id='simple-tab-2'
                     aria-controls='simple-tabpanel-2'/>
              </Tabs>
              <TabPanel value={this.state.tabValue} index="one">
                <XmlContent>
                  {this.getMessageStore().receivedMessage.body}
                </XmlContent>
              </TabPanel>
              <TabPanel value={this.state.tabValue} index="two">
                <XmlContent>
                  {this.getMessageStore().receivedMessage.acknowledge}
                </XmlContent>
              </TabPanel>
            </ExpansionPanelDetails>
            <Typography className={classes.hide}
                        onChange={this.showErrorMessage()}>{""
            + this.getMessageStore().receivedMessageError}</Typography>
            <Typography className={classes.hide}
                        onChange={this.showSuccessMessage()}>{""
            + this.getMessageStore().receivedMessage}</Typography>
          </ExpansionPanel>
        </div>
    )
  }

  getMessageStore() {
    return this.props.store.messageStore;
  }
}

PulledMessage.propTypes = {
  store: PropTypes.object.isRequired,
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(withSnackbar(PulledMessage));