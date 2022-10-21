import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"

import Form from "react-bootstrap/Form"
import Button from "react-bootstrap/Button"
import ChatService from "../services/ChatService"

class Chat extends Component {
    constructor(props) {
        super(props)
        this.state = {
        }
    }

    async componentDidMount() {

    }

    render() {
        return (
            <Form className="container" onSubmit={this.addChat}>
                <br></br>
                <Button type="submit">Add Chat</Button>
            </Form>
        )
    }
}

export default Chat