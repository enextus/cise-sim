import React, {Component} from "react";
import {MoveToInbox} from "@material-ui/icons";
import {AppBar, Button, Toolbar, Typography} from "@material-ui/core";
import {observer} from "mobx-react";

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
            <div style={{textAlign: "right", font: "Liberation Sans"}}>
                <AppBar title="Users">
                    <Toolbar>

                        <Typography
                            style={{textAlign: "right", font: "Liberation Sans", color: "white"}}>
                            <Button
                                style={{textAlign: "right", font: "Liberation Sans", color: "white"}}>
                                <MoveToInbox/>: {this.props.store.appStore.memberId}
                            </Button>
                        </Typography>

                        <Button variant="contained"
                                color={this.props.store.appStore.connected ? "secondary" : "primary"}>
                            Mode: REST
                        </Button>

                    </Toolbar>
                </AppBar>
            </div>
        );
    }

    renderUser() {
        // todo put condition to return all except the user
        return this.userList.map(user => (
            <Button>
                <MoveToInbox/> {user}
            </Button>
        ));
    }
}

function handleChange(event) {
    //this.props.connect= false;
}

