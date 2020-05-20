package eu.navads.nightjar.service.scriptexecution;

import eu.navads.nightjar.infra.logging.Loggable;
import eu.navads.nightjar.service.resourcelocator.ResourcePath;
import eu.navads.nightjar.service.resourcelocator.StaticResourceLocator;
import org.springframework.stereotype.Service;

@Service
public class ScriptExecutionServiceImpl implements ScriptExecutionService {

    private final StaticResourceLocator staticResourceLocator;

    public ScriptExecutionServiceImpl(final StaticResourceLocator staticResourceLocator) {
        this.staticResourceLocator = staticResourceLocator;
    }

    @Override
    @Loggable
    public String buildPageHtml() {
        return staticResourceLocator.get(ResourcePath.of("static/html/script-execution.html")).getContent();
    }
}
