import DevTools from "mobx-react-devtools";
import React from "react";
import {observer} from "mobx-react";
import Body from './components/Body';
import NavBar from './components/NavBar';
import AppStore from "./models/AppStore";
import TemplateStore from "./templates/TemplateStore";
import MessageStore from "./messages/MessageStore";
import ServiceStore from "./services/ServiceStore";
import {autorun} from "mobx";


const stores = {
    appStore: new AppStore(),
    templateStore: new TemplateStore(),
    messageStore: new MessageStore(),
    serviceStore: new ServiceStore(),
};

autorun(() => {
    stores.appStore.loadServiceId();
    stores.messageStore.startPull();
    stores.serviceStore.getServiceSelf();
});

@observer
export default class MainApp extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <>
                <DevTools/>
                <NavBar store={stores}/>
                <Body store={stores}/>
            </>
        );
    }
}

