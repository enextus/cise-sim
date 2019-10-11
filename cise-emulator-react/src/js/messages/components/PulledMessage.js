import React, {Component} from "react";
import {observer} from "mobx-react";
import {observable} from "mobx";
import format from "xml-formatter";
import {
    Button,
    ExpansionPanel,
    ExpansionPanelDetails,
    ExpansionPanelSummary,
    Paper,
    Tab,
    Tabs,
    Typography,
    withStyles
} from '@material-ui/core';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import Highlight from 'react-highlight.js';
import PropTypes from 'prop-types';
import {withSnackbar} from 'notistack';
import ShowXmlMessage from "../../components/common/ShowXmlMessage";

const styles = () => ({
    root: {
        flexGrow: 1,
        flexBasis: '99.0%'
    },
    pullPanel_tab_heading: {
        fontSize: '16px',
        flexGrow: 1,
        flexBasis: '98.0%'
    },
    pullPanel: {
        padding: 16,
        margin: 'auto',
        maxWidth: 1000,
        flexGrow: 1,
        flexBasis: '97.0%'
    },
    pullPanel_paper: {
        fontSize: '11px',
        width: "100%",
        minWidth: '300px'
    },
    pullPanel_tabs: {
        flexGrow: 1,
        fontSize: '9px',
        flexBasis: '95.0%'
    },
    pullPanel_tab: {},
    textfieldStyle: {
        width: "100%",
        borderLeft: `6px solid 4`,
        padding: `4px 6px`
    }
});

const action = (key) => (
    <Button onClick={() => {
        props.closeSnackbar(key)
    }}>
        {'Dismiss'}
    </Button>
); // ODOT: no used


@observer
class PulledMessage extends Component {
    constructor(props) {
        super(props);
    }

    @observable
    tabPullState = {
        value: 0
    };

    handleChange = (event, newValue) => {
        this.tabPullState.value = newValue
    };

    isReceivedMessageErrorUpdated() {
        return (this.props.store.messageStore.receivedMessageError!=null);
    }


    isDisabled() {
        return !this.props.store.templateStore.isTemplateSelected;
    }


    showErrorMessage = (event, newValue) => {
        if (this.props.store.messageStore.receivedMessageError){
            console.log("Error in PulledMessage", this.props.store.messageStore.receivedMessageError);
            this.props.enqueueSnackbar(this.props.store.messageStore.receivedMessageError.errorMessage, {
                variant: 'error',
                persist: true,
                action: (key) => (
                    <Button onClick={() => {
                        this.props.closeSnackbar(key)
                    }}>
                        {'Dismiss'}
                    </Button>
                ),
            });
            //this.props.store.messageStore.receivedMessageError= null;
        }
    }

    // async pullIntent(props) {
    //     if (!store) alert("no store");
    //     const response = await props.store.messageStore.pull();
    //
    //     if (!response) return;
    //
    //     if (response.errorCode) {
    //         this.props.enqueueSnackbar(response.errorMessage, {
    //             variant: 'error',
    //             persist: true,
    //             action: (key) => (
    //                 <Button onClick={() => {
    //                     this.props.closeSnackbar(key)
    //                 }}>
    //                     {'Dismiss'}
    //                 </Button>
    //             ),
    //         });
    //     } else if (response.status === 200) {
    //         this.props.enqueueSnackbar('New message has been received.', {variant: 'success',});
    //     }
    // }

    render() {
        const {classes} = this.props;
        if (this.props.store.messageStore.receivedMessageError !== null) {
            console.log("receivedError: ", this.props.store.messageStore.receivedMessageError);
        }

         return (
            <div className={classes.root}>

                <Typography onChange={this.showErrorMessage()}>{""+this.props.store.messageStore.receivedMessageError}</Typography>

                <ExpansionPanel
                    disabled={(this.props.store.messageStore.receivedMessage.body === "") 
                    && (this.props.store.messageStore.receivedMessage.acknowledge === "")}>
                    <ExpansionPanelSummary
                        expandIcon={<ExpandMoreIcon/>}
                        aria-controls="receiveMessagecontent"
                        id="ReceivedMessage">
                        <Typography className={classes.pullPanel_tab_heading}>
                            Message Received
                        </Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails className={classes.pullPanel_tabs}>
                        <Paper className={classes.pullPanel_paper}>

                            <Tabs className={classes.pullPanel_tabs}
                                  value={this.tabPullState.value}
                                  onChange={this.handleChange}
                                  aria-label="simple-tabpanel"
                                  indicatorColor="primary"
                                  textColor="primary">
                                <Tab className={classes.pullPanel_tab}
                                     label="message"
                                     id='simple-tab-1'
                                     aria-controls='simple-tabpanel-1'/>
                                <Tab label="acknowledgement"
                                     id='simple-tab-2'
                                     aria-controls='simple-tabpanel-2'/>
                            </Tabs>
                            <ShowXmlMessage content = {this.props.store.messageStore.receivedMessage.body} 
                                            hidden = {(this.tabPullState.value === 0 )||(this.props.store.messageStore.receivedMessage.body === "")} 
                                            textfieldStyle = {classes.textfieldStyle} />
                            <ShowXmlMessage content = {this.props.store.messageStore.receivedMessage.acknowledge} 
                                            hidden = {(this.tabPullState.value === 1)||((this.props.store.messageStore.receivedMessage.acknowledge === ""))} 
                                            textfieldStyle = {classes.textfieldStyle} />
                        </Paper>
                    </ExpansionPanelDetails>
                </ExpansionPanel>
            </div>
        );
    }
}

PulledMessage.propTypes = {
    store: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired
};

export default withStyles(styles)(withSnackbar(PulledMessage))


