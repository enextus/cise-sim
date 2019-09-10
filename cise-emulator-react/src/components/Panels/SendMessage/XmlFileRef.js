import React, {Component, PropTypes} from 'react'
import {observer} from 'mobx-react'

@observer
export default class XmlFileRef extends Component {

  constructor(props) {
    super(props);

  }


  render() {
    const {parent, option} = this.props;
    return (
        <option onClick={this.onChange} value={option.value}> {option.label}  </option>
    );
  }

  onChange(event){
    console.log(event);
  }

}

