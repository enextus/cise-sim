import React, {Component} from "react";
import {withStyles} from "@material-ui/core/styles";
import {ExpansionPanel, ExpansionPanelDetails} from "@material-ui/core";
import XmlContent from "../../../components/common/XmlContent";
import {fontSizeNormal, xmlContentHeightSize} from "../../../layouts/Font";
import {CompactExpansionPanelSummary} from "../../../components/common/CompactExpansionPanelSummary";
import Typography from "@material-ui/core/Typography";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import AspectRatioRoundedIcon from '@material-ui/icons/AspectRatioRounded';

const styles = theme => ({
    root: {
        width: "100%"
    },
    heading: {
        fontSize: theme.typography.pxToRem(15),
        fontWeight: theme.typography.fontWeightRegular
    },
});


class ExpansionPanelPreview extends Component {

    state = {
        isPreview: true,
        currentBody: ''
    }

    body;
    preview;

    constructor(props) {
        super(props);
        this.body = props.body;
        this.preview = props.body.split('\n').slice(0, props.numLines-1).join('\n');
        this.isPreview = true;
        this.state.currentBody = this.preview;
    }

    handleChange = (event, expanded) => {
        let newState = this.state.isPreview ?
                {isPreview:false, currentBody: this.body}:
                {isPreview:true,  currentBody: this.preview};

        this.setState(newState);
    }

    render() {
        const {classes} = this.props;


        return (
            <ExpansionPanel onChange={this.handleChange} elevation={0} id="ExpPannel" expanded style={{margin:0}} >

                <CompactExpansionPanelSummary id="ExpSummary" expandIcon={<AspectRatioRoundedIcon/>}>

                    <TableContainer>
                        <Table size="small" aria-label="a dense table">
                            <TableBody>
                                <TableRow >
                                    <TableCell component="th" scope="row" style={{ borderBottom: 0, padding: 0, textAlign:"right"}}>
                                        <Typography style={{textAlign:'right'}}>Expand</Typography>
                                    </TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </TableContainer>

                </CompactExpansionPanelSummary>

                <ExpansionPanelDetails id="ExpDetails" style={{fontSize:fontSizeNormal}}>
                    <XmlContent size={xmlContentHeightSize}>{this.state.currentBody}</XmlContent>
                </ExpansionPanelDetails>

            </ExpansionPanel>
        )
    }
}

export default withStyles(styles)(ExpansionPanelPreview);