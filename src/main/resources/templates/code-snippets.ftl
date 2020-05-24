<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible"/>
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="icon" type="image/png" href="/static/images/share.png">
    <title>Nightjar - Snippets</title>

    <style>
        html, body {
            margin: 0;
            padding: 0;
            height: 100%;
            width: 100%;
            color: white;
        }

        .content {
            height: 100%;
            background-color: #1e1e1e;
        }

        .header {
            width: 100%;
            height: 7%;
            background-color: #1e1e1e;

            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: space-between;
        }

        .header-name {
            padding: 5px;
            margin: 5px;
            width: auto;
            min-width: 200px;
            color: white;
            border: 1px solid grey;
            border-radius: 4px;
            text-align: center;
        }

        .header-search {
            padding: 7px;
            margin: 5px;
            width: 200px;
            border: 1px solid white;
            border-radius: 4px;
            background-color: #1e1e1e;
            color: white;
            text-align: center;
        }

        .header-btn {
            padding: 7px;
            margin: 5px;
            width: auto;
            min-width: 100px;
            height: 40px;
            background-color: #1e1e1e;
            color: white;
            border: 1px solid white;
            border-radius: 4px;
        }

        .table-container {
            margin: 20px 60px;
        }

        .table-body-container {
            border: 1px solid white;
            border-radius: 4px;
        }

        .snippets-table {
            width: 100%;
            border-collapse: collapse;
        }

        .snippets-table > thead > tr > th {
            border-bottom: 1px solid white;
            padding: 10px;
        }

        .snippets-table > tbody > tr {
            text-align: center;
        }

        .snippets-table > tbody > tr:hover {
            background-color: #2e856e;
            cursor: pointer;
        }

        .snippets-table > tbody > tr > td {
            padding: 10px;
        }

        .pagination-wrapper {
            display: flex;
            flex-direction: row;
            justify-content: flex-end;
            margin-top: 20px;
        }

        .pagination-container {

        }

        .pagination-navigation-button {
            background-color: transparent;
            border: 1px solid grey;
            color: white;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
            font-size: x-large;
        }

        .pagination-navigation-button-disabled {
            pointer-events: none;
            color: grey;
            border: 1px solid grey;
        }

        .pagination-info {
            color: darkgrey;
            margin-right: 20px;
        }

    </style>
    <link type="text/css" href="/static/css/custom/common.css"/>
</head>
<body>

<div class="content">

    <div class="header">
        <span class="header-name">Snippets</span>
        <input id="searchInput" class="header-search" type="text" placeholder="Search by name"/>
        <div>
            <button id="newSnippetBtn" class="header-btn">New Snippet</button>
        </div>
    </div>

    <div class="table-container">
        <div class="table-body-container">
            <table id="snippets-table" class="snippets-table">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>Creation Date</th>
                    <th>Modification Date</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="pagination-wrapper">
            <div class="pagination-container">
                <span id="paginationInfo" class="pagination-info">
                    1 of 10
                </span>
                <button id="previousPageBtn" class="pagination-navigation-button pagination-navigation-button-disabled"><</button>
                <button id="nextPageBtn" class="pagination-navigation-button">></button>
            </div>
        </div>
    </div>
</div>

<script src="/static/js/jquery/jquery.min.js"></script>
<script src="/static/js/jquery/jquery-key.js"></script>
<script src="/static/js/notifyjs/notify.min.js"></script>
<script src="/static/js/custom/common.js"></script>
<script src="/static/js/custom/codesnippets/code-snippets.js"></script>
</body>
</html>
