package eu.navads.nightjar.util;

public final class Assertion {

    private Assertion() {
        throw new IllegalAccessError("Utility class cannot be instantiated.");
    }

    public static <T> T notNull(final String fieldName, final T t) {
        if (t == null) {
            throw new IllegalArgumentException(String.format("%s cannot be null.", fieldName));
        }

        return t;
    }

    public static String notBlank(final String fieldName, final String string) {
        if (string == null || string.trim().isBlank()) {
            throw new IllegalArgumentException(String.format("%s cannot be blank.", fieldName));
        }

        return string;
    }
}
