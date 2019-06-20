import React from "react";
import { observer } from "mobx-react";

const Message = observer(({ message }) => (
  <li>
      finished ?
      <input
          type="checkbox"
          checked={message.finished}
          onChange={() => (message.finished = !message.finished)}
      />
      asyncAck ?
      <input
          type="checkbox"
          checked={message.asyncAcknowledge}
          onChange={() => (message.asyncAcknowledge= !message.asyncAcknowledge)}
      />
      Id  :  <span/>  {message.id}  <span/>    CorrelationId: <span/> {message.correlationId}   <span/> messageType  : <span/>  {message.messageType} <span/> templateService  : <span/> {message.templateService}<span/> templatePayload  : <span/> {message.templatePayload}
  </li>
));

export default Message;
