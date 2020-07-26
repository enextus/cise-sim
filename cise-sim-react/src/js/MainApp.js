import React, {Component} from 'react';
import {observer} from 'mobx-react';
import Body from './containers/Body';
import NavBar from './components/NavBar';
import TemplateStore from './stores/templates/TemplateStore';
import MessageStore from './stores/messages/MessageStore';
import ServiceStore from './stores/services/ServiceStore';
import {autorun} from 'mobx';
import IncidentStore from "./forms/IncidentForm/IncidentStore";
import DiscoveryStore from "./forms/DiscoveryForm/DiscoveryStore";


const stores = {
    templateStore: new TemplateStore(),
    messageStore: new MessageStore(),
    serviceStore: new ServiceStore(),
    incidentStore: new IncidentStore(),
    discoveryStore: new DiscoveryStore(),

};

autorun(() => {
    stores.serviceStore.loadServiceSelf();
    stores.messageStore.startPullHistoryProgressive();
    stores.incidentStore.getLabels();
    stores.discoveryStore.getLabels();
});

@observer
export default class MainApp extends Component {

    constructor(props) {
        super(props);
    }

    render() {
        const bodyElt = document.querySelector("body");
        bodyElt.style.backgroundColor = "white";
        return (
            <>
                <NavBar store={stores}/>
                <Body store={stores}/>
            </>
        )
    }
}

