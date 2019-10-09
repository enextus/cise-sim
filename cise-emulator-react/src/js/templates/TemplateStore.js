import {getTemplateById, getTemplateList} from "./TemplateService";
import {action, computed, observable} from "mobx";
import axios from "axios";
import {sendMessage} from "../messages/MessageService";
import Template from "./Template";

export default class TemplateStore {

    @observable templateList = [];
    @observable selected = "empty"; // selectedTemplateId
    @observable messageId = this.uuidv4();
    @observable correlationId = "";
    @observable requiresAck = false;
    @observable template = new Template({ templateId: "", templateName: "", templateContent: "" });

    @computed get templateOptions() {
        return this.templateList.map(t => ({label: t.name, value: t.id}));
    }

    @computed get isTemplateSelected() {
        return !(this.selected === "empty");
    }

    @action
    createNewMessageId() {
        this.messageId = this.uuidv4();
    }

    @action
    async loadTemplateList() {
        const templates = await getTemplateList();
        templates.forEach(t => this.templateList.push(t));
    }

    @action
    toggleRequiresAck() {
        this.requiresAck = !this.requiresAck;
    }

    @action
    async preview() {
        const getTemplateByIdResposnse = await getTemplateById(
            this.selected,
            this.messageId,
            this.correlationId,
            this.requiresAck);

        console.log("getTemplateByIdResposnse:", getTemplateByIdResposnse);

        if(getTemplateByIdResposnse.errorCode){
            console.log("TemplateStore preview return error");
        }
        else {
            console.log("TemplateStore preview success ");
            this.template = getTemplateByIdResposnse;
        }
        return getTemplateByIdResposnse;
    }

    // TODO To be moved into another store?
    @action
    send() {
        const message = sendMessage(
            this.selected,
            this.messageId,
            this.correlationId,
            this.requiresAck);
    }

    uuidv4() {
        return ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, c =>
            (c ^ crypto.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
        );
    }
}
