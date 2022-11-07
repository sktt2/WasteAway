import React, { Component } from "react"

import Paper from "@mui/material/Paper"
import Grid from "@mui/material/Grid"
import Divider from "@mui/material/Divider"
import TextField from "@mui/material/TextField"
import List from "@mui/material/List"
import ListItem from "@mui/material/ListItem"
import ListItemIcon from "@mui/material/ListItemIcon"
import ListItemText from "@mui/material/ListItemText"
import Avatar from "@mui/material/Avatar"
import SentimentVeryDissatisfiedIcon from "@mui/icons-material/SentimentVeryDissatisfied"
import ChatService from "../services/ChatService"
import StorageHelper from "../services/StorageHelper"
import { green, red } from "@mui/material/colors"

class ChatNavigator extends Component {
    constructor(props) {
        super(props)
        this.state = {
            mainData: [],
            data: [],
            username: "",
            userId: "",
        }
        this.goToChat = this.goToChat.bind(this)
    }

    async componentDidMount() {
        if (StorageHelper.getUser() == null) {
            this.props.history.push("/")
        }
        const res = await ChatService.getChatByUser(StorageHelper.getUsername())
        this.setState({ username: StorageHelper.getUsername(), userId: StorageHelper.getUserId() })
        this.setState({ mainData: res.data })
        this.setState({ data: res.data })
    }

    searchChat = (input) => {
        const dataSet = []
        if (input.target.value === "") {
            this.setState({ data: this.state.mainData })
            return
        }
        this.state.mainData.forEach((element) => {
            if (
                element.ownerUsername.toLowerCase().indexOf(input.target.value.toLowerCase()) > -1
            ) {
                if (element.ownerUsername === this.state.username) {
                    return
                }
                dataSet.push(element)
            } else if (
                element.takerUsername.toLowerCase().indexOf(input.target.value.toLowerCase()) > -1
            ) {
                if (element.takerUsername === this.state.username) {
                    return
                }
                dataSet.push(element)
            }
        })
        this.setState({ data: dataSet })
    }

    goToChat = (chat) => {
        this.props.history.push("/chat/" + chat.chatId)
    }

    render() {
        return (
            <div>
                <br />
                <Grid container component={Paper}>
                    <Grid item xs={12}>
                        <List>
                            <ListItem button>
                                <ListItemIcon>
                                    <Avatar alt={this.state.username} sx={{ bgcolor: green[500] }}>
                                        {this.state.username.charAt(0).toUpperCase()}
                                    </Avatar>
                                </ListItemIcon>
                                <ListItemText primary={this.state.username}></ListItemText>
                            </ListItem>
                        </List>
                        <Divider />
                        <Grid item xs={12} style={{ padding: "10px" }}>
                            <TextField
                                id="outlined-basic-email"
                                label="Search"
                                variant="outlined"
                                fullWidth
                                onChange={(input) => this.searchChat(input)}
                            />
                        </Grid>
                        <Divider />
                        <List>
                            {this.state.data.length === 0 ? (
                                <ListItem>
                                    <SentimentVeryDissatisfiedIcon />
                                    <ListItemText>No Chat Available</ListItemText>
                                </ListItem>
                            ) : (
                                this.state.data.map((data, i) => (
                                    <ListItem button onClick={() => this.goToChat(data)}>
                                        <ListItemIcon>
                                            <Avatar
                                                alt={
                                                    this.state.username === data.takerUsername
                                                        ? data.ownerUsername
                                                        : data.takerUsername
                                                }
                                                sx={{ bgcolor: red[500] }}>
                                                {(this.state.username === data.takerUsername
                                                    ? data.ownerUsername
                                                    : data.takerUsername
                                                )
                                                    .charAt(0)
                                                    .toUpperCase()}
                                            </Avatar>
                                        </ListItemIcon>
                                        <ListItemText
                                            primary={
                                                this.state.username === data.takerUsername
                                                    ? data.ownerUsername
                                                    : data.takerUsername
                                            }>
                                            {this.state.username === data.takerUsername
                                                ? data.ownerUsername
                                                : data.takerUsername}
                                        </ListItemText>
                                        <ListItemText
                                            secondary={
                                                this.state.userId === data.ownerId
                                                    ? "You're the owner"
                                                    : ""
                                            }
                                            align="right">
                                            {data.productName}
                                        </ListItemText>
                                        <ListItemIcon>
                                            <Avatar
                                                alt={data.productName}
                                                src={data.productImageUrl}
                                            />
                                        </ListItemIcon>
                                    </ListItem>
                                ))
                            )}
                        </List>
                    </Grid>
                </Grid>
            </div>
        )
    }
}

export default ChatNavigator
