package aie.easyAPI.excepation;

public class StopWatchException extends RuntimeException{
    public StopWatchException() {
    }

    public StopWatchException(String message) {
        super(message);
    }

    public StopWatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
