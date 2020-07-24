import {getValuesDiscovery, sendDiscoveryMessage} from "./DiscoveryService";
import DiscoveryMessageDto from "./dto/DiscoveryMessageDto";
import {buildValueLabelMap} from "../CommonComponents/HelperFunctions";


export default class DiscoveryStore {

    /**
     * Init phase, retrieving values infos from backend
     */
    async getLabels() {

        console.log("DiscoveryStore initialization Starting ...");

        const labelsDiscoveryDto = await getValuesDiscovery();

        this.setValueAndLabelOnDiscovery(labelsDiscoveryDto);

        console.log("DiscoveryStore initialization done");
    }


    /**
     * Values and Labels build
     */
    labelCountryList = [];
    labelSeaBasinList = [];
    labelServiceTypeList = [];
    labelServiceOperationList = [];
    labelServiceRoleList = [];

    setValueAndLabelOnDiscovery(labelsDiscoveryDto) {
        this.labelCountryList     = buildValueLabelMap(labelsDiscoveryDto.countryList.sort());
        this.labelSeaBasinList    = buildValueLabelMap(labelsDiscoveryDto.seaBasinList.sort());
        this.labelServiceTypeList = buildValueLabelMap(labelsDiscoveryDto.serviceTypeList.sort());
        this.labelServiceOperationList  = buildValueLabelMap(labelsDiscoveryDto.serviceOperationList.sort());
        this.labelServiceRoleList       = buildValueLabelMap(labelsDiscoveryDto.serviceRoleList.sort());
    }


    //--------------------------------------------

    /**
     * Store Input
     */

    discoveryInputInfo = new DiscoveryMessageDto();
    getDiscoveryInputInfo() {
        return this.discoveryInputInfo;
    }

    cleanResources() {
        this.discoveryInputInfo = new DiscoveryMessageDto();
    }

    //--------------------------------------------

    async sendDiscoveryMessage(sender, type, operation) {
        this.discoveryInputInfo.discoverySender = sender;
        this.discoveryInputInfo.discoveryServiceType = type;
        this.discoveryInputInfo.discoveryServiceOperation = operation;

        return await sendDiscoveryMessage(this.discoveryInputInfo);
    }

}