import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"
import "react-multi-carousel/lib/styles.css"
import Card from "react-bootstrap/Card"
import Button from "react-bootstrap/Button"
import ProductService from "../services/ProductService"
import ChatService from "../services/ChatService"
import bulbasaur from "../bulbasaur.jpg"

// Import CSS styling
import styles from "../styles/ComponentStyle.module.css"
import StorageHelper from "../services/StorageHelper"
import { Alert } from "bootstrap"

class ProductDetail extends Component {
    constructor(props) {
        super(props)
        this.state = {
            id: this.props.match.params.id,
            data: [],
            isError: ""
        }
    }

    async componentDidMount() {
        const res = await ProductService.getProduct(this.state.id)
        this.setState({ data: res.data })
    }

    addChat = (event) => {
        event.preventDefault();
        let newChat = {
            takerId: StorageHelper.getUserId(),
            productId: this.state.id,
        }
        console.log(JSON.stringify(newChat))
        ChatService.createChat(newChat)
            .then(() => {
                this.props.history.push("/chat/1")
            })
            .catch((error) => {
                console.log(error.response.data.message) // Owner cannot ...
            })
    }

    render() {
        return (
            <div className="container mt-3" style={{ width: "55%" }}>
                <Card className={styles.productDetails}>
                    <Card.Img variant="top" src={this.state.data.imageUrl || bulbasaur}/>
                    <Card.Body>
                        <Card.Title>{this.state.data.productName}</Card.Title>
                        <Card.Text>
                            {this.state.data.condition}
                            <br></br>
                            {this.state.data.address}
                            <br></br>
                            {this.state.data.description}
                            <br></br>
                            {this.state.data.ownerName}
                        </Card.Text>
                        <Button onClick={this.addChat}>GIMME</Button>
                    </Card.Body>
                </Card>
            </div>
        )
    }
}

export default ProductDetail
