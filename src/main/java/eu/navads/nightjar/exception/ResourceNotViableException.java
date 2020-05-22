package eu.navads.nightjar.exception;

public class ResourceNotViableException extends RuntimeException {

    protected ResourceNotViableException(final String message) {
        super(message);
    }

    protected ResourceNotViableException(final String message, final Throwable cause) {
        super(message, cause);
    }

    protected ResourceNotViableException(final Throwable cause) {
        super(cause);
    }

    public static ResourceNotViableException createInstance(final Class resourceType, final String identifier) {
        return createInstance(resourceType, identifier, null);
    }

    public static ResourceNotViableException createInstance(
            final Class resourceType,
            final String identifier,
            final Throwable cause
    ) {
        return new ResourceNotViableException(String.format(
                "%s by given %s is no more viable.",
                resourceType.getSimpleName(),
                identifier
        ), cause);
    }
}
