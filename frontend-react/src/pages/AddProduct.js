import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import Form from 'react-bootstrap/Form';
import ProductService from '../services/ProductService';

class AddProduct extends Component {
    constructor(props) {
        super(props)
        this.state = {
            productname: '',
            category: '',
            description: '',
            conditions: '',
            image: null,
            user: null
        }
        this.changeProductname = this.changeProductname.bind(this)
        this.changeCategory = this.changeCategory.bind(this)
        this.changeDescription = this.changeDescription.bind(this)
        this.changeConditions = this.changeConditions.bind(this)
        this.uploadImage = this.uploadImage.bind(this)
        this.validateInputs = this.validateInputs.bind(this)
        this.addProductClicked = this.addProductClicked.bind(this)
    }

    async componentDidMount() {
        var curruser = JSON.parse(localStorage.getItem('user'))
        const res = await ProductService.getUserByUsername(curruser.username)
        this.setState({user: res.data})
    }

    changeProductname(event) {
        this.setState({productname: event.target.value})
    }

    changeCategory(event) {
        this.setState({category: event.target.value})
    }

    changeDescription(event) {
        this.setState({description: event.target.value})
    }

    changeConditions(event) {
        this.setState({conditions: event.target.value})
    }

    uploadImage(event) {
        // save image into database
        this.setState({image: URL.createObjectURL(event.target.files[0])})
    }

    // Return proper error modal on invalid input
    validateInputs = (event) => {
        if (this.state.productname == '' || this.state.category == 'DEFAULT' || this.state.description == '' || this.state.conditions == '') {
            return false;
        } else {
            this.addProductClicked(event)
        }
    }

    addProductClicked = (event) => {
        event.preventDefault();
        let productname = this.state.productname
        let category = this.state.category
        if (category == 'DEFAULT') {
            category = null
        }
        let description = this.state.description
        let conditions = this.state.conditions
        let image = this.state.image
        let user = this.state.user
        let newProduct = {
            productName: productname,
            category: category,
            description: description,
            condition: conditions,
            dateTime: new Date().toISOString(),
            imageUrl: image,
            user: user
        }
        ProductService.addProduct(newProduct)
        .then((response) => {
            console.log(response)
            this.props.history.push('/products')
        })
        .catch((error) => {
            console.log(error)
            this.props.history.push('/error')
        })
    }

    render() {
        return (
            <div className="container">
                <br></br>
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <div className = "card-body">
                        <form>
                            <div>
                                <label>Product Name</label>
                                <Form.Control placeholder="Product name" name="productname" className="form-control" value={this.state.productname} onChange={this.changeProductname} required/>
                            </div>
                            <div>
                                <label>Category</label>
                                <Form.Select value={this.state.category} onChange={this.changeCategory}>
                                    <option value="DEFAULT" hidden>Select category</option>
                                    <option value="BOOKS">Books</option>
                                    <option value="ELECTRONICS">Electronics</option>
                                    <option value="FASHION">Fashion</option>
                                    <option value="FOOD">Food</option>
                                    <option value="TOYS">Toys</option>
                                    <option value="UTILITY">Utility</option>
                                    <option value="VIDEOGAMES">Video Games</option>
                                    <option value="OTHERS">Others</option>
                                </Form.Select>
                            </div>
                            <div>
                                <label>Description</label>
                                <Form.Control placeholder="Description" name="description" as="textarea" rows={3} value={this.state.description} onChange={this.changeDescription} required/>
                            </div>
                            <div>
                                <label>Condition of Product</label>
                                <Form.Control placeholder="Conditions" name="conditions" as="textarea" rows={3} value={this.state.conditions} onChange={this.changeConditions} required/>
                            </div>
                            <div>
                                <Form.Group controlId="formFile" className="mb-3">
                                    <Form.Label>Product Image</Form.Label>
                                    <Form.Control type="file" accept="image/*" onChange={this.uploadImage}/>
                                </Form.Group>
                                <div>
                                    <img className="container-fluid w-100" src={this.state.image}/>
                                </div>
                            </div>
                            <br></br>
                            <button className="btn btn-success" onClick={this.validateInputs}>Add Product</button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default AddProduct