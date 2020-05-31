import React from 'react';
import './global/styles/index.css';
import {Route, Switch, withRouter} from 'react-router';
import {ThemeProvider} from '@material-ui/core/styles';
import theme from './global/styles/globalTheme';
import CodeSnippetsContainer from "./code-snippets/CodeSnippetsContainer";
import EditorContainer from "./editor/EditorContainer";

function App() {
    return (
        <div className="app">
            <ThemeProvider theme={theme}>
                <Switch>
                    <Route exact path="/" component={CodeSnippetsContainer}/>
                    <Route path="/code-snippets" component={CodeSnippetsContainer}/>
                    <Route path="/editor" component={EditorContainer}/>
                </Switch>
            </ThemeProvider>
        </div>
    );
}

export default withRouter(App);
