package club.thewhitewall.nightjar.service.codesnippet.modification;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import club.thewhitewall.nightjar.domain.CodeSnippetExtraAttribute;
import club.thewhitewall.nightjar.domain.CodeSnippetModificationRequest;
import club.thewhitewall.nightjar.domain.CodeSnippetQualifier;
import club.thewhitewall.nightjar.infra.properties.NightjarProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class CodeSnippetModificationDefaultInvocationMethodSetterCustomizer implements CodeSnippetModificationCustomizer {

    private final NightjarProperties properties;

    @Autowired
    public CodeSnippetModificationDefaultInvocationMethodSetterCustomizer(final NightjarProperties properties) {
        this.properties = properties;
    }

    @Override
    public void customize(final CodeSnippet codeSnippet, final CodeSnippetModificationRequest modificationRequest) {
        final Set<CodeSnippetQualifier> qualifiers = modificationRequest.getQualifiers();
        final Map<CodeSnippetExtraAttribute, String> extraAttributes = modificationRequest.getExtraAttributes();

        if (qualifiers.contains(CodeSnippetQualifier.INVOCABLE) &&
                isBlank(extraAttributes.get(CodeSnippetExtraAttribute.INVOCATION_METHOD))) {
            codeSnippet.setExtraAttributeValue(
                    CodeSnippetExtraAttribute.INVOCATION_METHOD,
                    properties.codeSnippet.modification.defaultInvocationMethod
            );
        }
    }
}
