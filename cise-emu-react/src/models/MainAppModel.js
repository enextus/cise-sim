import { observable, computed, action } from "mobx";

export default class MainAppModel {
    @observable modalOpen = true;
    @observable Connected = false;
    @observable memberId = "#TobeLoaded#";
    @observable OptionService = ["#TobeLoaded#"];
    @observable OptionPayload = ["#TobeLoaded#"];
    @observable TimerSinceWithoutConnected = observable({secondsPassed: 0});
    @computed
    get IsModalOpened() {
        return this.modalOpen == true;
    }
    @computed
    get IsModalClosed() {
        return this.modalOpen == false;
    }
    @computed
    get IsConnected() {
        return this.Connected == true;
    }
    @computed
    get IsDisconnected() {
        return this.Connected == false;
    }
    @action
     closeModal() {
         this.modalOpen=false;
     }
    @action
     openModal() {
         this.modalOpen=true;
     }
}
