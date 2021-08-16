package cn.maidaotech.smartapi.common.exception;

public class FatalException extends RuntimeException {
    private static final long serialVersionUID = -6978320096293471013L;

    public FatalException(String message, Throwable cause) {
        super(message, cause);
    }

    public FatalException(String message) {
        super(message);
    }

    public FatalException(Throwable cause) {
        super(cause);
    }

}
