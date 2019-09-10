import DevTools from "mobx-react-devtools";
import React from "react";
import { observer } from "mobx-react";
import ReactDOM from 'react-dom';
import Singleton from '../transport/socket';
import Panels from '../components/Panels/Panels';
import NavBar from '../components/NavBar/NavBar';
import WaitModal from '../components/WaitModal';
import MainAppModel from "../models/MainAppModel";

@observer
export default class MainApp extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        const includeModal = this.props.store.appStore.IsModalClosed ? '' : <WaitModal />;
        const navbar = this.props.store.appStore.IsModalOpened ? '' : <NavBar store={this.props.store}/>;
        const panels = this.props.store.appStore.IsModalOpened ? '' : <Panels store={this.props.store}/>;

         return (

            <span style={{font: 'Liberation Sans'}}>
                <DevTools />
                {includeModal}
                {navbar}
                {panels}
            </span>
        );
    }


}

