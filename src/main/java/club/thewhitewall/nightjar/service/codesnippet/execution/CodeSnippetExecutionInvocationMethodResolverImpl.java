package club.thewhitewall.nightjar.service.codesnippet.execution;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import club.thewhitewall.nightjar.domain.CodeSnippetExtraAttribute;
import club.thewhitewall.nightjar.domain.CodeSnippetQualifier;
import club.thewhitewall.nightjar.infra.logging.Loggable;
import club.thewhitewall.nightjar.infra.validation.params.ValidParams;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class CodeSnippetExecutionInvocationMethodResolverImpl implements CodeSnippetExecutionInvocationMethodResolver {

    @Override
    @Loggable
    @ValidParams
    public String resolveMethodName(@Valid @NotNull final CodeSnippet codeSnippet) {
        final String methodName = codeSnippet.getExtraAttributeValue(CodeSnippetExtraAttribute.INVOCATION_METHOD);

        if (codeSnippet.containsQualifier(CodeSnippetQualifier.INVOCABLE) && isBlank(methodName)) {
            throw new IllegalStateException("INVOCABLE CodeSnippet must contain INVOCATION_METHOD.");
        } else if (!codeSnippet.containsQualifier(CodeSnippetQualifier.INVOCABLE)) {
            throw new IllegalArgumentException("Provided CodeSnippet must be INVOCABLE");
        }

        return methodName;
    }
}
