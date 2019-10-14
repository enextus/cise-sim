import React, {Component} from "react";
import {observer} from "mobx-react";
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
import XmlContent from "../../components/common/XmlContent";
import TabPanel from "../../components/common/TabPanel";

const styles = (theme) => ({
  root: {
    padding: theme.spacing(1),
  },
  hide: {
    visibility: 'hidden',
  }
});

/*

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
  },
  hide: {
    visibility: 'hidden',
  }
});
*/

@observer
class PulledMessage extends Component {

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
    if (this.getMessageStore().receivedMessage) {
      this.props.enqueueSnackbar("New message has been received.", {
        variant: 'info'
      });
    }
  };

  showErrorMessage(event, newValue) {
    if (this.getMessageStore().receivedMessageError) {

      if (this.getMessageStore().receivedMessageError.errorMessage
          !== this.prevReceivedMessageError.errorMessage) {

        this.prevReceivedMessageError = this.getMessageStore().receivedMessageError;
        this.props.enqueueSnackbar(
            this.getMessageStore().receivedMessageError.errorMessage, {
              variant: 'error',
              persist: true,
              action: (key) => (
                  <Button onClick={() => {
                    this.props.closeSnackbar(key)
                  }}>
                    {'Dismiss'}
                  </Button>
              ),
            });
      }
    }
  };

  render() {
    const {classes} = this.props;

    return (
        <div className={classes.root}>
          <ExpansionPanel expanded={this.getMessageStore().receivedMessage.body !== ""}>
            <ExpansionPanelSummary
                expandIcon={<ExpandMoreIcon/>}
                aria-controls="receiveMessageContent"
                id="ReceivedMessage">
              <Typography variant="h6">Message Received</Typography>
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
                <Tab label="acknowledgement"
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
    );
  }

  getMessageStore() {
    return this.props.store.messageStore;
  }
}

PulledMessage.propTypes = {
  store: PropTypes.object.isRequired,
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(withSnackbar(PulledMessage))


