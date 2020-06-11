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

    setValueAndLabelOnDiscovery(labelsDiscoveryDto) {
        this.labelCountryList     = buildValueLabelMap(labelsDiscoveryDto.countryList);
        this.labelSeaBasinList    = buildValueLabelMap(labelsDiscoveryDto.seaBasinList);
        this.labelServiceTypeList = buildValueLabelMap(labelsDiscoveryDto.serviceTypeList);
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

    sendDiscoveryMessage() {
        return sendDiscoveryMessage(this.discoveryInputInfo);
    }

}