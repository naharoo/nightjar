package club.thewhitewall.nightjar.exception;

public class PreconditionFailedException extends RuntimeException {

    private static final long serialVersionUID = 1441613319507470057L;

    public PreconditionFailedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public static PreconditionFailedException createInstance(final String message) {
        return createInstance(message, null);
    }

    public static PreconditionFailedException createInstance(final String message, final Throwable cause) {
        return new PreconditionFailedException(message, cause);
    }
}
