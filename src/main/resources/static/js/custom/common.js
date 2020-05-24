function redirectToUrl(url) {
    window.location = url;
}

function showErrorPopup(xhr, status, error) {
    const responseJSON = xhr.responseJSON;
    $.notify(`${error}\n${responseJSON.messages[0]}`, {
        position: 'top-center',
        autoHide: true
    })
    console.log({errorDetails: responseJSON});
}

function convertDateToHumanReadableForm(rawDate) {
    return new Date(rawDate).toLocaleString();
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
