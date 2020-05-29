import {observable} from 'mobx';

export default class Error  {
    @observable errorCode;
    @observable errorMessage;

    constructor(errorCode, errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}