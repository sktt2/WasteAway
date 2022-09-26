import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import Form from 'react-bootstrap/Form';
import bulbasaur from '../bulbasaur.jpg';

class AddProduct extends Component {
    constructor(props){
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
    }

    changeProductname(event) {
        this.setState({productname: event.target.value});
    }

    changeDescription(event) {
        this.setState({description: event.target.value});
    }

    changeConditions(event) {
        this.setState({conditions: event.target.value});
    }

    uploadImage(event) {
        this.setState({file: bulbasaur})
    }

    addProductClicked = (event) => {
        event.preventDefault();
        let productname = this.state.productname;
        let description = this.state.description;
        let conditions = this.state.conditions;
        // store values into product table with specific user details
        this.props.history.push('/product');
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
                                    <Form.Control type="file" onChange={this.uploadImage}/>
                                </Form.Group>
                                <div style={{width: 100}}>
                                    <img src={this.state.file}/>
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