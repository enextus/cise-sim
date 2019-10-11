import React, {Component} from "react";
import {observer} from "mobx-react";
import {observable} from "mobx";
import {
  ExpansionPanel,
  ExpansionPanelDetails,
  ExpansionPanelSummary,
  Tab,
  Tabs,
  Typography
} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import PropTypes from 'prop-types';
import XmlContent from "./common/XmlContent";

const style = (theme) => ({
  root: {
    padding: theme.spacing(1),
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
});

@observer
class PushedMessage extends Component {

  constructor(props) {
    super(props);
  }

  @observable  tabPushState = {
    value: 0
  };

  handleChange = (event, newValue) => {
    this.tabPushState.value = newValue
  };

  render() {
    const {classes} = this.props;
    this.value = 0;
    return (
        <div className={classes.root}>
          <ExpansionPanel>
            <ExpansionPanelSummary
                expandIcon={<ExpandMoreIcon/>}
                aria-controls="SentMessageContent"
                id="SentMessage">
              <Typography variant={"h6"}>Message Sent</Typography>
            </ExpansionPanelSummary>
            <ExpansionPanelDetails className={classes.pullPanel_expDetail}>
              <Tabs
                    value={this.tabPushState.value}
                    onChange={this.handleChange}
                    aria-label="simple-tabpanel"
                    indicatorColor="primary"
                    textColor="primary">
                <Tab
                     label="message"
                     id='simple-tab-2'
                     aria-controls='simple-tabpanel-2'/>
                <Tab label="acknowledgement"
                     id='simple-tab-3'
                     aria-controls='simple-tabpanel-3'/>
              </Tabs>
              <XmlContent>
                {this.props.store.messageStore.sentMessage.body}
              </XmlContent>

              <XmlContent>
                  {this.props.store.messageStore.sentMessage.acknowledge}
              </XmlContent>
            </ExpansionPanelDetails>
          </ExpansionPanel>
        </div>
    );
  }
}

PushedMessage.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(style)(PushedMessage)



