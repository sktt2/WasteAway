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
    InputAdornment
} from "@mui/material"

import VisibilityIcon from "@mui/icons-material/Visibility"
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff"
import IconButton from "@mui/material/IconButton"

import StorageHelper from "../services/StorageHelper"
import AuthService from "../services/AuthService"

class ChangePass extends Component {
    constructor(props) {
        super(props)
        this.state = {
            user: {},
            validated: "",
            url: "/changepass",
            displaySuccess: false,
            displayError: false,
            errorMessage: "",
            messageDisplay: {
                currentPassword: false,
                newPassword: false,
                newPasswordConfirm: false,
            },
            showCurrentPassword: false,
            showNewPassword: false,
            showConfirmNewPassword: false,
            currentPassword: "",
            newPassword: "",
            newPasswordConfirm: "",
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.changePassword = this.changePassword.bind(this)
        this.toggleShowPassword = this.toggleShowPassword.bind(this)
        this.handleChange = this.handleChange.bind(this)
    }

    async componentDidMount() {
        if (StorageHelper.getUser() == null) {
            this.props.history.push("/")
        }
        await this.setState({ user: StorageHelper.getUser() })
    }

    toggleShowPassword = (index) => {
        switch (index) {
            case "currentPassword":
                this.setState({ showCurrentPassword: !this.state.showCurrentPassword })
                break
            case "newPassword":
                this.setState({ showNewPassword: !this.state.showNewPassword })
                break
            case "newPasswordConfirm":
                this.setState({ showConfirmNewPassword: !this.state.showConfirmNewPassword })
                break
            default:
                return null
        }
    }

    validateInputs = (event) => {
        switch (event) {
            case "currentPassword":
                if (this.state.currentPassword.length < 8)
                    this.setState({ messageDisplay: { ...this.state.messageDisplay, currentPassword: true } })
                else
                    this.setState({ messageDisplay: { ...this.state.messageDisplay, currentPassword: false } })
                break

            case "newPassword":
                if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(this.state.newPassword))
                    this.setState({ messageDisplay: { ...this.state.messageDisplay, newPassword: true } })
                else
                    this.setState({ messageDisplay: { ...this.state.messageDisplay, newPassword: false } })
                break

            case "newPasswordConfirm":
                if (this.state.newPassword !== this.state.newPasswordConfirm)
                    this.setState({ messageDisplay: { ...this.state.messageDisplay, newPasswordConfirm: true } })
                else
                    this.setState({ messageDisplay: { ...this.state.messageDisplay, newPasswordConfirm: false } })
                break

            default:
                return null
        }
    }
    
    handleChange = (field) => {
        this.setState(field)
    }

    changePassword = (event) => {
        event.preventDefault()
        let messageDisplay = this.state.messageDisplay
        this.setState({ displayError: false })
        for (let field in messageDisplay) {
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
            currentPassword: this.state.currentPassword,
            newPassword: this.state.newPassword
        }

        AuthService.changePassword(body)
            .then(async() => {
                this.setState({ displaySuccess: true })
                setTimeout(() => {
                    this.props.history.push("/profile")
                }, 1000)
            })
            .catch((error)=>{
                let { message } = error.response.data
                if(message == "Bad credentials") message = "Incorrect Password"
                this.setState({
                    errorMessage: message,
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
                                <Alert severity="success">Password changed successfully</Alert>
                            ) : (
                                <Box />
                            )}
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel htmlFor="component-outlined">Current Password</InputLabel>
                                <OutlinedInput
                                    id="component-outlined"
                                    type={this.state.showCurrentPassword ? "text" : "password"}
                                    value={this.state.currentPassword}
                                    onChange={(event) =>
                                        this.handleChange({ currentPassword: event.target.value })
                                    }
                                    onBlur={(event) => this.validateInputs("currentPassword")}
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="toggle password visibility"
                                                onClick={ () => this.toggleShowPassword("currentPassword")}
                                                edge="end">
                                                {this.state.showCurrentPassword ? (
                                                    <VisibilityIcon />
                                                ) : (
                                                    <VisibilityOffIcon />
                                                )}
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    label="Current Password"
                                />
                                <FormHelperText sx={{ color: "red" }} id="currentPassword-error-text">
                                    {this.state.messageDisplay.currentPassword ? "Current password should be at least 8 characters long" : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                    
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel htmlFor="component-outlined">New Password</InputLabel>
                                <OutlinedInput
                                    id="component-outlined"
                                    type={this.state.showNewPassword ? "text" : "password"}
                                    value={this.state.newPassword}
                                    onChange={(event) =>
                                        this.handleChange({ newPassword: event.target.value })
                                    }
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="toggle password visibility"
                                                onClick={ () => this.toggleShowPassword("newPassword")}
                                                edge="end">
                                                {this.state.showNewPassword ? (
                                                    <VisibilityIcon />
                                                ) : (
                                                    <VisibilityOffIcon />
                                                )}
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    onBlur={(event) => this.validateInputs("newPassword")}
                                    label="New Password"
                                />
                                <FormHelperText sx={{ color: "red" }} id="newPassword-error-text">
                                    {this.state.messageDisplay.newPassword
                                        ? "New password must have at least 8 characters, at least one number and one letter"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel htmlFor="component-outlined">Confirm New Password</InputLabel>
                                <OutlinedInput
                                    id="component-outlined"
                                    type={this.state.showConfirmNewPassword ? "text" : "password"}
                                    value={this.state.newPasswordConfirm}
                                    onChange={(event) =>
                                        this.handleChange({ newPasswordConfirm: event.target.value })
                                    }
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="toggle password visibility"
                                                onClick={ () => this.toggleShowPassword("newPasswordConfirm")}
                                                edge="end">
                                                {this.state.showConfirmNewPassword ? (
                                                    <VisibilityIcon />
                                                ) : (
                                                    <VisibilityOffIcon />
                                                )}
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    onBlur={(event) => this.validateInputs("newPasswordConfirm")}
                                    label="Confirm New Password"
                                />
                                <FormHelperText sx={{ color: "red" }} id="newPasswordConfirm-error-text">
                                    {this.state.messageDisplay.newPasswordConfirm
                                        ? "New password does not match"
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
                                onClick={this.changePassword}>
                                Save
                            </Button>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        )
    }
}

export default ChangePass
