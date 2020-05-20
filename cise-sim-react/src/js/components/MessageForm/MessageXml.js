import React from 'react';

import {ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Typography, withStyles} from '@material-ui/core';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import EmailIcon from '@material-ui/icons/Email';
import XmlContent from "../common/XmlContent";


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


const messageXml = (props) => {

  const {classes} = props;

    return (
        <div className={classes.root}>
          <ExpansionPanel onClick={props.change}>
            <ExpansionPanelSummary
                expandIcon={<ExpandMoreIcon/>}
                aria-controls="receiveMessageContent"
                id="ReceivedMessage">

              <EmailIcon className={classes.icon}/>

              <Typography className={classes.title}>message <b>received</b></Typography>
            </ExpansionPanelSummary>
              {props.body != null ?
                <ExpansionPanelDetails>
                    <XmlContent>{props.body}</XmlContent>
                </ExpansionPanelDetails>
                  : null}
          </ExpansionPanel>
        </div>
    )
}

export default withStyles(styles)(messageXml);