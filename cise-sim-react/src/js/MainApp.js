import React, {Component} from 'react';
import {observer} from 'mobx-react';
import Body from './containers/BodyThread';
import NavBar from './components/NavBar';
import TemplateStore from './stores/templates/TemplateStore';
import MessageStore from './stores/messages/MessageStore';
import ServiceStore from './stores/services/ServiceStore';
import {autorun} from 'mobx';


const stores = {
    templateStore: new TemplateStore(),
    messageStore: new MessageStore(),
    serviceStore: new ServiceStore(),
};

autorun(() => {
    stores.serviceStore.loadServiceSelf();
    stores.messageStore.startPullHistoryProgressive();
});

@observer
export default class MainApp extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <NavBar store={stores}/>
                <Body store={stores}/>
            </>
        )
    }
}

