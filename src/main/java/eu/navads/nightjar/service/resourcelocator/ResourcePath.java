package eu.navads.nightjar.service.resourcelocator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static eu.navads.nightjar.util.Assertion.notNull;

@ToString
@EqualsAndHashCode
public final class ResourcePath {

    @Getter
    private final String value;

    private ResourcePath(final String value) {
        this.value = value;
    }

    public static ResourcePath of(final String value) {
        return new ResourcePath(notNull("value", value));
    }
}

