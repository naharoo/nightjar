<!DOCTYPE html>
<html>
<head>
    <meta content="IE=edge" http-equiv="X-UA-Compatible"/>
    <meta content="text/html;charset=utf-8" http-equiv="Content-Type">
    <link rel="icon" type="image/png" href="/static/images/share.png">

    <style>
        html, body {
            height: 100%;
            padding: 0;
            margin: 0;
        }

        #main-container {
            height: 100%;
            display: flex;
            flex-direction: column;
        }

        #editor-container {
            flex-grow: 2;
            padding: 5px 0 0 0;
            background-color: #1e1e1e;
        }

        #result-container {
            flex-grow: 1;
        }
    </style>
</head>
<body>
<div id="main-container">
    <div id="editor-container"></div>
    <div id="result-container">
        Yohoho!
    </div>
</div>

<script crossorigin="anonymous" src="/static/js/requirejs/require.min.js"></script>
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
        let editor = monaco.editor.create(document.getElementById('editor-container'), {
            value: [
                <#list codeLines as line>
                '${line}',
                </#list>
            ].join('\n'),
            language: 'javascript',
            theme: 'vs-dark'
        });
    });
</script>
</body>
</html>
