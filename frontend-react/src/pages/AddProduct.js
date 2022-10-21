import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"

import Form from "react-bootstrap/Form"
import Button from "react-bootstrap/Button"
import ProductService from "../services/ProductService"

// Import CSS styling
import styles from "../styles/ComponentStyle.module.css"

class AddProduct extends Component {
    constructor(props) {
        super(props)
        this.state = {
            validated: "",
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.addProductClicked = this.addProductClicked.bind(this)
    }

    // TODO
    // Import Formik library for custom form validation

    validateInputs = (event) => {
        const form = event.currentTarget
        if (form.checkValidity() === false) {
            event.preventDefault()
            event.stopPropagation()
        } else {
            this.addProductClicked(event)
        }
        this.setState({ validated: "was-validated" })
    }

    addProductClicked = (event) => {
        event.preventDefault()
        let newProduct = {
            productName: this.state.productname,
            category: this.state.category,
            description: this.state.description,
            condition: this.state.conditions,
            dateTime: new Date().toISOString(),
            imageUrl: null,
            userId: JSON.parse(localStorage.getItem("user")).id,
        }
        ProductService.addProduct(newProduct)
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
                                    <Form.Label>Product Name</Form.Label>
                                    <Form.Control
                                        placeholder="Product name"
                                        name="productname"
                                        value={this.state.productname || ""}
                                        onChange={(event) =>
                                            this.setState({ productname: event.target.value })
                                        }
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Enter the product name.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <br></br>
                            <div>
                                <Form.Group>
                                    <Form.Label>Category</Form.Label>
                                    <Form.Select
                                        value={this.state.category}
                                        onChange={(event) =>
                                            this.setState({ category: event.target.value })
                                        }
                                        required>
                                        <option value="" hidden>
                                            Select category
                                        </option>
                                        <option value="Books">Books</option>
                                        <option value="Electronics">Electronics</option>
                                        <option value="Fashion">Fashion</option>
                                        <option value="Food">Food</option>
                                        <option value="Toys">Toys</option>
                                        <option value="Utility">Utility</option>
                                        <option value="Video Games">Video Games</option>
                                        <option value="Others">Others</option>
                                    </Form.Select>
                                    <Form.Control.Feedback type="invalid">
                                        Select a category.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <br></br>
                            <div>
                                <Form.Group>
                                    <Form.Label>Condition of Product</Form.Label>
                                    <Form.Select
                                        value={this.state.conditions}
                                        onChange={(event) =>
                                            this.setState({ conditions: event.target.value })
                                        }
                                        required>
                                        <option value="" hidden>
                                            Select condition
                                        </option>
                                        <option value="Mint">Mint</option>
                                        <option value="Near Mint">Near Mint</option>
                                        <option value="Excellent">Excellent</option>
                                        <option value="Very Good">Very Good</option>
                                        <option value="Good">Good</option>
                                        <option value="Fair">Fair</option>
                                        <option value="Poor">Poor</option>
                                    </Form.Select>
                                    <Form.Control.Feedback type="invalid">
                                        Select the product condition.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <br></br>
                            <div>
                                <Form.Group>
                                    <Form.Label>Description</Form.Label>
                                    <Form.Control
                                        placeholder="Description"
                                        name="description"
                                        as="textarea"
                                        rows={3}
                                        value={this.state.description}
                                        onChange={(event) =>
                                            this.setState({ description: event.target.value })
                                        }
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Enter the product description.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <br></br>
                            <div>
                                <Form.Group controlId="formFile" className="mb-3">
                                    <Form.Label>Product Image</Form.Label>
                                    <Form.Control
                                        type="file"
                                        accept="image/*"
                                        onChange={(event) =>
                                            this.setState({
                                                image: URL.createObjectURL(event.target.files[0]),
                                            })
                                        }
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Upload an image of the product.
                                    </Form.Control.Feedback>
                                </Form.Group>
                                <div>
                                    <img className="container-fluid w-100" src={this.state.image} />
                                </div>
                            </div>
                            <br></br>
                            <Button className="btn btn-success" type="submit">
                                Add Product
                            </Button>
                        </Form>
                    </div>
                </div>
            </div>
        )
    }
}

export default AddProduct
