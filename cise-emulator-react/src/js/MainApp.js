import DevTools from "mobx-react-devtools";
import React from "react";
import {observer} from "mobx-react";
import Panels from './components/Panels/Panels';
import NavBar from './components/NavBar/NavBar';

@observer
export default class MainApp extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <React.Fragment>
                <DevTools/>
                <NavBar store={this.props.store}/>
                <Panels store={this.props.store}/>
            </React.Fragment>
        );
    }


}

