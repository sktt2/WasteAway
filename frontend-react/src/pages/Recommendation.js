import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"

import Form from "react-bootstrap/Form"
import Button from "react-bootstrap/Button"
import Alert from "react-bootstrap/Alert"
import AuthService from "../services/AuthService"

// Import CSS styling
import styles from "../styles/ComponentStyle.module.css"
import StorageHelper from "../services/StorageHelper"

class Recommendation extends Component {
    constructor(props) {
        super(props)
        this.state = {
            validated: ""
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.registerClicked = this.registerClicked.bind(this)

    }

    validateInputs = (event) => {
        const form = event.currentTarget
        if(form.checkValidity() === false) {
            event.preventDefault()
            event.stopPropagation()
        } else {
            this.registerClicked(event)
        }
        this.setState( {validated: "was-validated" })
    }

    registerClicked = (event) => {
        event.preventDefault()
        let recommend = this.state.recommendation;
        /*
        UserService.addRecommendation(Username, recommend)
            .then(() => {
                this.props.history.push("/products")
            })
            .catch(() => {
                this.props.history.push("/error")
            })
        */
    }

    render() {
        return (
            <div className="container">
                <br></br>
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <div className={styles.inputCard}>
                        <Form
                            noValidate
                            className={this.state.validated}
                            onSubmit={this.validateInputs}>
                            <div>
                                <Form.Group>
                                    <Form.Label>What are you looking for?</Form.Label>
                                    <Form.Select
                                        value={this.state.recommend}
                                        onChange={(event) =>
                                            this.setState({ recommend: event.target.value })
                                        }
                                        required>
                                        <option value="" hidden>Select category</option>
                                        <option value="NONE">None</option>
                                        <option value="BOOKS">Books</option>
                                        <option value="ELECTRONICS">Electronics</option>
                                        <option value="FASHION">Fashion</option>
                                        <option value="FOOD">Food</option>
                                        <option value="TOYS">Toys</option>
                                        <option value="UTILITY">Utility</option>
                                        <option value="VIDEO GAMES">Video Games</option>
                                    </Form.Select>
                                    <Form.Control.Feedback type="invalid">
                                        Select a category.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <br></br>
                            <Button className="btn btn-success" type="submit">
                                Finish Registration
                            </Button> 
                        </Form>
                    </div>
                </div>
            </div>
        );
    }
}

export default Recommendation