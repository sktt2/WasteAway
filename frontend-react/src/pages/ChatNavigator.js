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
import SentimentVeryDissatisfiedIcon from '@mui/icons-material/SentimentVeryDissatisfied';
import ChatService from "../services/ChatService"
import StorageHelper from "../services/StorageHelper"

class ChatNavigator extends Component {
    constructor(props) {
        super(props)
        this.state = {
            data: [],
        }
        this.goToChat = this.goToChat.bind(this)
    }

    async componentDidMount() {
        const res = await ChatService.getChatByUser(StorageHelper.getUsername())
        this.setState({ data: res.data })
    }

    goToChat = (chat) => {
        this.props.history.push("/chat/" + chat.chatId)
    }

    render() {
        return (
            <div>
            <br/>
        <Grid container component={Paper}>
            <Grid item xs={12}>
                <List>
                    <ListItem button>
                        <ListItemIcon>
                        <Avatar alt={StorageHelper.getUsername()} src="https://material-ui.com/static/images/avatar/6.jpg" />
                        </ListItemIcon>
                        <ListItemText primary={StorageHelper.getUsername()}></ListItemText>
                    </ListItem>
                </List>
                <Divider />
                <Grid item xs={12} style={{padding: '10px'}}>
                    <TextField id="outlined-basic-email" label="Search" variant="outlined" fullWidth />
                </Grid>
                <Divider />
                <List>
                    {this.state.data.length === 0 ?
                        <ListItem>
                            <SentimentVeryDissatisfiedIcon/>
                            <ListItemText>No Chat Available</ListItemText>
                        </ListItem>

                    :
                    this.state.data.map((data, i) => (
                        <ListItem button onClick={() => this.goToChat(data)}>
                            <ListItemIcon>
                                <Avatar alt={StorageHelper.getUsername() === data.takerUsername ? data.ownerUsername : data.takerUsername} 
                                src={"https://material-ui.com/static/images/avatar/" + (i + 1) + ".jpg"}/>
                            </ListItemIcon>
                            <ListItemText primary={StorageHelper.getUsername() === data.takerUsername ? data.ownerUsername : data.takerUsername}>
                                {StorageHelper.getUsername() === data.takerUsername ? data.ownerUsername : data.takerUsername}
                            </ListItemText>
                            <ListItemText secondary={StorageHelper.getUserId() === data.ownerId ? "owner" : ""} align="right">
                                {data.productName}
                            </ListItemText>
                            <ListItemIcon>
                                <Avatar alt={data.productName} src={data.productImageUrl}/>
                            </ListItemIcon>
                        </ListItem>
                    ))}
                </List>
            </Grid>
        </Grid>
      </div>
        )
    }
}

export default ChatNavigator