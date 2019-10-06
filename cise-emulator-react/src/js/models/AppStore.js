import {action, observable} from "mobx";

export default class AppStore {
    @observable memberId = "#TobeLoaded#";

    @action
    loadServiceId() {
        this.memberId = "fake-nodecx.nodecx.europa.cx";
    }
}
