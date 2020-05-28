package club.thewhitewall.nightjar.service.codesnippet.modification;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import club.thewhitewall.nightjar.domain.CodeSnippetModificationRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Order(HIGHEST_PRECEDENCE)
@Component
public class CodeSnippetModifiedFieldsBlindSetterCustomizer implements CodeSnippetModificationCustomizer {

    @Override
    public void customize(final CodeSnippet codeSnippet, final CodeSnippetModificationRequest modificationRequest) {
        codeSnippet.setValue(modificationRequest.getValue())
                   .setDescription(modificationRequest.getDescription())
                   .setQualifiers(modificationRequest.getQualifiers())
                   .setExtraAttributes(modificationRequest.getExtraAttributes());
    }
}
