export default class LabelDiscoveryDto {

    countryList;
    seaBasinList;
    serviceTypeList;
    serviceOperationList;
    serviceRoleList;

    constructor(props) {
        this.countryList = [...props.countryList];
        this.seaBasinList = [...props.seaBasinList];
        this.serviceTypeList = [...props.serviceTypeList];
        this.serviceOperationList = [...props.serviceOperationList];
        this.serviceRoleList = [...props.serviceRoleList];
    }
}