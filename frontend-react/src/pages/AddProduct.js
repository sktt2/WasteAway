import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import Form from 'react-bootstrap/Form';
import bulbasaur from '../bulbasaur.jpg';

class AddProduct extends Component {
    constructor(props){
        super(props)
        this.state = {
            username: '',
            password: '',
            file: null
        }
        this.uploadImage = this.uploadImage.bind(this)
    }

    uploadImage(event) {
        this.setState({file: bulbasaur})
    }

    addProductClicked = (event) => {
        event.preventDefault();
        // let username = this.state.username;
        // let password = this.state.password;
        // let authHeader = window.btoa(username + ':' + password);
        // let user = {'username': username, 'authHeader': authHeader};
        // localStorage.setItem('user', JSON.stringify(user));
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
                                <input placeholder="Product name" name="productname" className="form-control"/>
                            </div>
                            <div>
                                <label>Description</label>
                                <Form.Control placeholder="Description" name="description" as="textarea" rows={3} />
                            </div>
                            <div>
                                <label>Condition</label>
                                <Form>
                                    <div key={`inline-"radio"`} className="mb-3">
                                        <Form.Check inline label="1" name="group1" type="radio" id={`inline-"radio"-1`}/>
                                        <Form.Check inline label="2" name="group1" type="radio" id={`inline-"radio"-2`}/>
                                        <Form.Check inline label="3" name="group1" type="radio" id={`inline-"radio"-3`}/>
                                        <Form.Check inline label="4" name="group1" type="radio" id={`inline-"radio"-4`}/>
                                        <Form.Check inline label="5" name="group1" type="radio" id={`inline-"radio"-5`}/>
                                        <Form.Check inline label="6" name="group1" type="radio" id={`inline-"radio"-6`}/>
                                        <Form.Check inline label="7" name="group1" type="radio" id={`inline-"radio"-7`}/>
                                        <Form.Check inline label="8" name="group1" type="radio" id={`inline-"radio"-8`}/>
                                        <Form.Check inline label="9" name="group1" type="radio" id={`inline-"radio"-9`}/>
                                        <Form.Check inline label="10" name="group1" type="radio" id={`inline-"radio"-10`}/>
                                    </div>
                                </Form>
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