function attachSaveButtonListener() {
    const $saveBtn = $('#saveBtn');

    $saveBtn.on('click', function (event) {
        const isNewSnippet = window.snippet.isNew;
        const snippetValue = window.editor.getValue();//.replace(/\n/g, '\r\n')
        const snippetId = !isNewSnippet ? window.snippet.id : null;
        const newName = isNewSnippet ? $('#newName')[0].value : null;

        if (isNewSnippet) {
            $.ajax({
                type: 'POST',
                url: '/rest/code-snippets',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    name: newName,
                    value: snippetValue
                }),
                success: function (responseData, status, xhr) {
                    redirectToUrl('/editor?snippetId=' + responseData.id);
                },
                error: showErrorPopup,
                dataType: 'JSON'
            })
        } else {
            $.ajax({
                type: 'PUT',
                url: '/rest/code-snippets/' + snippetId,
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    value: snippetValue
                }),
                success: function (responseData, status, xhr) {
                    redirectToUrl('/editor?snippetId=' + responseData.id);
                },
                error: showErrorPopup,
                dataType: 'JSON'
            })
        }
    })
}

function attachSnippetDetailsButtonListener() {
    const $snippetDetailsBtn = $('#snippetDetailsBtn');
    const $detailsPopup = $('.details-popup');

    $snippetDetailsBtn.on('click', function (event) {
        const modalState = $detailsPopup.attr('modal-state');

        if (modalState === 'closed') {
            $detailsPopup.removeAttr('style');
            $detailsPopup.attr('modal-state', 'open');
        } else if (modalState === 'open') {
            $detailsPopup.attr('style', 'display: none');
            $detailsPopup.attr('modal-state', 'closed');
        }
    });
}

function bindKeyboardShortcuts() {
    $.key('alt+s', function (e) {
        const $saveBtn = $('#saveBtn');
        $saveBtn.click();
    });
    $.key('alt+a', function (e) {
        const $allSnippetsBtn = $('#allSnippetsBtn');
        $allSnippetsBtn.click();
    });
    $.key('alt+n', function (e) {
        const $newSnippetBtn = $('#newSnippetBtn');
        $newSnippetBtn.click();
    });
    $.key('alt+q', function (e) {
        const $snippetDetailsBtn = $('#snippetDetailsBtn');
        $snippetDetailsBtn.click();
    });
}

$(document).ready(function () {
    attachSaveButtonListener();
    attachAllSnippetsButtonListener();
    attachNewSnippetButtonListener();
    attachSnippetDetailsButtonListener();
    bindKeyboardShortcuts();
});
