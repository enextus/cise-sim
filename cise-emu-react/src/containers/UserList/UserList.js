import React, { Component } from "react";
import { connect } from "react-redux";
import InboxIcon from "@material-ui/icons/MoveToInbox";
import {
  Button,
  AppBar,
  FormGroup,
  Switch,
  Toolbar,
  Typography,
  FormControlLabel
} from "@material-ui/core";
class UserList extends Component {
  render() {
    if (!this.props.users || this.props.users.length < 1) {
      return "";
    }
    return (
      <div style={{ textAlign: "right" }}>
        <AppBar title="Users" showMenuIconButton={false}>
          <Toolbar>
            <Typography variant="h6">{this.renderUser()}</Typography>
            <FormGroup>
              <FormControlLabel
                control={
                  <Switch
                    checked={connect}
                    onChange={handleChange}
                    aria-label="LoginSwitch"
                  />
                }
                label={connect ? "up" : "down"}
              />
            </FormGroup>
            <Button
              variant="contained"
              color={connect ? "primary" : "secondary"}
            >Mode: REST</Button>
          </Toolbar>
        </AppBar>
      </div>
    );
  }

  renderUser() {
    return this.props.users.map(user => (
      <Button>
        <InboxIcon /> {user.name}
      </Button>
    ));
  }
}

function handleChange(event) {
  //this.props.connect= false;
}

function mapStateToProps(state) {
  return {
    users: state.users,
    connect: state.connect
  };
}

export default connect(mapStateToProps)(UserList);
