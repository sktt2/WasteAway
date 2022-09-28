import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import Form from 'react-bootstrap/Form';
import ProductService from '../services/ProductService';

class AddProduct extends Component {
    constructor(props) {
        super(props)
        this.state = {
            productname: '',
            description: '',
            conditions: '',
            file: null
        }
        this.changeProductname = this.changeProductname.bind(this)
        this.changeDescription = this.changeDescription.bind(this)
        this.changeConditions = this.changeConditions.bind(this)
        this.uploadImage = this.uploadImage.bind(this)
        this.addProductClicked = this.addProductClicked.bind(this)
    }

    changeProductname(event) {
        this.setState({productname: event.target.value})
    }

    changeDescription(event) {
        this.setState({description: event.target.value})
    }

    changeConditions(event) {
        this.setState({conditions: event.target.value})
    }

    uploadImage(event) {
        this.setState({file: URL.createObjectURL(event.target.files[0])})
    }

    addProductClicked = (event) => {
        event.preventDefault();
        let name = this.state.productname
        let description = this.state.description
        let conditions = this.state.conditions
        let newProduct = {
            name: name,
            description: description,
            conditions: conditions,
            address: "hello from the underground dungeon",
            dateTime: new Date().toISOString(),
            user: {id: 1}
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
                                <input placeholder="Product name" name="productname" className="form-control" value={this.state.productname} onChange={this.changeProductname}/>
                            </div>
                            <div>
                                <label>Description</label>
                                <Form.Control placeholder="Description" name="description" as="textarea" rows={3} value={this.state.description} onChange={this.changeDescription}/>
                            </div>
                            <div>
                                <label>Condition of Product</label>
                                <Form.Control placeholder="Conditions" name="conditions" as="textarea" rows={3} value={this.state.conditions} onChange={this.changeConditions}/>
                            </div>
                            <div>
                                <Form.Group controlId="formFile" className="mb-3">
                                    <Form.Label>Product Image</Form.Label>
                                    <Form.Control type="file" accept="image/*" onChange={this.uploadImage}/>
                                </Form.Group>
                                <div>
                                    <img className="container-fluid w-100" src={this.state.file}/>
                                </div>
                            </div>
                            <br></br>
                            <button className="btn btn-success" onClick={this.addProductClicked}>Add Product</button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default AddProduct