export default class Template {

    id = "";
    name = "";
    content = "";

    constructor(props) {
        console.log("Template",props);
        this.id = props.templateId;
        this.name = props.templateName;
        this.content = props.templateContent;
    }
}