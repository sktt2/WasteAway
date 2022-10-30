import React, { Component } from "react"

import Paper from '@mui/material/Paper';
import Box from '@mui/material/Box';
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
            id: this.props.match.params.id,
            chat: [],
            messages: [],
            message: "",
            chatter: "",
        }
        this.keyPress = this.keyPress.bind(this)
        this.addMessage = this.addMessage.bind(this)
    }

    async componentDidMount() {
        const res = await ChatService.getChatById(this.state.id)
        this.setState({ chat: res.data })
        this.setState({ chatter: (StorageHelper.getUsername() === this.state.chat.ownerUsername ?
            this.state.chat.takerUsername : this.state.chat.ownerUsername)})
        const res1 = await ChatService.getMessagesByChat(this.state.id)
        this.setState({ messages: res1.data })
        this.scrollToBottom();
        setTimeout(() => {
            window.location.reload(false)
        }, 100000)
    }
    
    scrollToBottom() {
        this.el.scrollIntoView({ behavior: 'smooth' });
    }

    keyPress = (event) => {
        if (event.keyCode === 13) {
            this.addMessage(event)
            this.setState({ message: "" })
        }
    }

    addMessage = (event) => {
        event.preventDefault()
        if (this.state.message === "") {
            return
        }
        let newMessage = {
            content: this.state.message,
            dateTime: new Date().toISOString(),
            senderUsername: StorageHelper.getUsername(),
            receiverUsername: this.state.chatter,
            chatId: this.state.id,
        }
        ChatService.addMessage(this.state.id, newMessage)
            .then((response) => {
                this.setState({ messages: [...this.state.messages, response.data] })
                this.scrollToBottom()
            })
            .catch(() => {
                this.props.history.push("/error")
            })
    }

    render() {
        return (
            <Box>
            <br/>
        <Grid container component={Paper} xs={12}>
            <Grid item xs={12}>
                <List>
                    <ListItem button /* Push to person's profile */>
                        <ListItemIcon>
                        <Avatar alt={this.state.chatter} 
                            src="https://material-ui.com/static/images/avatar/6.jpg" />
                        </ListItemIcon>
                        <ListItemText primary={this.state.chatter}></ListItemText>
                        <ListItemText secondary={this.state.chatter === this.state.chat.ownerUsername ? "owner" : ""} align="right">
                            {this.state.chat.productName}
                        </ListItemText>
                        <ListItemIcon>
                            <Avatar alt={this.state.chat.productName} src={this.state.chat.productImageUrl}/>
                        </ListItemIcon>
                    </ListItem>
                </List>
                <Divider />
                <List style={{maxHeight: 500, overflow: 'auto'}}>
                    {this.state.messages.map((data, i) => (StorageHelper.getUsername() === data.senderUsername ?
                        <ListItem>
                            <Grid container>
                                <Grid item xs={12}>
                                    <ListItemText align="right" primary={data.content}></ListItemText>
                                </Grid>
                                <Grid item xs={12}>
                                    <ListItemText align="right" secondary={data.dateTime}></ListItemText>
                                </Grid>
                            </Grid>
                        </ListItem>
                        :
                        <ListItem>
                            <Grid container>
                                <Grid item xs={12}>
                                    <ListItemText align="left" primary={data.content}></ListItemText>
                                </Grid>
                                <Grid item xs={12}>
                                    <ListItemText align="left" secondary={data.dateTime}></ListItemText>
                                </Grid>
                            </Grid>
                        </ListItem>
                    ))}
                    <div ref={el => { this.el = el; }} />
                </List>
                <Divider />
                <Grid container style={{padding: '20px'}}>
                    <Grid item xs={11}>
                        <TextField id="outlined-basic-email" label="Type Something" 
                            fullWidth value={this.state.message} 
                            onChange={(event) =>
                                this.setState({ message: event.target.value })
                            }
                            onKeyDown={(event) => this.keyPress(event)}/>
                    </Grid>
                    <Grid xs={1} align="right">
                        <Fab color="primary" aria-label="add" onClick={(event) => this.addMessage(event)}><SendIcon /></Fab>
                    </Grid>
                </Grid>
            </Grid>
        </Grid>
        <br></br>
      </Box>
        )
    }
}

export default Chat