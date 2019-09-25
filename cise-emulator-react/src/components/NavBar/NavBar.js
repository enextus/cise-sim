import React, {Component} from "react";
import {AppBar, Button, Toolbar, Typography} from "@material-ui/core";
import {observer} from "mobx-react";
import DirectionsBoatIcon from '@material-ui/icons/DirectionsBoat';

@observer
export default class NavBar extends Component {
    member = "";
    memberList = {};

    constructor(props) {
        super(props);
    }

    render() {
        this.myuser = this.props.store.appStore.memberId;
        this.memberList = this.props.store.appStore.memberList;
        if (this.myuser === "") {
            return ("");
        }

        return (
            <AppBar>
                <Toolbar>
                    <Typography variant="h5" type="title" color="inherit" style={{flex: 1, fontWeight: "bold"}}>
                        <DirectionsBoatIcon/>&nbsp;
                        CISE Emu
                    </Typography>
                    <div>
                        <Button
                            variant="contained"
                            disabled={!this.isConnected()}
                            color="secondary">

                            Mode: REST
                        </Button>
                    </div>
                </Toolbar>
            </AppBar>
        );
    }

    isConnected() {
        return this.props.store.appStore.connected;
    }
}
