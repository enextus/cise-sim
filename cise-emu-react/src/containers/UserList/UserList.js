import React, { Component } from "react";
import {MoveToInbox} from "@material-ui/icons";
import {
  Button,
  AppBar,
  FormGroup,
  Switch,
  Toolbar,
  Typography,
  FormControlLabel
} from "@material-ui/core";
export default class UserList extends Component {
   myuser="";
   userList={}
  render() {
    this.myuser=this.props.store.appStore.memberId;
    if (this.myuser==="") {
      return ("");
    }
/*{this.renderUser()}*/
    return (
      <div style={{ textAlign: "right" }}>
        <AppBar title="Users" showMenuIconButton={false}>
          <Toolbar>
            <Typography variant="h6"> <Button>
              <MoveToInbox /> {this.props.store.appStore.memberId}
            </Button></Typography>
            <FormGroup>
              <FormControlLabel
                control={
                  <Switch
                    checked={this.props.store.appStore.connected}
                    onChange={handleChange}
                    aria-label="LoginSwitch"
                  />
                }
                label={this.props.store.appStore.connected ? "up" : "down"}
              />
            </FormGroup>
            <Button
              variant="contained"
              color={this.props.store.appStore.connected ? "primary" : "secondary"}
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
