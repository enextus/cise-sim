import React, {Component} from "react";
import {observer} from "mobx-react";
import Highlight from 'react-highlight.js';

@observer
export default class ShowXmlMessage extends React.Component {

    contenuto = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
    hidden = false;
    textfieldStyle = "";
 
    constructor(content, hidden, textfieldStyle) {
        super(content);
        this.contenuto = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>";
        if (content !== "" ) {
            this.contenuto = content;
        }
        
        this.hidden = hidden;
        this.textfieldStyle = textfieldStyle;
    }

    render() {
        console.log(this.contenuto);
        return (
            <div hidden={!this.hidden} className={this.textfieldStyle}>
                <Highlight language={"xml"}> 
                {this.contenuto}
                </Highlight>
            </div> 
        );
    }
}

