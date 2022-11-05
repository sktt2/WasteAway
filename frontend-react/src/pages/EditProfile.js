import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"

import Form from "react-bootstrap/Form"
import UserService from "../services/UserService"
import StorageHelper from "../services/StorageHelper"

// Import CSS styling
import styles from "../styles/ComponentStyle.module.css"

class EditProfile extends Component {
    constructor(props) {
        super(props)
        this.state = {
            user: {},
            validated: "",
            url: "/editprofile",
            name: "",
            email: "",
            address: "",
            phoneNumber: 0,
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.editDetails = this.editDetails.bind(this)
    }

    async componentDidMount() {
        if (StorageHelper.getUser() == null) {
            this.props.history.push("/")
        }
        const user = StorageHelper.getUser()
        this.setState({ name: user.userInfo.name })
        this.setState({ email: user.email })
        this.setState({ address: user.userInfo.address })
        this.setState({ phoneNumber: user.userInfo.phoneNumber })
        await this.setState({ user: StorageHelper.getUser() })
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
        this.setState({ validated: "was-validated" })
    }

    editDetails = (event) => {
        event.preventDefault()
        let body = {
            username: StorageHelper.getUsername(),
            name: this.state.name,
            email: this.state.email,
            address: this.state.address,
            phoneNumber: parseInt(this.state.phoneNumber),
        }
        UserService.updateUser(body)
            .then(async () => {
                document.getElementById("successMessage").style.display = "block"
                const newUser = await UserService.getUser(StorageHelper.getUsername())
                StorageHelper.setUser(JSON.stringify(newUser.data))
                setTimeout(function () {
                    window.location.reload("false")
                }, 1000)
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
                        <form
                            noValidate
                            className={this.state.validated}
                            onSubmit={this.validateInputs}>
                            <div>
                                <Form.Group>
                                    <Form.Label>Name</Form.Label>
                                    <Form.Control
                                        placeholder="Name"
                                        name="Name"
                                        value={this.state.name}
                                        onChange={(event) =>
                                            this.setState({ name: event.target.value })
                                        }
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Name cannot be blank.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>email</Form.Label>
                                    <Form.Control
                                        placeholder="Email"
                                        name="Email"
                                        value={this.state.email}
                                        onChange={(event) =>
                                            this.setState({ email: event.target.value })
                                        }
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Email cannot be blank
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Address</Form.Label>
                                    <Form.Control
                                        placeholder="Address"
                                        name="Address"
                                        value={this.state.address}
                                        onChange={(event) =>
                                            this.setState({ address: event.target.value })
                                        }
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Address cannot be blank.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Phone</Form.Label>
                                    <Form.Control
                                        placeholder="Phone number"
                                        name="Phone"
                                        value={this.state.phoneNumber}
                                        onChange={(event) =>
                                            this.setState({ phoneNumber: event.target.value })
                                        }
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Phone Number cannot be blank.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div id="successMessage" style={{ display: "none", color: "green" }}>
                                SAVED SUCCESSFULLY
                            </div>
                            <button
                                className="btn btn-success"
                                type="submit"
                                style={{ margin: "20px 0px 0px 0px" }}>
                                SAVE
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        )
    }
}

export default EditProfile
