export default class LabelIncidentType {

    type;
    subTypeList;

    constructor(props) {
        this.type = props.type;
        this.subTypeList = [...props.subTypeList];
    }
}