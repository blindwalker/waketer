package at.kropf.waketer.model;

/**
 * Created by martinkropf on 08.11.14.
 */
public class ErrorHandler {

    public Throwable getThrowable() {
        return throwable;
    }

    private Throwable throwable;

    public String getUserErrorMessage() {
        return userErrorMessage;
    }

    private String userErrorMessage;

    public ErrorHandler(Throwable throwable, String userErrorMessage) {
        this.throwable = throwable;
        this.userErrorMessage = userErrorMessage;
    }
}
