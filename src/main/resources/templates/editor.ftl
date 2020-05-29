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
            background-color: #1e1e1e;

            max-height: 7%;

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
            height: 93%;
            overflow: hidden;
            background-color: #1e1e1e;
        }
    </style>
    <link rel="stylesheet" href="/static/css/custom/common.css"/>
</head>
<body>
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

        let prevHeight = 0
        const updateEditorHeight = () => {
            const editorElement = editor.getDomNode()

            if (!editorElement) {
                return
            }

            const lineHeight = editor.getOption(monaco.editor.EditorOption.lineHeight)
            const lineCount = editor.getModel()?.getLineCount() || 1
            const height = editor.getTopForLineNumber(lineCount + 1) + lineHeight

            if (prevHeight !== height) {
                prevHeight = height
                editorElement.style.height = `${'$'}{height}px`
                editor.layout()
            }
        }
        window.editor.onDidChangeModelDecorations(() => {
            updateEditorHeight() // typing
            requestAnimationFrame(updateEditorHeight) // folding
        })

        window.snippet.id = '${snippetId}';
        window.snippet.isNew = ${isNewSnippet};
    });
</script>
<script src="/static/js/custom/common.js"></script>
<script src="/static/js/custom/editor/editor.js"></script>
</body>
</html>
