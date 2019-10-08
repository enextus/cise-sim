import {SnackbarContent} from "@material-ui/core";

export default class Notification extends SnackbarContent {
    key = "";
    constructor(props) {
        super(props);
        this.key = props.key;
    }
}