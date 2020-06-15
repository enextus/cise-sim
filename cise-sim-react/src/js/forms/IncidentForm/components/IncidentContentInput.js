import React, {Component} from "react";
import {Paper} from "@material-ui/core";
import {withStyles} from "@material-ui/core/styles";
import TableContainer from "@material-ui/core/TableContainer";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import IconButton from "@material-ui/core/IconButton";
import DescriptionRoundedIcon from '@material-ui/icons/DescriptionRounded';

const styles = theme => ({
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        padding: 16,
        margin: '16px auto',
        maxWidth: 800
    },

    button: {
        margin: theme.spacing(1),
    },

    rightIcon: {
        marginLeft: theme.spacing(1),
    },
});

class IncidentContentInput extends Component {

    state = {
        filename: undefined,
    }

    constructor(props) {
        super(props);
    }

    getIncidentStore() {
        return this.props.store.incidentStore
    };


    handleInput= (event) => {
        this.setState({filename:event.target.files[0].name})
        this.getIncidentStore().getContentInputArrayItem(this.props.id).name = event.target.files[0].name;

        const files = event.target.files;

        // Initialize an instance of the `FileReader`
        const reader = new FileReader();

        // Specify the handler for the `load` event
        reader.onload = (e) => {

            // data:image/png;base64,
            const comma = e.target.result.indexOf(",");
            const semicolon = e.target.result.indexOf(";");
            const colon = e.target.result.indexOf(":");

            this.getIncidentStore().getContentInputArrayItem(this.props.id).content   = e.target.result.substring(comma+1);
            this.getIncidentStore().getContentInputArrayItem(this.props.id).mediaType = e.target.result.substring(colon+1, semicolon);

        }

        // Read the file
        reader.readAsDataURL(files[0]);
    }

    render() {

        const {classes} = this.props;


        return (
            <TableContainer component={Paper} >
                <Table size="small" aria-label="a dense table">
                    <TableBody>
                        <TableRow>

                            <TableCell align={"left"}>
                                <label htmlFor={"icon-button-file"+this.props.id}>
                                    <IconButton color="primary" aria-label="upload picture" component="span">
                                        <DescriptionRoundedIcon />
                                    </IconButton>
                                    <input id={"icon-button-file"+this.props.id} type="file" style={{display:"none"}}  onChange={this.handleInput}/>
                                </label>
                                {this.state.filename}
                            </TableCell>

                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        )
    }
}

export default withStyles(styles)(IncidentContentInput);