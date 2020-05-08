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
import TabPanel from '../../components/common/TabPanel';
import EmailIcon from '@material-ui/icons/Email';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

import MesRender from "../HistoryMessageRender";

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
  };

  isDisabled() {
    return !this.props.store.templateStore.isTemplateSelected;
  }

  orderingHistoryMessage(msgList) {
      const orderedList = [...msgList];
      orderedList.sort(function(a,b) {return Date.parse(a.dateTime)-Date.parse(b.dateTime)})
      return orderedList;
  }

  render() {
    const {classes} = this.props;

    const msgRcv = this.orderingHistoryMessage(this.getMessageStore().historyMsgList);

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
                <Tab label="message"
                     id='simple-tab-1'
                     value="one"
                     aria-controls='simple-tabpanel-1'/>
              </Tabs>
              <TabPanel value={this.state.tabValue} index="one" >


                  <TableContainer component={Paper}>
                      <Table className={classes.table} size="small" aria-label="a dense table">
                          <TableHead>
                              <TableRow>
                                  <TableCell align="center">Date & Time&nbsp;</TableCell>
                                  <TableCell align="center">Message Type&nbsp;</TableCell>
                                  <TableCell align="center">Service Type&nbsp;</TableCell>
                                  <TableCell align="center">Direction&nbsp;</TableCell>
                              </TableRow>
                          </TableHead>
                          <TableBody>
                              {msgRcv.map((msg) => {
                                  return (
                                    <MesRender
                                        dateTime={msg.dateTime}
                                        messageType={msg.messageType}
                                        serviceType={msg.serviceType}
                                        isSent={msg.isSent}
                                        id={msg.id}
                                        key={msg.id}
                                    />
                              )})}
                          </TableBody>
                      </Table>
                  </TableContainer>


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

HistoryMessage.propTypes = {
  store: PropTypes.object.isRequired,
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(HistoryMessage);