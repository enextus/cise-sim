import React, { Component } from 'react';

export default class ChatHistory extends Component {

    render() {
        const style = {
            backgroundColor: '#eaeaea',
            padding: 1,
            height: '100%',
            overflowY: 'scroll',
            display: 'flex',
            flexDirection: 'column'
        };
        //appStore
        //messageStore
        const msgs = this.props.store.messageStore.messages.map((message, i) =>
            this.renderMessages(message, i)
        );

        return (
            <div style={style}>
                {msgs}
            </div>
        )
    }

    renderMessages(message, i) {
        const style = {
            display: 'block',
            margin: '5px 0'
        };

        const isMe = this.props.store.appStore.memberId === message.source;
        const floatDirection = isMe ? 'right' : 'left'
        const nameColor = isMe ? 'green' : 'red';
        const margin = isMe ? ' 0 0 0 40px' : '0 40px 0 0 ';

        const textStyle = {
            float: floatDirection,
            backgroundColor: '#fff',
            padding: '6px 10px',
            borderRadius: '15px',
            margin: margin,
            textAlign: 'left'
        }

        const nameStyle = {
            color: nameColor,
            float: floatDirection
        }

        return (
            <div key={i} style={style}>
                <span style={textStyle}>
                    <span style={nameStyle}>{message.source}</span>{" - "}
                    <span> {message.destination}</span>
                    <br />
                    {message.correlationId}
                </span>
            </div>
        );
    }
}


// Whatever is returned is going to show up as props inside UserList
//init function mapStateToProps(state) {
//init  return {
//init   messages: state.messages,
//init   thisUser: state.thisUser
//init  }
//init }

// Promote component to container
//init  export default connect(mapStateToProps)(ChatHistory);



