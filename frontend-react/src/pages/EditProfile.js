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
        console.log(typeof body.phone)
        UserService.updateUser(body)
            .then(async () => {
                document.getElementById("successMessage").style.display = "block"
                const newUser = await UserService.getUser(StorageHelper.getUsername())
                console.log(newUser)
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
                                            {
                                                this.setState({ name: event.target.value })
                                                this.validateInputs(event)
                                            }
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
                                        type="email"
                                        placeholder="Email"
                                        name="Email"
                                        value={this.state.email}
                                        onChange={(event) =>
                                            {
                                                this.setState({ email: event.target.value })
                                                this.validateInputs(event)
                                            }
                                        }
                                        pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$"
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Invalid email address
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
                                            {
                                                this.setState({ address: event.target.value })
                                                this.validateInputs(event)
                                            }
                                        }
                                        pattern= "^.+?(?<!\d)\d{6}$"
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Please input address in this format:  11 TOA PAYOH S111111
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
                                            {
                                                this.setState({ phoneNumber: event.target.value })
                                                this.validateInputs(event)
                                            }
                                        }
                                        pattern="([8-9]{1})([0-9]{7})$"
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Invalid phone number
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div id="successMessage" style={{ display: "none", color: "green" }}>
                                SAVED SUCCESSFULLY
                            </div>
                            <button
                                className="btn btn-success"
                                type="submit"
                                style={{ margin: "20px 0px 0px 0px" }}
                                onClick={(event) => { this.editDetails(event) }}>
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
