import React, { Component } from "react"

import Paper from '@mui/material/Paper';
import Grid from '@mui/material/Grid';
import Divider from '@mui/material/Divider';
import TextField from '@mui/material/TextField';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import Avatar from '@mui/material/Avatar';
import Fab from '@mui/material/Fab';
import SendIcon from '@mui/icons-material/Send';
import ChatService from "../services/ChatService"
import StorageHelper from "../services/StorageHelper"

class Chat extends Component {
    constructor(props) {
        super(props)
        this.state = {
            message: "",
        }
        this.addMessage = this.addMessage.bind(this)
    }

    addMessage = (event) => {
        event.preventDefault()
        let newMessage = {
            content: this.state.message,
            dateTime: new Date().toISOString(),
            senderUsername: StorageHelper.getUsername(),
            userId: StorageHelper.getUserId(),
        }
        console.log(newMessage)
        ChatService.addMessage(newMessage)
            .then(() => {
                this.forceUpdate()
            })
            .catch(() => {
                this.props.history.push("/error")
            })
    }

    render() {
        return (
            <div>
            <br/>
        <Grid container component={Paper}>
            <Grid item xs={12}>
                <List>
                    <ListItem key="1">
                        <Grid container>
                            <Grid item xs={12}>
                                <ListItemText align="right" primary="Hey man, What's up ?"></ListItemText>
                            </Grid>
                            <Grid item xs={12}>
                                <ListItemText align="right" secondary="09:30"></ListItemText>
                            </Grid>
                        </Grid>
                    </ListItem>
                    <ListItem key="2">
                        <Grid container>
                            <Grid item xs={12}>
                                <ListItemText align="left" primary="Hey, Iam Good! What about you ?"></ListItemText>
                            </Grid>
                            <Grid item xs={12}>
                                <ListItemText align="left" secondary="09:31"></ListItemText>
                            </Grid>
                        </Grid>
                    </ListItem>
                    <ListItem key="3">
                        <Grid container>
                            <Grid item xs={12}>
                                <ListItemText align="right" primary="Cool. i am good, let's catch up!"></ListItemText>
                            </Grid>
                            <Grid item xs={12}>
                                <ListItemText align="right" secondary="10:30"></ListItemText>
                            </Grid>
                        </Grid>
                    </ListItem>
                </List>
                <Divider />
                <Grid container style={{padding: '20px'}}>
                    <Grid item xs={11}>
                        <TextField id="outlined-basic-email" label="Type Something" 
                            fullWidth value={this.state.message} 
                            onChange={(event) =>
                                this.setState({ message: event.target.value })
                            }/>
                    </Grid>
                    <Grid xs={1} align="right">
                        <Fab color="primary" aria-label="add" onClick={this.addMessage}><SendIcon /></Fab>
                    </Grid>
                </Grid>
            </Grid>
        </Grid>
      </div>
        )
    }
}

export default Chat