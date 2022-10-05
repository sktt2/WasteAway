import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";

import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import Alert from "react-bootstrap/Alert";
import AuthService from "../services/AuthService";

class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {
            validated: "",
            errormessage: "",
            messageDisplay: false,
        };
        this.validateInputs = this.validateInputs.bind(this);
        this.registerClicked = this.registerClicked.bind(this);
    }

    // TODO
    // Import Formik library for custom form validation

    passwordEqualsConfirm() {
        return this.state.password === this.state.confirmpassword
    }

    validateInputs = (event) => {
        const form = event.currentTarget;
        if (form.checkValidity() === false || !this.passwordEqualsConfirm()) {
            event.preventDefault();
            event.stopPropagation();
        } else {
            this.registerClicked(event);
        }
        this.setState({ validated: "was-validated" });
    };

    registerClicked = (event) => {
        event.preventDefault();
        let username = this.state.username;
        let name = this.state.name;
        let email = this.state.email;
        let password = this.state.password;
        let address = this.state.address;
        let mobile = this.state.mobile;
        AuthService.register(username, name, email, password, address, mobile)
            .then(() => {
                AuthService.signin(username, password)
                    .then((response) => {
                        localStorage.setItem("user", JSON.stringify(response.data));
                        this.props.history.push("/product");
                    })
            })
            .catch(error => {
                // Read body of error response and display message get to alert
                console.log(error.response.data)
                this.setState({ 
                    errormessage: "Username/Email has already been used!",
                    messageDisplay: true,
                });
            })
    };

    render() {
        return (
            <div className="container">
                <br></br>
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <div className="card-body">
                        <Alert variant="danger" show={this.state.messageDisplay} onClose={() => this.setState({ messageDisplay: false, })} dismissible>
                            {/* Map each errormessage from error.response.data[i] here */}
                            {this.state.errormessage}
                        </Alert>
                        <Form noValidate className={this.state.validated} onSubmit={this.validateInputs}>
                            <div>
                                <Form.Group>
                                    <Form.Label>Username</Form.Label>
                                    <Form.Control placeholder="Username" name="username" required value={this.state.username || ""}
                                        onChange={(event) => this.setState({ username: event.target.value, })}/>
                                    <Form.Control.Feedback type="invalid">
                                        Enter a username.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Name</Form.Label>
                                    <Form.Control placeholder="Name" name="name" required value={this.state.name || ""}
                                        onChange={(event) => this.setState({ name: event.target.value, })}/>
                                    <Form.Control.Feedback type="invalid">
                                        Enter your name.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Email</Form.Label>
                                    <Form.Control type="email" placeholder="Email" name="email" required value={this.state.email || ""}
                                        onChange={(event) => this.setState({ email: event.target.value, })}/>
                                    <Form.Control.Feedback type="invalid">
                                        Enter your email.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Password</Form.Label>
                                    <Form.Control type="password" placeholder="Password" name="password" required value={this.state.password || ""}
                                        onChange={(event) => this.setState({ password: event.target.value, })}/>
                                    <Form.Control.Feedback type="invalid">
                                        Enter a password.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Confirm Password</Form.Label>
                                    <Form.Control type="password" placeholder="Confirm Password" name="confirmpassword" required value={this.state.confirmpassword || ""}
                                        onChange={(event) => this.setState({ confirmpassword: event.target.value, })}/>
                                    <Form.Control.Feedback type="invalid">
                                        Enter the above password.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                <Form.Label>Home Address</Form.Label>
                                <Form.Control placeholder="Address" name="address" required value={this.state.address || ""} 
                                    onChange={(event) => this.setState({ address: event.target.value, })}/>
                                <Form.Control.Feedback type="invalid">
                                    Enter your home address.
                                </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Mobile Number</Form.Label>
                                    <Form.Control placeholder="Mobile Number" name="mobile" required value={this.state.mobile || ""}
                                        onChange={(event) => this.setState({ mobile: event.target.value, })}/>
                                    <Form.Control.Feedback type="invalid">
                                        Enter your mobile number.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <br></br>
                            <Button className="btn btn-success" type="submit">Register</Button>{" "}
                            <Button className="btn btn-success" onClick={() => this.props.history.push("/login")}>
                                Return to Login
                            </Button>
                        </Form>
                    </div>
                </div>
            </div>
        );
    }
}

export default Register;
