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
        }

        #header-container {
            width: 100%;
            height: 7%;
            background-color: #1e1e1e;

            display: flex;
            flex-direction: row;
            align-items: center;
            justify-content: space-between;
        }

        #snippetName {
            padding: 5px;
            margin: 5px;
            width: 200px;
            color: white;
            border: 1px solid grey;
            border-radius: 4px;
        }

        #newName {
            padding: 7px;
            margin: 5px;
            width: 200px;
            border: 1px solid white;
            border-radius: 4px;
            background-color: #1e1e1e;
            color: white;
        }

        #saveBtn {
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
            height: 95%;
            padding: 5px 0 0 0;
            background-color: #1e1e1e;
        }
    </style>
</head>
<body>
<div id="main-container">
    <div id="header-container">
        <#if isNewSnippet?boolean>
            <input id="newName" type="text" placeholder="New snippet name"/>
        <#else>
            <span id="snippetName">Name: ${snippetName}</span>
        </#if>
        <button id="saveBtn">Save</button>
    </div>
    <div id="editor-container"></div>
</div>

<script src="/static/js/requirejs/require.min.js"></script>
<script src="/static/js/jquery/jquery.min.js"></script>
<script>
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
        window.editor.snippetId = '${snippetId}';
        window.editor.isNewSnippet = ${isNewSnippet};
    });
</script>
<script src="/static/js/custom/editor/editor.js"></script>
</body>
</html>
