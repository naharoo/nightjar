function buildDatatableAndFetchData(name = null) {
    let dataTable = $('#dataTable').DataTable({
        "processing": true,
        "serverSide": true,
        "ordering": false,
        "searching": false,
        "ajax": {
            url: '/rest/code-snippets/search',
            type: 'GET',
            data: {
                name: name
            },
            dataSrc: "content",
            recordsTotal: "totalSize",
            recordsFiltered: "content.length"
        },
        "columns": [
            {
                data: "id",
                title: "Id"
            },
            {
                data: "name",
                title: "Name"
            },
            {
                data: "creationDate",
                title: "Creation Date"
            },
            {
                data: "modificationDate",
                title: "Modification Date"
            }
        ]
    });


}

$(document).ready(function () {
    buildDatatableAndFetchData();
});

