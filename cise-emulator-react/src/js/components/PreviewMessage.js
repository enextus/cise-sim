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
import XmlContent from "./common/XmlContent";

const styles = (theme) => ({
  root: {
    padding: theme.spacing(1)
  },
});

@observer
class PreviewMessage extends Component {

  constructor(props) {
    super(props);
  }

  render() {
    const {classes} = this.props;
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
              <XmlContent>
                {this.templateStore().template.content}
              </XmlContent>
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