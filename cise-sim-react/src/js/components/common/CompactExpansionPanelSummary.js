import {withStyles} from "@material-ui/core/styles";
import {ExpansionPanelSummary} from "@material-ui/core";


const summaryStyles = {
    root: {
        minHeight: 7*4 ,
        "&$expanded": {
            minHeight: 0,
            margin:0,
        }
    },
    content: {
        margin: "4px 0",
        "&$expanded": {
            margin: "4px 0",
        },

    },
    expandIcon: {
        padding: 3
    },
    expanded: {}
};
export const CompactExpansionPanelSummary = withStyles(summaryStyles)(
    ExpansionPanelSummary
);
CompactExpansionPanelSummary.muiName = "ExpansionPanelSummary";

