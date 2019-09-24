import {action, computed, observable} from "mobx";
import FileRef from "./FileRef";

export default class MainAppModel {
    @observable modalOpen = true;
    @observable memberId = "#TobeLoaded#";
    @observable optionsTemplate = [];

    //@observable TimerSincePullRequested = observable({secondsPassed: 0});

    @computed
    get isModClosed() {
        return this.modalOpen == false;
    }

    @computed
    get IsConnected() {
        return (
            this.memberId != "#TobeLoaded#" && this.optionsTemplate.length > 0
        );
    }


    @computed get templateOptions() {
        if (!this.IsConnected) return [{label: "#None", value: "#None"}];
        console.log("giveOptions", this.optionsTemplate);
        return this.optionsTemplate.map(x => ({label: x.name, value: x.hash}));
    }


    @action
    closeModal() {
        this.modalOpen = false;
    }


    @action
    obtainSelfMember() {
        this.memberId = "fake.id.debug";
        // axios.get(("/webapi/members/0"))
        //     .then((response) => {
        //         console.log("obtainSelfMember SUCCESS !!! @axios call ", response.data);
        //         this.memberId = (response.data.name);
        //     })
        //     .catch((err) => {
        //         console.log("ERROR !!! @axios call ", err)
        //     })
    }

    @action
    obtainXmlTemplates() {
        this.optionsTemplate.push(
            new FileRef("Choose a template", "/None", "#None")
        );
        this.optionsTemplate.push(
            new FileRef("pushTemplate.xml", "/tmp/pushTemplate.xml", "345435345")
        );

        // axios.get(("/webapi/templates"))
        //     .then((response) => {
        //         console.log("obtainXmlTemplates SUCCESS !!! @axios call ", response.data);
        //         this.optionsTemplate = [];
        //         for (let itXmlFile of response.data) {
        //             this.optionsTemplate.push( //FIFO
        //                 new FileRef(itXmlFile.name, itXmlFile.path, itXmlFile.hash)
        //             );
        //         }
        //         console.log("this optionService:" + this.optionsTemplate);
        //     })
        //     .catch((err) => {
        //         console.log("ERROR !!! @axios call ", err)
        //     })
    }

}
