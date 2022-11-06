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
import PanToolIcon from '@mui/icons-material/PanTool';
import SendIcon from '@mui/icons-material/Send';
import ChatService from "../services/ChatService"
import ProductService from "../services/ProductService"
import StorageHelper from "../services/StorageHelper"
import { red } from "@mui/material/colors"
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';

class Chat extends Component {
    constructor(props) {
        super(props)
        this.state = {
            id: this.props.match.params.id,
            chat: [],
            messages: [],
            message: "",
            chatter: "",
            givenAway: false,
            open: false,
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
        const res2 = await ProductService.getBooleanIfProductGAExist(this.state.chat.productId)
        this.setState({ givenAway: res2.data })
        this.scrollToBottom()
        this.refreshChat()
    }
    
    scrollToBottom() {
        this.el.scrollIntoView({ behavior: 'smooth' });
    }

    async refreshChat() {
        const res1 = await ChatService.getMessagesByChat(this.state.id)
        this.setState({ messages: res1.data })
        setTimeout(() => {
            this.refreshChat()
        }, 1000)
    }

    convertDateTime = (dateTime) => {
        let newDateTime = (new Date(dateTime)).toString()
        let dateTimeWithoutTimezone = newDateTime.split(" G")[0]
        return dateTimeWithoutTimezone
    }

    keyPress = (event) => {
        if (event.keyCode === 13) {
            this.addMessage(event)
            this.setState({ message: "" })
        }
    }

    handleClickOpen = () => {
        this.setState({ open: true })
    }
    
    handleClose = () => {
        this.setState({ open: false })
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

    giveProduct = (event) => {
        event.preventDefault()
        ProductService.giveProduct({
            receiverUsername: this.state.chat.takerUsername,
            productId: this.state.chat.productId,
        }).then(() => {
            this.setState({ givenAway: true })
            this.handleClose()
        })
    }

    render() {
        return (
            <Box>
                <Dialog
                    open={this.state.open}
                    onClose={() => this.handleClose()}
                >
                <DialogTitle id="alert-dialog-title">
                    {"Give product to user?"}
                </DialogTitle>
                <DialogActions>
                <Button onClick={() => this.handleClose()}>No</Button>
                <Button onClick={(event) => this.giveProduct(event)} autoFocus>
                    Yes
                </Button>
                </DialogActions>
            </Dialog>
            <br/>
                <Grid container component={Paper} xs={12}>
                    <Grid item xs={12}>
                        <List>
                            <ListItem button /* Push to person's profile */>
                                <ListItemIcon>
                                <Avatar alt={this.state.chatter} sx={{ bgcolor: red[500] }}>
                                    {this.state.chatter.charAt(0).toUpperCase()}
                                </Avatar>
                                </ListItemIcon>
                                <ListItemText primary={this.state.chatter}></ListItemText>
                                <ListItemText secondary={StorageHelper.getUsername() === this.state.chat.ownerUsername ? "You're the owner" : ""} align="right">
                                    {this.state.chat.productName}
                                </ListItemText>
                                <ListItemIcon>
                                    <Avatar alt={this.state.chat.productName} src={this.state.chat.productImageUrl}/>
                                </ListItemIcon>
                            </ListItem>
                        </List>
                        <Divider />
                        <List style={{maxHeight: 500, minHeight: 500, overflow: 'auto'}}>
                            {this.state.messages.map((data, i) => (
                                <ListItem>
                                    <Grid container>
                                        <Grid item xs={12}>
                                            <ListItemText align={StorageHelper.getUsername() === data.senderUsername ? "right" : "left"} primary={data.content}></ListItemText>
                                        </Grid>
                                        <Grid item xs={12}>
                                            <ListItemText align={StorageHelper.getUsername() === data.senderUsername ? "right" : "left"} secondary={this.convertDateTime(data.dateTime)}></ListItemText>
                                        </Grid>
                                    </Grid>
                                </ListItem>
                            ))}
                            <div ref={el => { this.el = el; }} />
                        </List>
                        <Divider />
                        <Grid container style={{padding: '20px'}}>
                            {StorageHelper.getUsername() === this.state.chat.takerUsername ? "" : 
                                <Grid xs={2} align="left">
                                    <Fab color="secondary" variant="extended" onClick={() => this.setState({ open: true })} 
                                        sx={{ ml: '20px' }} disabled={this.state.givenAway}>
                                        <PanToolIcon sx={{ mr: 1 }} />
                                        Give Away
                                    </Fab>
                                </Grid>
                            }
                            <Grid item xs={StorageHelper.getUsername() === this.state.chat.takerUsername ? "11" : "9"}>
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