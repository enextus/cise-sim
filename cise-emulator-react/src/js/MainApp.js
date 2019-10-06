import DevTools from "mobx-react-devtools";
import React from "react";
import {observer} from "mobx-react";
import Body from './components/Body';
import NavBar from './components/NavBar';
import AppStore from "./models/AppStore";
import TemplateStore from "./templates/TemplateStore";
import MessageStore from "./models/MessageStore";
import {autorun} from "mobx";

const stores = {
    appStore: new AppStore(),
    templateStore: new TemplateStore(),
    messageStore: new MessageStore()
};

autorun(() => {
    stores.appStore.loadServiceId();
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

