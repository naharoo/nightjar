function bindKeyboardShortcuts() {
    $.key('alt+s', function (e) {
        const $searchInput = $('#searchInput');
        $searchInput.focus();
    });
    $.key('alt+n', function (e) {
        const $newSnippetBtn = $('#newSnippetBtn');
        $newSnippetBtn.click();
    });
}

$(document).ready(() => {
    attachNewSnippetButtonListener();
    bindKeyboardShortcuts();
});

