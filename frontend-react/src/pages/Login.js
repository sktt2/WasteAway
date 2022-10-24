import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"

import Form from "react-bootstrap/Form"
import Button from "react-bootstrap/Button"
import Alert from "react-bootstrap/Alert"
import AuthService from "../services/AuthService"

// Import CSS styling
import styles from "../styles/ComponentStyle.module.css"
import StorageHelper from "../services/StorageHelper"

class Login extends Component {
    constructor(props) {
        super(props)
        this.state = {
            showpassword: "password",
            isrememberme: false,
            errormessage: "",
            messageDisplay: false,
            validated: "",
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
        this.showPasswordClicked = this.showPasswordClicked.bind(this)
    }

    componentDidMount() {
        if (localStorage.hasOwnProperty("user")) {
            this.props.history.push("/")
        }
        if (localStorage.hasOwnProperty("rememberme")) {
            var retrieveUsername = JSON.parse(localStorage.getItem("rememberme"))
            this.setState({ username: retrieveUsername.username })
        }
    }

    // TODO
    // Import Formik library for custom form validation

    validateInputs = (event) => {
        const form = event.currentTarget
        if (form.checkValidity() === false) {
            event.preventDefault()
            event.stopPropagation()
        } else {
            this.loginClicked(event)
        }
        this.setState({ validated: "was-validated" })
    }

    loginClicked = (event) => {
        event.preventDefault()
        AuthService.signin(this.state.username, this.state.password)
            .then((response) => {
                StorageHelper.setUser(JSON.stringify(response.data))
                if (this.state.isrememberme) {
                    localStorage.setItem("rememberme", JSON.stringify(response.data))
                } else {
                    if (localStorage.hasOwnProperty("rememberme")) {
                        localStorage.removeItem("rememberme")
                    }
                }
                this.props.history.push("/products")
            })
            .catch((err) => {
                this.setState({
                    errormessage: "Username/Password is incorrect!",
                    messageDisplay: true,
                })
            })
    }

    showPasswordClicked = () => {
        if (this.state.showpassword === "password") {
            this.setState({ showpassword: "text" })
        } else {
            this.setState({ showpassword: "password" })
        }
    }

    isRememberMeClicked = () => {
        if (this.state.isrememberme === false) {
            this.setState({ isrememberme: true })
        } else {
            this.setState({ isrememberme: false })
        }
    }

    render() {
        return (
            <div className="container">
                <br></br>
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <div className={styles.inputCard}>
                        <Alert
                            variant="danger"
                            show={this.state.messageDisplay}
                            onClose={() => this.setState({ messageDisplay: false })}
                            dismissible>
                            {this.state.errormessage}
                        </Alert>
                        <Form
                            noValidate
                            className={this.state.validated}
                            onSubmit={this.validateInputs}>
                            <div>
                                <Form.Group>
                                    <Form.Label>Username</Form.Label>
                                    <Form.Control
                                        placeholder="Username"
                                        name="username"
                                        required
                                        value={this.state.username || ""}
                                        onChange={(event) =>
                                            this.setState({ username: event.target.value })
                                        }
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Enter a username.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label htmlFor="password-input">Password</Form.Label>
                                    <div className="input-group">
                                        <Form.Control
                                            type={this.state.showpassword}
                                            id="password-input"
                                            required
                                            value={this.state.password || ""}
                                            onChange={(event) =>
                                                this.setState({ password: event.target.value })
                                            }
                                            placeholder="Password"
                                        />
                                        <div className="input-group-append">
                                            <Button
                                                className="btn bg-transparent btn-outline-secondary"
                                                type="button"
                                                onClick={this.showPasswordClicked}>
                                                {this.state.showpassword === "password" ? (
                                                    <svg
                                                        xmlns="http://www.w3.org/2000/svg"
                                                        width="16"
                                                        height="16"
                                                        fill="currentColor"
                                                        className="bi bi-eye-slash"
                                                        viewBox="0 0 16 16">
                                                        <path d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7.028 7.028 0 0 0-2.79.588l.77.771A5.944 5.944 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.134 13.134 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755-.165.165-.337.328-.517.486l.708.709z" />
                                                        <path d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829l.822.822zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829z" />
                                                        <path d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z" />
                                                    </svg>
                                                ) : (
                                                    <svg
                                                        xmlns="http://www.w3.org/2000/svg"
                                                        width="16"
                                                        height="16"
                                                        fill="currentColor"
                                                        className="bi bi-eye"
                                                        viewBox="0 0 16 16">
                                                        <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z" />
                                                        <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z" />
                                                    </svg>
                                                )}
                                            </Button>
                                        </div>
                                        <Form.Control.Feedback type="invalid">
                                            Enter a password.
                                        </Form.Control.Feedback>
                                    </div>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Check
                                    id="flexCheckDefault"
                                    onChange={this.isRememberMeClicked}
                                    label={"Remember Me"}
                                />
                            </div>
                            <a href="/forgotpass" className="card-link">
                                Forgot password?
                            </a>
                            <br></br>
                            <Button className="btn btn-success" type="submit">
                                Login
                            </Button>{" "}
                            <Button
                                className="btn btn-success"
                                onClick={() => this.props.history.push("/register")}>
                                Register
                            </Button>
                        </Form>
                    </div>
                </div>
            </div>
        )
    }
}

export default Login
