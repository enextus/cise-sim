import DevTools from "mobx-react-devtools";
import React from "react";
import {observer} from "mobx-react";
import Panels from '../components/Panels/Panels';
import NavBar from '../components/NavBar/NavBar';
import WaitModal from '../components/WaitModal';

@observer
export default class MainApp extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        const includeModal = this.props.store.appStore.isModClosed ? '' : <WaitModal/>;
        const navbar = this.props.store.appStore.isModClosed ? <NavBar store={this.props.store}/> : '';
        const panels = this.props.store.appStore.isModClosed ? <Panels store={this.props.store}/> : '';

        return (

            <span style={{font: 'Liberation Sans'}}>
                <DevTools/>
                {includeModal}
                {navbar}
                {panels}
            </span>
        );
    }


}

