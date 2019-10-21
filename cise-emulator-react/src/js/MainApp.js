import DevTools from "mobx-react-devtools";
import React from "react";
import {observer} from "mobx-react";
import Body from './components/Body';
import NavBar from './components/NavBar';
import TemplateStore from "./templates/TemplateStore";
import MessageStore from "./messages/MessageStore";
import ServiceStore from "./services/ServiceStore";
import {autorun} from "mobx";


const stores = {
    templateStore: new TemplateStore(),
    messageStore: new MessageStore(),
    serviceStore: new ServiceStore(),
};

autorun(() => {
    stores.serviceStore.loadServiceSelf();
    stores.messageStore.startPull();
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

