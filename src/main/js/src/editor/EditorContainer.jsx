import React, {Component} from 'react';
import KeyBinder from "../global/components/KeyBinder";
import Editor from "./components/Editor";

class EditorContainer extends Component {

    constructor(props) {
        super(props);
        this.state = {
            editor: null
        }

        this.handleEditorDidMount = this.handleEditorDidMount.bind(this);
    }

    handleEditorDidMount(_, editor) {
        this.setState({editor: editor});
    }

    render() {
        return (
                <>
                    <Editor handleEditorDidMount={this.handleEditorDidMount}/>
                    <KeyBinder entries={[
                        {
                            name: 'Create new snippet',
                            keybinding: 'alt+n',
                            action: () => {
                            }
                        },
                        {
                            name: 'Save snippet',
                            keybinding: 'alt+s',
                            action: () => {
                            }
                        },
                        {
                            name: 'All snippets',
                            keybinding: 'alt+a',
                            action: () => {
                            }
                        },
                        {
                            name: 'Snippet Details',
                            keybinding: 'alt+q',
                            action: () => {
                            }
                        }
                    ]}/>
                </>
        );
    }
}

export default EditorContainer;
