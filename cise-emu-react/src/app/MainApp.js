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
export default class MainApp extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        const includeModal = this.props.store.appStore.IsModalClosed ? '' : <WaitModal />
        const navbar = this.props.store.appStore.IsModalOpened ? '' : <NavBar store={this.props.store}/>
        const chat = this.props.store.appStore.IsModalOpened ? '' : <Chat store={this.props.store}/>
        const includeApp =  this.props.store.appStore.IsModalOpened? '' :   <div>sec : {this.props.store.appStore.TimerSinceWithoutConnected.secondsPassed}</div>
        return (
            <span>
                <DevTools />
                {includeModal}
                {navbar}
                {chat}
                {includeApp}
            </span>
        );
    }


}







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



