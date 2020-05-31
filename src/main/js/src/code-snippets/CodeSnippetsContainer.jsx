import React, {Component} from 'react';
import KeyBinder from "../global/components/KeyBinder";

class CodeSnippetsContainer extends Component {

    render() {
        return (
            <>
                <div>
                    Code Snippets
                </div>
                <KeyBinder entries={[
                    {
                        name: 'Create new snippet',
                        keybinding: 'alt+n',
                        action: () => {console.log("Yohohooooo!")}
                    },
                    {
                        name: 'Focus on search',
                        keybinding: 'alt+s',
                        action: () => {}
                    }
                ]}/>
            </>
        );
    }
}

export default CodeSnippetsContainer;
