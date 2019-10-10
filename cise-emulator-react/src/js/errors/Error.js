export default class Error  {
    code;
    message;

    constructor(errorCode, errorMessage) {
        this.code = errorCode;
        this.message = errorMessage;
    }
}