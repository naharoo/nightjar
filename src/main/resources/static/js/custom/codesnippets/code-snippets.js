function renderDate(rawDate) {
    return new Date(rawDate).toLocaleString();
}

function buildDatatableAndFetchData(name = null) {
    let dataTable = $('#dataTable').DataTable({
        "processing": false,
        "serverSide": true,
        "bServerSide": false,
        "ordering": false,
        "searching": true,
        "paging": true,
        "ajax": {
            url: `/rest/code-snippets/search`,
            type: 'GET',
            data: function (d) {
                return {
                    draw: d.draw,
                    name: d.search['value'],
                    page: d.start,
                    size: d.length
                }
            }
        },
        "columns": [
            {
                data: "id",
                title: "Id"
            },
            {
                data: "name",
                title: 'Name',
                'render': function(data, type, row, meta){
                    if(type === 'display'){
                        data = `<a href="/editor?snippetId=${row.id}">${data}</a>`;
                    }

                    return data;
                }
            },
            {
                data: "creationDate",
                title: "Creation Date",
                'render': renderDate
            },
            {
                data: "modificationDate",
                title: "Modification Date",
                'render': renderDate
            }
        ]
    });


}

$(document).ready(() => {
    buildDatatableAndFetchData();
});

