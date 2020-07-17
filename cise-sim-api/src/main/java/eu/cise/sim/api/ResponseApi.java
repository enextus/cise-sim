package eu.cise.sim.api;

public class ResponseApi<T> {

    private final T result;

    private final boolean isOk;

    private final String errDetail;
    private final ErrorId errorId;

    public ResponseApi(T result) {
        this.result = result;
        this.isOk = Boolean.TRUE;
        this.errDetail = null;
        this.errorId = ErrorId.NONE;
    }

    public ResponseApi(ErrorId errorId, String errDetail) {
        this.result = null;
        this.isOk = Boolean.FALSE;
        this.errDetail = errDetail;
        this.errorId = errorId;
    }

    public enum ErrorId {
        NONE, FATAL
    }


    public T getResult() {
        return result;
    }

    public boolean isOk() {
        return isOk;
    }

    public String getErrDetail() {
        return errDetail;
    }

    public ErrorId getErrorId() {
        return errorId;
    }
}
