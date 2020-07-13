function attachSaveButtonListener() {
    const $saveBtn = $('#saveBtn');

    $saveBtn.on('click', function (event) {
        const isNewSnippet = window.snippet.isNew;
        const snippetValue = window.editor.getValue();//.replace(/\n/g, '\r\n')
        const snippetId = !isNewSnippet ? window.snippet.id : null;
        const newName = isNewSnippet ? $('#newName')[0].value : null;
        const description = $('#description').val();
        const qualifiers = window.snippetDetails.qualifiersMultiselect.value();
        const extraAttributesWrapper = $('.extra-attributes-wrapper');

        if (isNewSnippet) {
            $.ajax({
                type: 'POST',
                url: '/rest/code-snippets',
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify({
                    name: newName,
                    value: snippetValue,
                    description: description,
                    qualifiers: qualifiers
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
                    value: snippetValue,
                    description: description,
                    qualifiers: qualifiers
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

function drawDescription() {
    const isNewSnippet = window.snippet.isNew;

    if (!isNewSnippet) {
        const snippetId = window.snippet.id;

        $.ajax({
            url: `/rest/code-snippets/${snippetId}`,
            type: 'GET',
            success: (snippet) => {
                console.log(snippet);
                $('#description').val(snippet.description);
            },
            error: showErrorPopup
        })
    }

}

function drawQualifiers() {
    $.ajax({
        url: '/rest/code-snippets/qualifiers/list',
        type: 'GET',
        success: (allQualifiers) => {
            const multiselectOptions = allQualifiers.map((qualifier) => {
                return {label: qualifier, value: qualifier}
            });

            const isNewSnippet = window.snippet.isNew;

            if (isNewSnippet) {
                window.snippetDetails.qualifiersMultiselect = new SelectPure(".qualifiers", {
                    options: multiselectOptions,
                    multiple: true
                });
            } else {
                const snippetId = window.snippet.id;

                $.ajax({
                    url: `/rest/code-snippets/${snippetId}`,
                    type: 'GET',
                    success: (snippet) => {
                        window.snippetDetails.qualifiersMultiselect = new SelectPure(".qualifiers", {
                            options: multiselectOptions,
                            value: snippet.qualifiers,
                            multiple: true
                        });
                    },
                    error: showErrorPopup
                })
            }

        },
        error: showErrorPopup
    })
}

function drawExtraAttributes() {
    $.ajax({
        url: '/rest/code-snippets/extra-attributes/list',
        type: 'GET',
        success: (allExtraAttributeKeys) => {
            const $extraAttributesWrapper = $("#extra-attributes-wrapper");
            const isNewSnippet = window.snippet.isNew;

            if (isNewSnippet) {
                allExtraAttributeKeys.forEach((extraAttributeKey) => {
                    $extraAttributesWrapper.append(`
                        <div class="extra-attribute">
                            <b class="extra-attribute-key">${extraAttributeKey}</b>
                            <input class="extra-attribute-value" type="text"/>
                        </div>
                    `);
                });
            } else {
                const snippetId = window.snippet.id;

                $.ajax({
                    url: `/rest/code-snippets/${snippetId}`,
                    type: 'GET',
                    success: (snippet) => {
                        allExtraAttributeKeys.forEach((extraAttributeKey) => {
                            const extraAttributeValue = snippet.extraAttributes[extraAttributeKey];
                            const valueToBeRendered = `value="${extraAttributeValue}"`;

                            $extraAttributesWrapper.append(`
                                <div class="extra-attribute">
                                    <b class="extra-attribute-key">${extraAttributeKey}</b>
                                    <input class="extra-attribute-value" type="text" ${extraAttributeValue ? valueToBeRendered : ""}/>
                                </div>
                            `);
                        });
                    },
                    error: showErrorPopup
                })
            }

        },
        error: showErrorPopup
    })
}

$(document).ready(function () {
    attachSaveButtonListener();
    attachAllSnippetsButtonListener();
    attachNewSnippetButtonListener();
    attachSnippetDetailsButtonListener();
    bindKeyboardShortcuts();

    drawDescription();
    drawQualifiers();
    drawExtraAttributes();
});
