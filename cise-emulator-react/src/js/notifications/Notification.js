export default class Notification  {
    errorCode;
    errorMessage;

    constructor(errorCode, errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}