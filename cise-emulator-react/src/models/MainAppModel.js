import {action, computed, observable} from "mobx";
import FileRef from "./FileRef";

export default class MainAppModel {
    @observable modalOpen = true;
    @observable memberId = "#TobeLoaded#";
    @observable optionsTemplate = [];

    @computed
    get isModClosed() {
        return this.modalOpen === false;
    }

    @computed
    get isConnected() {
        return (
            this.memberId !== "#TobeLoaded#" && this.optionsTemplate.length > 0
        );
    }

    @computed get templateOptions() {
        if (!this.isConnected) return [{label: "#None", value: "#None"}];
        console.log("giveOptions", this.optionsTemplate);
        return this.optionsTemplate.map(x => ({label: x.name, value: x.hash}));
    }

    @action
    closeModal() {
        this.modalOpen = false;
    }

    @action
    loadServiceId() {
        this.memberId = "fake-nodecx.nodecx.europa.cx";
        // axios.get(("/webapi/members/0"))
        //     .then((response) => {
        //         console.log("loadServiceId SUCCESS !!! @axios call ", response.data);
        //         this.memberId = (response.data.name);
        //     })
        //     .catch((err) => {
        //         console.log("ERROR !!! @axios call ", err)
        //     })
    }

    @action

    loadXmlTemplates() {
        this.optionsTemplate.push(
            new FileRef("Choose a template", "/None", "#None")
        );

        try {
            axios.get(("/webapi/templates"))
                .then((response) => {
                    console.log("loadXmlTemplates SUCCESS !!! @axios call ", response.data);

                    for (let itXmlFile of response.data) {
                        this.optionsTemplate.push( //FIFO
                            new FileRef(itXmlFile.name, itXmlFile.path, itXmlFile.hash)
                        );
                    }
                    console.log("this optionService:" + this.optionsTemplate);
                })
                .catch((err) => {
                    console.log("ERROR !!! @axios call ", err)

                })
        } catch (e) {
            this.optionsTemplate.push(
                new FileRef("pushTemplate.xml", "/tmp/pushTemplate.xml", "345435345")
            );
        }
    }

}
