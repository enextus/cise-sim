import { observable, computed, action } from "mobx";

import StepModel from "./StepModel";
import MessageModel from "./MessageModel";
import MessageType from "./MessageType";

export default class StepListModel {
    @observable steps = [];

    @computed
    get unfinishedStepCount() {
        return this.steps.filter(step=> !step.stepClosed).length;
    }
    @computed
    get finishedStepCount() {
        return this.steps.filter(step=> step.stepClosed).length;
    }
    @computed
    get erroneousStepFlow() {
        return this.steps.filter(step => !step.stepError()).length;
    }



    @action
    addMessage(correlationId) {
        this.messages.push(new MessageModel(correlationId,MessageType.MASTER_OUT,"templatePush.xml","you.xml",true));
    }
    @action
    addStep(title) {
         this.steps.push(new StepModel(title));
    }
}
