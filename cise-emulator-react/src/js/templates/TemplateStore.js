import {getTemplateList} from "./TemplateService";
import {action, computed, observable} from "mobx";

export default class TemplateStore {

    @observable templateList = [];

    @computed get templateOptions() {
        console.log("templateList", this.templateList);
        return this.templateList.map(t => ({label: t.name, value: t.id}));
    }

    uuidv4() {
        return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
        );
    }

    @action
    async loadTemplateList() {
        console.log("getTemplateList()", getTemplateList());
        const templates = await getTemplateList();
        templates.forEach(t => this.templateList.push(t));
    }

}
