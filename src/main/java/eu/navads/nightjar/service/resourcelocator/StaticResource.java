package eu.navads.nightjar.service.resourcelocator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static eu.navads.nightjar.util.Assertion.notNull;

@ToString
@EqualsAndHashCode
public final class StaticResource {

    @Getter
    private final ResourcePath path;

    @Getter
    private final String content;

    private StaticResource(final ResourcePath path, final String content) {
        this.path = path;
        this.content = content;
    }

    public static StaticResource with(final ResourcePath path, final String content) {
        return new StaticResource(notNull("path", path), content);
    }
}
