export default class LabelDiscoveryDto {

    countryList;
    seaBasinList;
    serviceTypeList;

    constructor(props) {
        this.countryList = [...props.countryList];
        this.seaBasinList = [...props.seaBasinList];
        this.serviceTypeList = [...props.serviceTypeList];
    }
}