import React from 'react';
import { makeStyles, withStyles } from '@material-ui/core/styles';
import LinearProgress from '@material-ui/core/LinearProgress';

const ColorLinearProgress = withStyles({
    colorPrimary: {
        backgroundColor: '#b2dfdb',
    },
    barColorPrimary: {
        backgroundColor: '#00695c',
    },
})(LinearProgress);

const useStyles = makeStyles(theme => ({
    root: {
        flexGrow: 1,
    },
    margin: {
        margin: theme.spacing(1),
    },
}));

export default function CustomizedProgressBars() {
    const classes = useStyles();

    return (
        <div className={classes.root}>
            <ColorLinearProgress className={classes.margin} />
        </div>
    );
}
