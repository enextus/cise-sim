import React, { Component } from "react";
import {MoveToInbox,RemoveFromQueue} from "@material-ui/icons";
import {
  Button,
  AppBar,
  FormGroup,
  Switch,
  Toolbar,
  Typography,
  FormControlLabel
} from "@material-ui/core";
import { observer } from "mobx-react";
@observer
export default class NavBar extends Component {
   member="";
   memberList={};

  constructor(props) {
    super(props);
  }


  render() {
    this.myuser=this.props.store.appStore.memberId;
    this.memberList=this.props.store.appStore.memberList;
    if (this.myuser==="") {
      return ("");
    }
/*{this.renderUser()}*/
    return (
      <div style={{ textAlign: "right", font: "Liberation Sans" }}>
        <AppBar title="Users" >
          <Toolbar>
            <Typography  variant="contained" style={{ textAlign: "right", font: "Liberation Sans", color: "white" }}> <Button style={{ textAlign: "right", font: "Liberation Sans", color: "white" }}>
              <MoveToInbox />: it.sim-egn
            </Button></Typography>

            <Button
              variant="contained"
              color={this.props.store.appStore.connected ? "secondary" : "primary"}
            >Mode: REST</Button>
          </Toolbar>
        </AppBar>
      </div>
    );
  }

  renderUser() {
    // todo put condition to return all except the user
    /*return this.userList.filter(user => (
      <Button>
        <MoveToInbox /> {user}
      </Button>
    ));*/
    return this.userList.map(user => (
        <Button>
          <MoveToInbox /> {user}
        </Button>
    ));
  }
}

function handleChange(event) {
  //this.props.connect= false;
}

//init function mapStateToProps(state) {
//init return {
//init users: state.users,
//init connect: state.connect
//init };
//init }

//init export default connect(mapStateToProps)(UserList);
