import React, {useState, useEffect} from "react";
import {makeStyles} from '@material-ui/core/styles';
import Fab from "@material-ui/core/Fab";
import {List, ListItem} from '@material-ui/core';
import Paper from "@material-ui/core/Paper";
import ClickAwayListener from "@material-ui/core/ClickAwayListener";
import Zoom from "@material-ui/core/Zoom";
import CloseIcon from '@material-ui/icons/Close';
import KeyboardIcon from '@material-ui/icons/Keyboard';
import PropTypes from 'prop-types';

const Mousetrap = require("mousetrap");

const useStyles = makeStyles((theme) => ({
    keyBinderContainer: {
        position: 'absolute',
        bottom: theme.spacing(2),
        right: theme.spacing(2),
        zIndex: 100,
    },
    fab: {
        float: 'right'
    },
    paper: {
        padding: theme.spacing(1),
        marginBottom: theme.spacing(2)
    }
}))

const KeyBinder = (props) => {
    const [isOpen, handleToggleEvent] = useState(false);

    useEffect(() => {
        Mousetrap.bind('alt+k', () => handleToggleEvent(!isOpen));
        props.entries.forEach(entry => {
            Mousetrap.bind(entry.keybinding, entry.action);
        })

        return () => {
            Mousetrap.unbind('alt+k');
            props.entries.forEach(entry => {
                Mousetrap.unbind(entry.keybinding);
            })
        }
    });

    const classes = useStyles();

    return (
            <ClickAwayListener onClickAway={() => handleToggleEvent(false)}>
                <div className={classes.keyBinderContainer}>
                    <Zoom in={isOpen}>
                        <Paper
                                className={classes.paper}
                                elevation={3}
                        >
                            <List>
                                <ListItem button key={'alt+k - Open/Close KeyBinder'}>
                                    alt+k - Open/Close KeyBinder
                                </ListItem>
                                {
                                    props.entries.map((entry) => (
                                            <ListItem button key={entry.keybinding + entry.name}>
                                                {entry.keybinding} - {entry.name}
                                            </ListItem>
                                    ))
                                }
                            </List>
                        </Paper>
                    </Zoom>

                    <Fab
                            className={classes.fab}
                            onClick={() => handleToggleEvent(!isOpen)}
                            color="secondary"
                            aria-label="KeyBinder"
                    >
                        {isOpen ?
                                <CloseIcon/>
                                :
                                <KeyboardIcon/>
                        }
                    </Fab>
                </div>
            </ClickAwayListener>
    );
};

KeyBinder.propTypes = {
    entries: PropTypes.arrayOf(
            PropTypes.shape({
                name: PropTypes.string.isRequired,
                keybinding: PropTypes.string.isRequired,
                action: PropTypes.func.isRequired
            })
    ).isRequired
}
export default KeyBinder;
