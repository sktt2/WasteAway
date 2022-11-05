import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"
import Form from "react-bootstrap/Form"
import Button from "react-bootstrap/Button"
import ProductService from "../services/ProductService"
import StorageHelper from "../services/StorageHelper"

// Import CSS styling
import styles from "../styles/ComponentStyle.module.css"

class Recommendation extends Component {
    constructor(props) {
        super(props)
        this.state = {
            validated: "",
            recommend: ""
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.formSubmit = this.formSubmit.bind(this)
    }

    validateInputs = (event) => {
        const form = event.currentTarget
        if(form.checkValidity() === false) {
            event.preventDefault()
            event.stopPropagation()
        } else {
            this.formSubmit(event)
        }
        this.setState( {validated: "was-validated" })
    }

    formSubmit = (event) => {
        event.preventDefault()
        let newRecommendation = {
            username: StorageHelper.getUsername(),
            recommend: this.state.recommendation
        }
        ProductService.addRecommendation(newRecommendation)
            .then(() => {
                this.props.history.push("/products")
            })
            .catch(() => {
                this.props.history.push("/error")
            })
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
                                        Select a category
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <br></br>
                            <Button className="btn btn-success" type="submit">
                                Continue
                            </Button> 
                        </Form>
                    </div>
                </div>
            </div>
        );
    }
}

export default Recommendation