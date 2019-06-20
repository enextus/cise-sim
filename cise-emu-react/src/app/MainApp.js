import DevTools from "mobx-react-devtools";
import React from "react";
import { observer } from "mobx-react";
import ReactDOM from 'react-dom';
import MuiThemeProvider from '@material-ui/core/styles/MuiThemeProvider';
import Singleton from '../transport/socket';
import Chat from '../containers/Chat/Chat';
import NavBar from '../containers/UserList/UserList';
import MessageType from '../containers/Chat/SendMessage/MessageType';
import messages from "../models/MessageListModel";
import MessageList from "../components/MessageList";

import WaitModal from '../components/WaitModal';
import MainAppModel from "../models/MainAppModel";

@observer
class MainApp extends React.Component {

    constructor(props) {
        super(props);
        //console.log(timerData);
    }

    render() {
        //console.log(timerData);
        //appStore
        //messageStore
        const includeModal = this.props.store.appStore.IsModalClosed ? '' : <WaitModal />
        const includeApp =  this.props.store.appStore.IsModalOpened? '' :   <div>sec passed: {this.props.store.appStore.TimerSinceWithoutConnected.secondsPassed}</div>
        const chat = this.props.store.appStore.IsModalOpened ? '' : <Chat store={this.props.store}/>
        const navbar = this.props.store.appStore.IsModalOpened ? '' : <NavBar store={this.props.store}/>
        return (
            <span>
                <DevTools />
                {includeModal}
                {includeApp}
                {navbar}
                {chat}
            </span>
        );
    }
}

export default MainApp;



/*

import MuiThemeProvider from '@material-ui/core/styles/MuiThemeProvider';
import DevTools from "mobx-react-devtools";
import Singleton from '../transport/socket';
import { render } from "react-dom";

//import UserList from '../containers/UserList/UserList';
import Chat from '../containers/Chat/Chat';


//import { userJoined, userJoinedAck, userLeft, messageReceived } from '../actions/index';
import  React  from 'react';
//import {Component} from 'react';

import messages from "../models/MessageListModel";

import { observer } from "mobx-react";
import MessageList from "../components/MessageList";
import appModel from "../models/MainAppModel";
import {observable} from "mobx";




@observer
class MainApp extends React.Component {

    @observable
    timerData = observable({
        secondsPassed: 0
    })


    store = {
        'messages':messages,
        'appModel':appModel
    };

    constructor(props) {
        super(props);
        console.log(this.store.appModel);
        console.log(this.store.messages)
    }



    render() {

        console.log(this.store.appModel);
        console.log(this.store.messages)
        let appStore = this.store.appModel;
         const chat = appStore.IsModalOpened ? '' : <Chat />

         const messageList = appStore.IsModalOpened ? '' : <MessageList store={messages} />

         const modal = appStore.IsModalClosed ? '' : <WaitModal />

        return (
            <MuiThemeProvider>
                <DevTools/>
                <div>Seconds passed: {this.props.timerData.secondsPassed} </div>
            </MuiThemeProvider>
        );
    }



<UserList users={this.users} />
{chat}
{test2}
{waitmodal}



    registerSocket() {
        let self = this;
        this.socket = Singleton.getInstance();

        this.socket.onmessage = (response) => {
            let message = JSON.parse(response.data);
            let users;

            switch (message.type) {
                case MessageType.TEXT_MESSAGE:
                    self.props.messageReceived(message);
                    break;
                case MessageType.USER_JOINED:
                    users = JSON.parse(message.data);
                    self.props.userJoined(users);
                    break;
                case MessageType.USER_LEFT:
                    users = JSON.parse(message.data);
                    self.props.userLeft(users);
                    break;
                case MessageType.USER_JOINED_ACK:
                    let thisUser = message.user;
                    self.props.userJoinedAck(thisUser);
                    break;
                default:
            }
        }

        this.socket.onopen = () => {
            this.sendJoinedMessage();
        }

        window.onbeforeunload = () => {
            let messageDto = JSON.stringify({ user: appStore.memberId, type: MessageType.USER_LEFT });
            this.socket.send(messageDto);
        }

    }

    sendJoinedMessage() {
        let messageDto = JSON.stringify({ user: appStore.memberId, type: MessageType.USER_JOINED });
        this.socket.send(messageDto);
    }

    ConnectAfterWait() {
        this.registerSocket();
        if (appStore.IsModalOpened) {
            appStore.closeModal() ;
        } ;
    }


}




var mainApp = new MainApp ();

setInterval(() => {
    mainApp.timerData.secondsPassed++
}, 1000)


export default MainApp;

// setTimeout(() => {
//     //do your thing
//     app.ConnectAfterWait;
// }, 1000);
//
// setTimeout(() => {
//     app.OptionService.push("newContent.xml");
//     app.OptionPayload.push("newContent.xml");
//     app.closeModal();
// }, 2500);

// all following is not mobx approach
/*function mapStateToProps(state) {
    return {
        messages: state.message,
        users: state.users,
        thisUser: state.thisUser
    }
}*/

/*function mapDispatchToProps(dispatch, props) {
    return bindActionCreators({
        userJoined: userJoined,
        userJoinedAck: userJoinedAck,
        userLeft: userLeft,
        messageReceived: messageReceived
    }, dispatch);
}*/

//export default connect(mapStateToProps, mapDispatchToProps)(App);