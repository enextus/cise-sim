import React from "react";
import {observer} from "mobx-react";
import Highlight from 'react-highlight.js';

@observer
export default class ShowXmlMessage extends React.Component {

  render() {
    return (
        <div hidden={!this.props.hidden}>
          <Highlight language={"xml"}>
            {this.getContent()}
          </Highlight>
        </div>);
  }

  noContent() {
    return this.props.content === undefined || this.props.content === "";
  }

  getContent() {
    return this.noContent() ? "<no-content/>" : this.props.content;
  }
}

