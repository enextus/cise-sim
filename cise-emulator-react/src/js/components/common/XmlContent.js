import React from "react";
import {observer} from "mobx-react";
import Highlight from 'react-highlight.js';
import PropTypes from "prop-types";
import withStyles from "@material-ui/core/styles/withStyles";

const styles = (theme) => ({
  root: {
    maxHeight: '600px',
    overflow: 'auto',
  }
});

@observer
class XmlContent extends React.Component {

  render() {
    const {classes} = this.props;
    return (
        <div hidden={this.noContent()}>
          <Highlight language={"xml"} className={classes.root}>
            {this.getContent()}
          </Highlight>
        </div>);
  }

  noContent() {
    return this.getXmlContent() === undefined || this.getXmlContent() === "";
  }

  getContent() {
    return this.noContent() ? "<no-content/>" : this.getXmlContent();
  }

  getXmlContent() {
    return this.props.children;
  }
}

XmlContent.propTypes = {
  children: PropTypes.object.isRequired
};

export default withStyles(styles)(XmlContent)