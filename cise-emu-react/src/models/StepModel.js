import { observable } from "mobx";
export default class StepModel {
  idStep = Math.random();
  @observable stepTitle;
  @observable stepClosed = false;
  @observable stepError = false;
  constructor(title) {
    this.stepTitle = title;
    }
  }

