import {getTemplateById, getTemplateList} from "./TemplateService";
import { observable, computed, action, decorate } from "mobx";
import {sendMessage} from "../messages/MessageService";
import Template from "./Template";

export default class TemplateStore {
    templateList = [];
    selected = "empty"; // selectedTemplateId
    messageId = this.uuidv4();
    correlationId = "";
    requiresAck = false;
    template = new Template({templateId: "", templateName: "", templateContent: ""});

    //@computed
    get templateOptions() {
        return this.templateList.map(t => ({label: t.name, value: t.id}));
    }

    get isTemplateSelected() {
        return !(this.selected === "empty");
    }

    //@action
    createNewMessageId() {
        this.messageId = this.uuidv4();
    }

    //@action
    async loadTemplateList() {
        const templates = await getTemplateList();
        templates.forEach(t => this.templateList.push(t));
    }

    //@action
    toggleRequiresAck() {
        this.requiresAck = !this.requiresAck;
    }

    //@action
    async preview() {
        const getTemplateByIdResposnse = await getTemplateById(
            this.selected,
            this.messageId,
            this.correlationId,
            this.requiresAck);

        console.log("getTemplateByIdResposnse:", getTemplateByIdResposnse);

        if (getTemplateByIdResposnse.code) {
            console.log("TemplateStore preview return error");
        } else {
            console.log("TemplateStore preview success ");
            this.template = getTemplateByIdResposnse;
        }
        return getTemplateByIdResposnse;
    }


    // TODO To be moved into another store?
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

decorate(TemplateStore, {
        templateList: observable,
        selected: observable,
        messageId: observable,
        correlationId: observable,
        requiresAck: observable,
        templateOptions: computed,
        isTemplateSelected: computed,
        createNewMessageId: action,
        loadTemplateList: action,
        toggleRequiresAck: action,
        preview: action,
        send: action
    }
);