import React, {Component} from 'react';

export default class History extends Component {

    constructor(props) {
        super(props);

    }

    render() {
        const style = {
            backgroundColor: '#e9e9e9',
            padding: 1,
            height: '100%',
            overflowY: 'scroll',
            display: 'flex',
            flexDirection: 'column',
            font: 'Liberation Sans',
            fontFamily: 'Liberation Sans'
        };
        const msgs = this.props.store.messageStore.messages.map((message, i) =>
            this.renderMessages(message, i)
        );

        const lastMsg = this.props.store.messageStore.currentMessage;

        return (
            <table style={style}>
                <tbody>
                <tr>
                    <td>{"  -----------------  "}</td>
                </tr>
                {msgs}

                <tr>
                    <td>{"  -----------------  "}</td>
                </tr>
                <tr>
                    <td>{lastMsg.source}{"  -  "}{lastMsg.destination}</td>
                </tr>
                <tr>
                    <td>{lastMsg.correlationId}</td>
                </tr>
                </tbody>
            </table>
        )
    }

    renderMessages(message, i) {
        const style = {
            display: 'block',
            margin: '5px 0'
        };

        const isMe = (this.props.store.appStore.memberId === message.source);
        const floatDirection = isMe ? 'right' : 'left';
        const nameColor = isMe ? 'green' : 'red';
        const margin = isMe ? ' 0 0 0 40px' : '0 40px 0 0 ';

        const textStyle = {
            float: floatDirection,
            backgroundColor: '#fff',
            padding: '6px 10px',
            borderRadius: '15px',
            margin: margin,
            textAlign: 'left',
            font: 'Liberation Sans',
            fontFamily: 'Liberation Sans'
        };

        const nameStyle = {
            color: nameColor,
            float: floatDirection,
            font: 'Liberation Sans',
            fontFamily: 'Liberation Sans'
        };

        return (
            <tr>
                <td>

                    <div key={i} style={style}>
                <span style={textStyle}>
                    <span style={nameStyle}>{message.source}</span>{" - "}
                    <span> {message.destination}</span>
                    <br/>
                    {message.correlationId}
                </span>
                    </div>
                </td>
            </tr>
        );
    }
}


