import React, {Component} from 'react';
import {observer} from 'mobx-react';
import {ExpansionPanel, ExpansionPanelDetails, Typography} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import PropTypes from 'prop-types';
import XmlContent from '../../components/common/XmlContent';
import {fontSizeNormal, fontSizeSmall, xmlContentHeightSize} from "../../layouts/Font";
import ExpansionPanelSummary from "@material-ui/core/ExpansionPanelSummary";

const styles = (theme) => ({
  root: {
  //  padding: theme.spacing(1)
  },
  title: {
    fontSize:fontSizeSmall
  },
  icon: {
    marginRight: "5px",
    color: "#6da0b3",
  },
});


@observer
class PreviewMessage extends Component {
  constructor(props) {
    super(props);
  }

  handleUpdate() {

  }

  render() {
    const {classes} = this.props;

    return (
          <ExpansionPanel expanded={!this.isTemplateEmpty()} elevation={0} >

            <ExpansionPanelSummary
              onClick={this.handleUpdate}
              aria-controls="previewMessageContent"
              id="previewMessage"
              style={{margin:0, padding:0}}
            >

              <Typography className={classes.title}><strong>Message Preview</strong></Typography>

            </ExpansionPanelSummary>

            <ExpansionPanelDetails style={{margin:0, padding:0,fontSize:fontSizeNormal, overflow:"auto"}}>
              <XmlContent size={xmlContentHeightSize}>
                {this.templateStore().template.errorCode === undefined ?
                  this.templateStore().template.content :
                    '<ErrorDetail>' + this.templateStore().template.errorMessage + '</ErrorDetail>'
                }
              </XmlContent>
            </ExpansionPanelDetails>
          </ExpansionPanel>
    )
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
