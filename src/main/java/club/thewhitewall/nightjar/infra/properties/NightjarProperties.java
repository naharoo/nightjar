package club.thewhitewall.nightjar.infra.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "nightjar")
public class NightjarProperties {

    public CodeSnippetProperties codeSnippet;

    @Data
    public static class CodeSnippetProperties {

        public CodeSnippetModificationProperties modification;

        @Data
        public static class CodeSnippetModificationProperties {

            public String defaultInvocationMethod;
        }
    }
}

