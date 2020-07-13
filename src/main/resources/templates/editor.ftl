<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta content="IE=edge" http-equiv="X-UA-Compatible"/>
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type">
    <link rel="icon" type="image/png" href="/static/images/share.png">
    <title>Nightjar - Snippet Editor</title>

    <style>
        html, body {
            height: 100%;
            padding: 0;
            margin: 0;
        }

        #main-container {
            height: 100%;
            width: 100%;
            display: flex;
            flex-direction: column;
        }

        #header-container {
            width: 100%;
            background-color: #1e1e1e;

            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: space-between;
        }

        .header-left-container {
            display: flex;
            flex-direction: row;
            justify-content: center;
        }

        #snippetName {
            display: inline-block;
            padding: 10px;
            margin: 5px;
            width: 150px;
            height: 18px;
            color: white;
            border: 1px solid grey;
            border-radius: 4px;
            text-align: center;
            white-space: nowrap;
            overflow-y: scroll;
            scrollbar-width: none; /* Firefox */
            -ms-overflow-style: none;
        }

        #snippetName::-webkit-scrollbar {
            width: 0;
            height: 0;
        }

        #newName {
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
            width: 100px;
            height: 40px;
            background-color: #1e1e1e;
            color: white;
            border: 1px solid white;
            border-radius: 4px;
        }

        #editor-container {
            height: 100%;
            overflow: hidden;
            background-color: #1e1e1e;
        }

        .details-popup {
            display: flex;
            flex-direction: column;
            background-color: #2e856e;
            border: 1px #e5d8d8;
            width: 400px;
            height: auto;
            z-index: 100;
            position: absolute;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
        }

        .popup-form-item {
            width: 100%;
            margin: 10px 0;
            display: flex;
            justify-content: center;
        }

        .label {
            text-align: center;
            margin: 3px 0;
            color: white;
        }

        .textarea {
            width: 300px;
            height: 100px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
            resize: none;
            display: block;
        }

        .qualifiers {
            width: 300px;
        }

        .extra-attributes-wrapper {
            display: flex;
            flex-direction: column;
        }

        .extra-attribute {
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            margin: 2px 0;
        }

        .extra-attribute-key {
            padding: 2px;
        }

        .extra-attribute-value {
            padding: 2px;
            -webkit-border-radius: 5px;
            -moz-border-radius: 5px;
            border-radius: 5px;
        }

    </style>
    <link rel="stylesheet" href="/static/css/custom/common.css"/>
    <link rel="stylesheet" href="/static/css/multiselect/multiselect.css"/>
</head>
<body>
<div class="details-popup" style="display: none" modal-state="closed">
    <div class="popup-form-item">
        <div style="display: flex; flex-direction: column">
            <label for="description" class="label">Description</label>
            <textarea name="description" id="description" class="textarea"></textarea>
        </div>
    </div>
    <div class="popup-form-item">
        <div style="display: flex; flex-direction: column">
            <label class="label">Qualifiers</label>
            <span class="qualifiers"></span>
        </div>
    </div>
    <div class="popup-form-item">
        <div style="display: flex; flex-direction: column">
            <label class="label">Extra Attributes</label>
            <div id="extra-attributes-wrapper" class="extra-attributes-wrapper">
            </div>
        </div>
    </div>
</div>
<div id="main-container">
    <div id="header-container">
        <div class="header-left-container">
            <#if isNewSnippet?boolean>
                <input id="newName" type="text" placeholder="New snippet name"/>
            <#else>
                <div id="snippetName">${snippetName}</div>
            </#if>
            <button id="snippetDetailsBtn" class="header-btn">Details</button>
        </div>
        <div>
            <button id="newSnippetBtn" class="header-btn">New Snippet</button>
            <button id="allSnippetsBtn" class="header-btn">All Snippets</button>
            <button id="saveBtn" class="header-btn">Save</button>
        </div>
    </div>
    <div id="editor-container"></div>
</div>

<script src="/static/js/jquery/jquery.min.js"></script>
<script src="/static/js/jquery/jquery-key.js"></script>
<script src="/static/js/notifyjs/notify.min.js"></script>
<script src="/static/js/requirejs/require.min.js"></script>
<script>
    window.snippet = {};
    window.snippetDetails = {};

    require.config({paths: {'vs': 'https://unpkg.com/monaco-editor@0.20.0/min/vs'}});
    window.MonacoEnvironment = {getWorkerUrl: () => proxy};

    let proxy = URL.createObjectURL(new Blob([`
	self.MonacoEnvironment = {
		baseUrl: 'https://unpkg.com/monaco-editor@0.20.0/min/'
	};
	importScripts('https://unpkg.com/monaco-editor@0.20.0/min/vs/base/worker/workerMain.js');
`], {type: 'text/javascript'}));
    require(["vs/editor/editor.main"], function () {
        window.editor = monaco.editor.create(document.getElementById('editor-container'), {
            value: [
                <#list codeLines as line>
                '${line}',
                </#list>
            ].join('\n'),
            language: 'javascript',
            theme: 'vs-dark'
        });
    });

    window.snippet.id = '${snippetId}';
    window.snippet.isNew = ${isNewSnippet};
</script>
<script src="/static/js/multiselect/bundle.min.js"></script>
<script src="/static/js/custom/common.js"></script>
<script src="/static/js/custom/editor/editor.js"></script>
</body>
</html>
