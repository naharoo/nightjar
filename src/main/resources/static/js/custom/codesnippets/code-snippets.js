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

function attachSnippetsTableRowClickListener() {
    const $row = $('#snippets-table > tbody > tr');

    $row.on('click', function (event) {
        const snippetId = $(event.target.parentNode).attr('snippetId');
        redirectToUrl(`/editor?snippetId=${snippetId}`);
    });
}

function populatePageDataIntoTable(pageData, pageIndex) {
    const $table = $('#snippets-table');
    $table.attr('pageIndex', pageIndex)

    const $tableBody = $('#snippets-table > tbody');
    $tableBody.empty();

    const content = pageData.content;
    content.forEach(snippet => {
        $tableBody.append(`
                <tr snippetId="${snippet.id}">
                    <td>${snippet.id}</td>
                    <td>${snippet.name}</td>
                    <td>${convertDateToHumanReadableForm(snippet.creationDate)}</td>
                    <td>${convertDateToHumanReadableForm(snippet.modificationDate)}</td>
                </tr>
        `);
    })

    const $paginationInfo = $('#paginationInfo');
    const currentPage = pageIndex + 1;
    const totalPages = pageData.totalPages;
    $paginationInfo.text(`${currentPage} of ${totalPages}`);

    if (currentPage >= totalPages) {
        $('#nextPageBtn').addClass('pagination-navigation-button-disabled');
    } else if (currentPage > 1) {
        $('#previousPageBtn').removeClass('pagination-navigation-button-disabled');
    }

    if (currentPage <= 1) {
        $('#previousPageBtn').addClass('pagination-navigation-button-disabled');
    } else if (currentPage < totalPages) {
        $('#nextPageBtn').removeClass('pagination-navigation-button-disabled');
    }

    attachSnippetsTableRowClickListener();
}

function loadDataIntoTable(filter_name = null, page = 0, size = 20) {
    $.ajax({
        url: '/rest/code-snippets/search',
        type: 'GET',
        data: {
            name: filter_name,
            page: page,
            size: size
        },
        success: data => populatePageDataIntoTable(data, page),
        error: showErrorPopup
    })
}

function attachNextPageButtonListener() {
    const $nextPageBtn = $('#nextPageBtn');

    $nextPageBtn.on('click', function (event) {
        const $table = $('#snippets-table');
        const nextPageIndex = +$table.attr('pageIndex') + 1;

        const filter_name = $('#searchInput').val();
        loadDataIntoTable(filter_name, nextPageIndex)
    });
}

function attachPreviousPageButtonListener() {
    const $previousPageBtn = $('#previousPageBtn');

    $previousPageBtn.on('click', function (event) {
        const $table = $('#snippets-table');
        const nextPageIndex = +$table.attr('pageIndex') - 1;

        const filter_name = $('#searchInput').val();
        loadDataIntoTable(filter_name, nextPageIndex)
    });
}

function attachSearchInputListener() {
    const $searchInput = $('#searchInput');
    $searchInput.on('keyup', function (event) {
        const filter_name = $searchInput.val();
        loadDataIntoTable(filter_name)
    });
}

$(document).ready(() => {
    attachNewSnippetButtonListener();
    bindKeyboardShortcuts();
    attachNextPageButtonListener();
    attachPreviousPageButtonListener();
    attachSearchInputListener();

    loadDataIntoTable();
});

