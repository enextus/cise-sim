
import { observable} from "mobx";

export default class Member {
    @observable id = true;
    @observable name = false;

    constructor(name, id) {
        this.id = id;
        this.name = name;
    }
}
