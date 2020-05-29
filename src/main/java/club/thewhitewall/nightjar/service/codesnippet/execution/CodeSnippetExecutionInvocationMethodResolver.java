package club.thewhitewall.nightjar.service.codesnippet.execution;

import club.thewhitewall.nightjar.domain.CodeSnippet;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface CodeSnippetExecutionInvocationMethodResolver {

    String resolveMethodName(@Valid @NotNull CodeSnippet codeSnippet);
}
