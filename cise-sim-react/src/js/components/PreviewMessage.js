import {Component} from 'react';
import {observer} from 'mobx-react';
import {ExpansionPanel, ExpansionPanelDetails, ExpansionPanelSummary, Typography} from '@material-ui/core';
import {withStyles} from '@material-ui/core/styles';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import PropTypes from 'prop-types';
import XmlContent from './common/XmlContent';
import DesciptionIcon from '@material-ui/icons/Description'

const styles = (theme) => ({
  root: {
    padding: theme.spacing(1)
  },
  title: {
    fontSize: "12pt",
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
        <div className={classes.root}>
          <ExpansionPanel expanded={!this.isTemplateEmpty()}>
            <ExpansionPanelSummary
                onClick={this.handleUpdate}
                expandIcon={<ExpandMoreIcon/>}
                aria-controls="previewMessageContent"
                id="previewMessage">
              <DesciptionIcon className={classes.icon}/>
              <Typography className={classes.title}>message <b>preview</b></Typography>
            </ExpansionPanelSummary>
            <ExpansionPanelDetails>
              <XmlContent>
                {this.templateStore().template.content}
              </XmlContent>
            </ExpansionPanelDetails>
          </ExpansionPanel>
        </div>

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
