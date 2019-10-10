export default class Error  {
    errorCode;
    errorMessage;

    constructor(errorCode, errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}