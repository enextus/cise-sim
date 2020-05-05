export default class Template {

    id = "";
    name = "";
    content = "";

    constructor(props) {
        this.id = props.templateId;
        this.name = props.templateName;
        this.content = props.templateContent;
    }
}