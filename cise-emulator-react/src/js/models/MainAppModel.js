import {action, computed, observable} from "mobx";
import Template from "./FileRef";
import axios from "axios";

export default class MainAppModel {
  @observable modalOpen = true;
  @observable memberId = "#TobeLoaded#";
  @observable optionsTemplate = [];

  defaultGetConfig = {
    method: 'get',
    header: {
      'Content-Type': 'application/json'
    }
  };

  defaultServiceUrl = "/api/";

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
    if (!this.isConnected) {
      return [{label: "#None", value: "#None"}];
    }

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
        new Template("Choose a template", "/None", "#None")
    );

    const serviceUrl = ((document.location.hostname === "localhost") ?
        'http://localhost:47080' + this.defaultServiceUrl + 'templates' :
        'http://' + document.location.host + this.defaultServiceUrl
        + 'templates');


    try {
      axios.get(serviceUrl, this.defaultGetConfig)
      .then((response) => {
        console.log("loadXmlTemplates SUCCESS !!! @axios call ", response.data);

        for (let template of response.data) {
          this.optionsTemplate.push(
              new Template(template.templateName, template.templateId, template.templateId)
          );
        }
        console.log("this optionService:" + this.optionsTemplate);
      })
      .catch((err) => {
        console.log("ERROR !!! @axios call ", err)

      })
    } catch (e) {
      alert("configuration error :  inaccessible template list ")
    }
  }

}
