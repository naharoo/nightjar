import React from 'react';
import MonacoEditor from "@monaco-editor/react";
import {makeStyles} from '@material-ui/core/styles';
import * as PropTypes from "prop-types";

const useStyles = makeStyles((theme) => ({
    editorContainer: {
        height: '100%'
    },
    editor: {
        paddingTop: theme.spacing(2)
    }
}));

const Editor = (props) => {
    const classes = useStyles();

    return (
            <div className={classes.editorContainer}>
                <MonacoEditor
                        theme='dark'
                        language='javascript'
                        editorDidMount={props.handleEditorDidMount}
                        className={classes.editor}
                />
            </div>
    )
}

Editor.propTypes = {
    handleEditorDidMount: PropTypes.func.isRequired
}
export default Editor;
