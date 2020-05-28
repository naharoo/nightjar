package club.thewhitewall.nightjar.service.codesnippet.modification;

import club.thewhitewall.nightjar.domain.CodeSnippet;
import club.thewhitewall.nightjar.domain.CodeSnippetModificationRequest;

public interface CodeSnippetModificationCustomizer {

    void customize(CodeSnippet codeSnippet, CodeSnippetModificationRequest modificationRequest);
}
