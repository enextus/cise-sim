/*
 * BSD 3-Clause License
 *
 * Copyright (c) 2021, Joint Research Centre (JRC) All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

import {Button, withStyles} from '@material-ui/core';
import DescriptionIcon from '@material-ui/icons/Description';
import React from 'react';
import PropTypes from 'prop-types';
import {observer} from 'mobx-react';
import {withSnackbar} from 'notistack';


const styles = theme => ({
    button: {
        margin: theme.spacing(1),
    },
    rightIcon: {
        marginLeft: theme.spacing(1),
    },
});

const action = (key) => (
    <Button onClick={() => {
        props.closeSnackbar(key)
    }}>
        {'Dismiss'}
    </Button>
)

@observer
class PreviewButton extends React.Component {

    isDisabled() {
        return !this.props.store.isTemplateSelected;
    }

    async preview() {
        const response = await this.props.store.preview();

        if (response.errorCode) {
            this.props.enqueueSnackbar(response.errorMessage, {
                variant: 'error',
                persist: true,
                action: (key) => (
                    <Button onClick={() => {
                        this.props.closeSnackbar(key)
                    }}>
                        {'Dismiss'}
                    </Button>
                ),
            })
        } else {
            this.props.enqueueSnackbar('New preview has been generated.', {variant: 'success',});
        }
    }

    render() {
        const {classes} = this.props;

        return (
            <Button id="preview"
                    variant="contained"
                    onClick={() => this.preview()}
                    color="primary"
                    className={classes.button}
                    disabled={this.isDisabled()}>
                Preview
                <DescriptionIcon className={classes.rightIcon}/>
            </Button>
        )
    }

}


PreviewButton.propTypes = {
    store: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(withSnackbar(PreviewButton))
