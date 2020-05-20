package eu.navads.nightjar.service.resourcelocator;

import eu.navads.nightjar.infra.logging.Loggable;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static eu.navads.nightjar.util.Assertion.notNull;
import static java.lang.ClassLoader.getSystemResourceAsStream;

@Component
public class CachedStaticResourceLocator implements StaticResourceLocator {

    private final Map<ResourcePath, StaticResource> cache = new ConcurrentHashMap<>();

    private static StaticResource loadFile(final ResourcePath resourcePath) {
        try {
            final String content = IOUtils.toString(
                    notNull("InputStream", getSystemResourceAsStream(notNull("resourcePath", resourcePath).getValue())),
                    StandardCharsets.UTF_8.name()
            );
            return StaticResource.with(resourcePath, content);
        } catch (final IOException ioException) {
            throw new IllegalStateException(String.format("Resource:'%s' is not found.", resourcePath), ioException);
        }
    }

    @Override
    @Loggable
    public StaticResource get(final ResourcePath relativePath) {
        return cache.computeIfAbsent(relativePath, CachedStaticResourceLocator::loadFile);
    }
}
