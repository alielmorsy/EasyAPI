package aie.easyAPI.excepation;

public class SerializeException extends Exception{
    public SerializeException() {
    }

    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(String message, Throwable cause) {
        super(message, cause);
    }
}
