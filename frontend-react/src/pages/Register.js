import React, { Component } from "react"

import AuthService from "../services/AuthService"

// Import CSS styling
import { Box } from "@mui/system"
import {
    FormControl,
    FormHelperText,
    Grid,
    InputLabel,
    OutlinedInput,
    Button,
    Alert,
    InputAdornment,
} from "@mui/material"

import VisibilityIcon from "@mui/icons-material/Visibility"
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff"
import IconButton from "@mui/material/IconButton"
import StorageHelper from "../services/StorageHelper"

class Register extends Component {
    constructor(props) {
        super(props)
        this.state = {
            errorMessage: "",
            displayError: false,
            messageDisplay: {
                username: false,
                name: false,
                email: false,
                password: false,
                confirmpassword: false,
                address: false,
                mobile: false,
            },
            username: "",
            name: "",
            email: "",
            password: "",
            confirmpassword: "",
            address: "",
            mobile: "",
            showpassword: false,
            showconfirmpassword: false,
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.toggleShowPassword = this.toggleShowPassword.bind(this)
        this.registerClicked = this.registerClicked.bind(this)
        this.handleChange = this.handleChange.bind(this)
    }

    async componentDidMount() {
        if (StorageHelper.getUser()) {
            this.props.history.push("/")
        }
    }

    toggleShowPassword = (index) => {
        switch (index) {
            case "password":
                this.setState({ showpassword: !this.state.showpassword })
                break
            case "confirmpassword":
                this.setState({ showconfirmpassword: !this.state.showconfirmpassword })
                break
            default:
                return null
        }
    }

    validateInputs = (event) => {
        switch (event) {
            case "name":
                if (this.state.name.length < 3)
                    this.setState({ messageDisplay: { ...this.state.messageDisplay, name: true } })
                else
                    this.setState({ messageDisplay: { ...this.state.messageDisplay, name: false } })
                break
            case "username":
                if (this.state.username.length < 5)
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, username: true },
                    })
                else
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, username: false },
                    })
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
            case "password":
                if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(this.state.password))
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, password: true },
                    })
                else
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, password: false },
                    })
                break
            case "confirmpassword":
                if (this.state.password !== this.state.confirmpassword)
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, confirmpassword: true },
                    })
                else
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, confirmpassword: false },
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
    registerClicked = (event) => {
        event.preventDefault()
        let messageDisplay = this.state.messageDisplay
        for (let field in messageDisplay) {
            if (this.state[field].length === 0 || messageDisplay[field]) {
                this.setState({
                    errorMessage: "There are invalid fields",
                    displayError: true,
                })
                return
            }
        }
        let username = this.state.username
        let name = this.state.name
        let email = this.state.email
        let password = this.state.password
        let address = this.state.address
        let mobile = this.state.mobile
        AuthService.register(username, name, email, password, address, mobile)
            .then(() => {
                this.props.history.push("/login")
            })
            .catch((error) => {
                // Read body of error response and display message get to alert
                const { message } = error.response.data
                if (message.includes("Username is already taken"))
                    this.setState({
                        errorMessage: "Username is already taken",
                        displayError: true,
                    })
                if (message.includes("Email is already"))
                    this.setState({
                        errorMessage: "Email is already taken",
                        displayError: true,
                    })
            })
    }

    render() {
        return (
            <Box sx={{ padding: "2vw 0" }}>
                <Box
                    component="form"
                    display="flex"
                    sx={{
                        border: "1px solid",
                        flexDirection: "column",
                        padding: "1vw 0",
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
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel htmlFor="component-outlined">Username</InputLabel>
                                <OutlinedInput
                                    fullWidth={true}
                                    id="component-outlined"
                                    value={this.state.username}
                                    onChange={(event) =>
                                        this.handleChange({ username: event.target.value })
                                    }
                                    onBlur={(event) => this.validateInputs("username")}
                                    label="Username"
                                />
                                <FormHelperText sx={{ color: "red" }} id="username-error-text">
                                    {this.state.messageDisplay.username
                                        ? "Username needs to be at least 5 characters"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
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
                                    {this.state.messageDisplay.name
                                        ? "Name needs to be at least 3 characters"
                                        : " "}
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
                                <InputLabel htmlFor="component-outlined">Password</InputLabel>
                                <OutlinedInput
                                    id="component-outlined"
                                    type={this.state.showpassword ? "text" : "password"}
                                    value={this.state.password}
                                    onChange={(event) =>
                                        this.handleChange({ password: event.target.value })
                                    }
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="toggle password visibility"
                                                onClick={() => this.toggleShowPassword("password")}
                                                edge="end">
                                                {this.state.showpassword ? (
                                                    <VisibilityIcon />
                                                ) : (
                                                    <VisibilityOffIcon />
                                                )}
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    onBlur={(event) => this.validateInputs("password")}
                                    label="Password"
                                />
                                <FormHelperText sx={{ color: "red" }} id="password-error-text">
                                    {this.state.messageDisplay.password
                                        ? "Password must have at least 8 characters, at least one number and one letter"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel htmlFor="component-outlined">
                                    Confirm Password
                                </InputLabel>
                                <OutlinedInput
                                    id="component-outlined"
                                    type={this.state.showconfirmpassword ? "text" : "password"}
                                    value={this.state.confirmpassword}
                                    onChange={(event) =>
                                        this.handleChange({ confirmpassword: event.target.value })
                                    }
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="toggle password visibility"
                                                onClick={() =>
                                                    this.toggleShowPassword("confirmpassword")
                                                }
                                                edge="end">
                                                {this.state.showconfirmpassword ? (
                                                    <VisibilityIcon />
                                                ) : (
                                                    <VisibilityOffIcon />
                                                )}
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    onBlur={(event) => this.validateInputs("confirmpassword")}
                                    label="Confirm Password"
                                />
                                <FormHelperText
                                    sx={{ color: "red" }}
                                    id="confirmpassword-error-text">
                                    {this.state.messageDisplay.confirmpassword
                                        ? "Passwords must be the same"
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
                                onClick={this.registerClicked}>
                                Register
                            </Button>
                            <Button
                                variant="contained"
                                color="success"
                                onClick={() => this.props.history.push("/login")}>
                                Back to Login
                            </Button>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        )
    }
}

export default Register
