import React, { Component } from "react"
import { Box } from "@mui/system"

import {
    FormControl,
    FormHelperText,
    Grid,
    InputLabel,
    OutlinedInput,
    Button,
    Alert,
} from "@mui/material"
import UserService from "../services/UserService"
import StorageHelper from "../services/StorageHelper"

class EditProfile extends Component {
    constructor(props) {
        super(props)
        this.state = {
            user: {},
            validated: "",
            url: "/editprofile",
            displaySuccess: false,
            displayError: false,
            errorMessage: "",
            messageDisplay: {
                name: false,
                email: false,
                address: false,
                mobile: false,
            },
            name: "",
            email: "",
            address: "",
            mobile: "",
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.editDetails = this.editDetails.bind(this)
        this.handleChange = this.handleChange.bind(this)
    }

    async componentDidMount() {
        if (StorageHelper.getUser() == null) {
            this.props.history.push("/")
        }
        const user = StorageHelper.getUser()
        this.setState({ name: user.userInfo.name })
        this.setState({ email: user.email })
        this.setState({ address: user.userInfo.address })
        console.log(typeof user.userInfo.phoneNumber, user.userInfo.phoneNumber)
        this.setState({ mobile: "" + user.userInfo.phoneNumber })
        await this.setState({ user: StorageHelper.getUser() })
    }

    validateInputs = (event) => {
        switch (event) {
            case "name":
                if (this.state.name.length === 0)
                    this.setState({ messageDisplay: { ...this.state.messageDisplay, name: true } })
                else
                    this.setState({ messageDisplay: { ...this.state.messageDisplay, name: false } })
                break
            case "email":
                if (!/^\w+([.-]?\w+)*@\w+([.-]?\w+)*(\.\w{2,3})+$/.test(this.state.email))
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, email: true },
                    })
                else
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, email: false },
                    })
                break
            case "address":
                if (this.state.address.length === 0)
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, address: true },
                    })
                else
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, address: false },
                    })
                break
            case "mobile":
                if (!/([8-9]{1})([0-9]{7})$/.test(this.state.mobile))
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, mobile: true },
                    })
                else
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, mobile: false },
                    })
                break
            default:
                return null
        }
    }
    handleChange = (field) => {
        this.setState(field)
    }

    editDetails = (event) => {
        event.preventDefault()
        let messageDisplay = this.state.messageDisplay
        this.setState({ displayError: false })
        for (let field in messageDisplay) {
            console.log(field, this.state[field].length)
            if (this.state[field].length === 0 || messageDisplay[field]) {
                this.setState({
                    errorMessage: "There are invalid fields",
                    displayError: true,
                })
                return
            }
        }

        let body = {
            username: StorageHelper.getUsername(),
            name: this.state.name,
            email: this.state.email,
            address: this.state.address,
            phoneNumber: parseInt(this.state.mobile),
        }
        UserService.updateUser(body)
            .then(async () => {
                this.setState({ displaySuccess: true })
                const newUser = await UserService.getUser(StorageHelper.getUsername())
                StorageHelper.setUser(JSON.stringify(newUser.data))
                setTimeout(() => {
                    this.props.history.push("/profile")
                }, 1000)
            })
            .catch((error) => {
                console.log(error)
                const { message } = error.response.data
                if (message.includes("Email is already"))
                    this.setState({
                        errorMessage: "Email is already taken",
                        displayError: true,
                    })
            })
    }

    render() {
        return (
            <Box>
                <Box
                    component="form"
                    display="flex"
                    sx={{
                        border: "1px solid",
                        flexDirection: "column",
                        padding: "2vw ",
                        margin: "0vw 15vw",
                        borderRadius: 2,
                    }}
                    alignItems="center"
                    autoComplete="off">
                    <Grid
                        container
                        direction="column"
                        alignItems="center"
                        justifyContent="center"
                        style={{ width: "100%" }}
                        rowSpacing={1}>
                        <Grid item>
                            {this.state.displayError ? (
                                <Alert severity="error">{this.state.errorMessage}</Alert>
                            ) : (
                                <Box />
                            )}
                            {this.state.displaySuccess ? (
                                <Alert severity="success">Updated Successfully </Alert>
                            ) : (
                                <Box />
                            )}
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel htmlFor="component-outlined">Name</InputLabel>
                                <OutlinedInput
                                    id="component-outlined"
                                    value={this.state.name}
                                    onChange={(event) =>
                                        this.handleChange({ name: event.target.value })
                                    }
                                    onBlur={(event) => this.validateInputs("name")}
                                    label="Name"
                                />
                                <FormHelperText sx={{ color: "red" }} id="name-error-text">
                                    {this.state.messageDisplay.name ? "Name cannot be blank" : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel htmlFor="component-outlined">Email</InputLabel>
                                <OutlinedInput
                                    id="component-outlined"
                                    value={this.state.email}
                                    onChange={(event) =>
                                        this.handleChange({ email: event.target.value })
                                    }
                                    onBlur={(event) => this.validateInputs("email")}
                                    label="Email"
                                />
                                <FormHelperText sx={{ color: "red" }} id="email-error-text">
                                    {this.state.messageDisplay.email
                                        ? "Invalid email address"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel htmlFor="component-outlined">Address</InputLabel>
                                <OutlinedInput
                                    id="component-outlined"
                                    value={this.state.address}
                                    onChange={(event) =>
                                        this.handleChange({ address: event.target.value })
                                    }
                                    onBlur={(event) => this.validateInputs("address")}
                                    label="Address"
                                />
                                <FormHelperText sx={{ color: "red" }} id="address-error-text">
                                    {this.state.messageDisplay.address
                                        ? "Address cannot be blank"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel htmlFor="component-outlined">Mobile Number</InputLabel>
                                <OutlinedInput
                                    id="component-outlined"
                                    value={this.state.mobile}
                                    onChange={(event) =>
                                        this.handleChange({ mobile: event.target.value })
                                    }
                                    onBlur={(event) => this.validateInputs("mobile")}
                                    label="Mobile Number"
                                />
                                <FormHelperText sx={{ color: "red" }} id="name-error-text">
                                    {this.state.messageDisplay.mobile
                                        ? "Mobile number must be 8 characters starting with 8 or 9"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <Button
                                color="success"
                                type="submit"
                                sx={{ marginRight: "2vh" }}
                                variant="contained"
                                onClick={this.editDetails}>
                                Save
                            </Button>
                            <Button
                                sx={{ float: "right" }}
                                variant="contained"
                                onClick={()=>{this.props.history.push("/changepass")}}>
                                Change Password
                            </Button>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        )
    }
}

export default EditProfile
