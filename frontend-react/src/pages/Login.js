import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"

import Form from "react-bootstrap/Form"
import Button from "react-bootstrap/Button"
import Alert from "react-bootstrap/Alert"
import AuthService from "../services/AuthService"
import VisibilityIcon from "@mui/icons-material/Visibility"
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff"
import IconButton from "@mui/material/IconButton"
// Import CSS styling
import styles from "../styles/ComponentStyle.module.css"
import StorageHelper from "../services/StorageHelper"

class Login extends Component {
    constructor(props) {
        super(props)
        this.state = {
            showpassword: false,
            isrememberme: false,
            errormessage: "",
            messageDisplay: false,
            validated: "",
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.loginClicked = this.loginClicked.bind(this)
        this.toggleShowPassword = this.toggleShowPassword.bind(this)
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

    toggleShowPassword = () => {
        this.setState({ showpassword: !this.state.showpassword })
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
                                            type={this.state.showpassword ? "text" : "password"}
                                            id="password-input"
                                            required
                                            value={this.state.password || ""}
                                            onChange={(event) =>
                                                this.setState({ password: event.target.value })
                                            }
                                            placeholder="Password"
                                        />
                                        <div className="input-group-append">
                                            <IconButton
                                                aria-label="showpassword"
                                                onClick={() => this.toggleShowPassword()}
                                                sx={{
                                                    borderRadius: "0px 4px 4px 0px",
                                                    border: "1px solid rgba(195,202,210)",
                                                    borderWidth: "1px 1px 1px 0px",
                                                }}>
                                                {this.state.showpassword ? (
                                                    <VisibilityIcon />
                                                ) : (
                                                    <VisibilityOffIcon />
                                                )}
                                            </IconButton>
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
