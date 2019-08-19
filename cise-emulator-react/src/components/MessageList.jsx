import React, { Component } from "react";
import { observable, action } from "mobx";
import { observer } from "mobx-react";

import Message from "./Message";

@observer
class MessageList extends React.Component {
    @observable ComMessCorrelationId = "";
    @observable ComMessTemplateService= "";
    @observable ComMessTemplatePayload = "";
    @observable ComMessAsyncAck= "";

  render() {
    return (
      <div>
        <form onSubmit={this.handleFormSubmit}>
          Correlation Id :
            <input
                type="text"
                value={this.ComMessCorrelationId}
                onChange={this.handleInputChange1}
            />
             template.xml:
            <input
                type="text"
                value={this.ComMessTemplateService}
                onChange={this.handleInputChange2}
            />
             payload.xml:
            <input
                type="text"
                value={this.ComMessTemplatePayload}
                onChange={this.handleInputChange3}
            />
            requiere acknowledge
            <input
                type="checkbox"
                checked={this.ComMessAsyncAck}
                onChange={this.handleInputChange4}
                />

          <button type="submit">Add</button>
        </form>
        <hr />
        <ul>
          {this.props.store.messages.map(message => (
            <Message message={message} key={message.id} />
          ))}
        </ul>
          Message to consume left: {this.props.store.messages.uncompletedMessageFlow}

      </div>
    );
  }

    @action
    handleInputChange1 = e => {
        this.ComMessCorrelationId = e.target.value;
    };
    @action
    handleInputChange2 = e => {
        this.ComMessTemplateService = e.target.value;
    };
    @action
    handleInputChange3 = e => {
        this.ComMessTemplatePayload = e.target.value;
    };
    @action
    handleInputChange4 = e => {
        this.ComMessAsyncAck = !this.ComMessAsyncAck;
    };

  @action
  handleFormSubmit = e => {
    this.props.store.addMessagefull(this.ComMessCorrelationId, this.ComMessTemplateService, this.ComMessTemplatePayload,  this.ComMessAsyncAck );
    //this.ComMessCorrelationId = "";
    e.preventDefault();
  };
}

export default MessageList;
