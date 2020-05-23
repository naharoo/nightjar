function redirectToUrl(url) {
    window.location = url;
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
                dataType: 'JSON'
            })
        }
    })
}

$(document).ready(function () {
    attachSaveButtonListener();
});
