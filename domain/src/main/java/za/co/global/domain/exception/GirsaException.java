package za.co.global.domain.exception;

public class GirsaException extends Exception {

    public GirsaException(String message) { super(message); }

    public GirsaException(String message,Throwable throwable) {
        super(message,throwable);
    }


    public GirsaException(Throwable throwable) {
        super(throwable);
    }
}
