function redirectToUrl(url) {
    window.location = url;
}

function showErrorPopup(xhr, status, error) {
    $.notify(`${error}\nYou can find error details in console`, {
        position: 'top-center',
        autoHide: true
    })
    console.log({errorDetails: xhr.responseJSON});
}

function attachSaveButtonListener() {
    const $saveBtn = $('#saveBtn');

    $saveBtn.on('click', function (event) {
        const isNewSnippet = window.editor.isNewSnippet;
        const snippetValue = window.editor.getValue();//.replace(/\n/g, '\r\n')
        const snippetId = !isNewSnippet ? window.editor.snippetId : null;
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

function attachAllSnippetsButtonListener() {
    const $allSnippetsBtn = $('#allSnippetsBtn');

    $allSnippetsBtn.on('click', function (event) {
        redirectToUrl('/code-snippets');
    });
}

function attachNewSnippetButtonListener() {
    const $newSnippetBtn = $('#newSnippetBtn');

    $newSnippetBtn.on('click', function (event) {
        redirectToUrl('/editor');
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
}

$(document).ready(function () {
    attachSaveButtonListener();
    attachAllSnippetsButtonListener();
    attachNewSnippetButtonListener();
    bindKeyboardShortcuts();
});
