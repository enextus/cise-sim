export default class LabelVesselDto {

    typeList;
    roleList;

    constructor(props) {
        this.typeList = [...props.typeList];
        this.roleList = [...props.roleList];
    }
}