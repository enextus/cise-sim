import React, {Component} from 'react';
import {observer} from 'mobx-react';
import Highlight from 'react-highlight.js';
import PropTypes from 'prop-types';
import withStyles from '@material-ui/core/styles/withStyles';

const styles = (theme) => ({
  small: {
    maxHeight: '250px',
    overflow: 'auto',
    overflowX: 'visible'
  },
  normal: {
      maxHeight: '370px',
      overflow: 'auto',
      overflowX: 'visible'
  }
});

@observer
class XmlContent extends Component {

  constructor(props) {
    super(props);
  }

  render() {

    const {classes} = this.props;

    let styleType =classes.small;
    switch (this.props.size) {
      case 'small':
        styleType = classes.small;
        break;

      case 'normal':
        styleType = classes.normal;
        break;
    }

    return (
        <div hidden={this.noContent()}>
          <Highlight language={"xml"} className={styleType} >
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
  children: PropTypes.any.isRequired
};

export default withStyles(styles)(XmlContent)