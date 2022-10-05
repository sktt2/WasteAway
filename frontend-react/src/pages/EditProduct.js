import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import Form from 'react-bootstrap/Form';
import ProductService from '../services/ProductService';
import StorageHelper from "../services/StorageHelper"
import { Popover } from 'react-bootstrap';

class EditProduct extends Component {
    constructor(props) {
        super(props)
        this.state = {
            id: this.props.match.params.id,
            data: [],
            validated: '',
            url: "/product/edit/"
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.editDetails = this.editDetails.bind(this)
    }

    async componentDidMount(){
        const res = await ProductService.getProduct(this.state.id);
        this.setState({data: res.data})
        if (res.data.ownerName != StorageHelper.getUserName()){
            this.props.history.push('/profile')
        }
    }

    // Return proper error modal on invalid input
    validateInputs = (event) => {
        const form = event.currentTarget
        if (form.checkValidity() === false) {
            event.preventDefault()
            event.stopPropagation()
        } else {
            this.editDetails(event)
        }
        this.setState({validated: 'was-validated'})
    }

    editDetails = (event) => {
        event.preventDefault();
        let id = this.state.id
        // String productName, String condition, String dateTime, String category, String description
        let body = {
            id: this.state.id,
            productName: this.state.productname || this.state.data.productName,
            condition: this.state.conditions || this.state.data.condition,
            dateTime: new Date().toISOString(),
            category: this.state.category || this.state.data.category,
            description: this.state.description || this.state.data.description,
            imageUrl: this.state.image || this.state.data.imageUrl,
        }
        ProductService.updateProductDetail(id, body)
        .then(() => {
            document.getElementById("successMessage").style.display = "block";
            setTimeout(5000)
            window.location.reload('false')
            
        })
        .catch(() => {
            this.props.history.push('/error')
        })
    }

    render() {
        return (
            <div className="container">
                <br></br>
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <div className = "card-body">
                        <form noValidate className={this.state.validated} onSubmit={this.validateInputs}>
                            <div>
                                <Form.Group>
                                    <Form.Label>Product Name</Form.Label>
                                    <Form.Control placeholder="Product name" name="productname" value={this.state.productname || this.state.data.productName } onChange={event => this.setState({productname: event.target.value})} required/>
                                    <Form.Control.Feedback type="invalid">
                                        Please provide a product name.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Category</Form.Label>
                                    <Form.Select value={ this.state.category || this.state.data.category} onChange={event => this.setState({category: event.target.value})} required>
                                        <option value="" hidden>Select category</option>
                                        <option value="BOOKS">Books</option>
                                        <option value="ELECTRONICS">Electronics</option>
                                        <option value="FASHION">Fashion</option>
                                        <option value="FOOD">Food</option>
                                        <option value="TOYS">Toys</option>
                                        <option value="UTILITY">Utility</option>
                                        <option value="VIDEOGAMES">Video Games</option>
                                        <option value="OTHERS">Others</option>
                                    </Form.Select>
                                    <Form.Control.Feedback type="invalid">
                                        Select a category.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Description</Form.Label>
                                    <Form.Control placeholder="Description" name="description" as="textarea" rows={3} value={this.state.description || this.state.data.description} onChange={event => this.setState({description: event.target.value})} required/>
                                    <Form.Control.Feedback type="invalid">
                                        Please provide the product description.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Condition of Product</Form.Label>
                                    <Form.Control placeholder="Conditions" name="conditions" as="textarea" rows={3} value={this.state.condition || this.state.data.condition} onChange={event => this.setState({conditions: event.target.value})} required/>
                                    <Form.Control.Feedback type="invalid">
                                        Please provide the product condition.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group controlId="formFile" className="mb-3">
                                    <Form.Label>Product Image</Form.Label>
                                    <Form.Control type="file" accept="image/*" onChange={event => this.setState({image: URL.createObjectURL(event.target.files[0])})}/>
                                    <Form.Control.Feedback type="invalid">
                                        Please provide the product image.
                                    </Form.Control.Feedback>
                                </Form.Group>
                                <div>
                                    <img className="container-fluid w-100" src={this.state.image || this.state.data.imageUrl} value={this.state.image}/>
                                </div>
                            </div>
                            <br></br>
                            <div id="successMessage" style={{display:"none", color:"green"}} >SAVED SUCCESSFULLY</div>
                            <button className="btn btn-success" type="submit">SAVE</button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default EditProduct