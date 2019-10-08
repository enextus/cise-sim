import React, {Component} from "react";
import {observer} from "mobx-react";
import Highlight from 'react-highlight.js';

@observer
export default class ShowXmlMessage extends React.Component {

    render() {

        return (
            <div hidden={!this.props.hidden} className={this.props.textfieldStyle}>
                <Highlight language={"xml"}> 
                {this.props.content}
                </Highlight>
            </div> 
        );
    }
}

