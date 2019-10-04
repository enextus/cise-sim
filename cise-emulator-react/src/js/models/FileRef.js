
import { observable} from "mobx";

export default class FileRef {
    @observable path = "";
    @observable name = "";
    @observable hash = "";

    constructor(name, path, hash) {
        this.path = path;
        this.name = name;
        this.hash = hash;
    }
    iqual(hash){
        return (this.hash == hash)
    }
}