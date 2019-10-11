import React, {Component} from "react";
import {observer} from "mobx-react";
import {
  ExpansionPanel,
  ExpansionPanelDetails,
  ExpansionPanelSummary,
  Typography
} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import PropTypes from 'prop-types';
import ShowXmlMessage from "./common/ShowXmlMessage";

const styles = () => ({
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
});

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
          <ExpansionPanel expanded={!this.isTemplateEmpty()}>
            <ExpansionPanelSummary
                expandIcon={<ExpandMoreIcon/>}
                aria-controls="previewMessageContent"
                id="previewMessage">
              <Typography variant={"h6"}>Message Preview</Typography>
            </ExpansionPanelSummary>
            <ExpansionPanelDetails>
              <ShowXmlMessage
                  content={this.templateStore().template.content}
                              hidden='false'/>
            </ExpansionPanelDetails>
          </ExpansionPanel>
        </div>

    );
  }

  isTemplateEmpty() {
    return this.templateStore().template.content === "";
  }

  templateStore() {
    return this.props.store.templateStore;
  }
}

PreviewMessage.propTypes = {
  classes: PropTypes.object.isRequired
};

export default withStyles(styles)(PreviewMessage);