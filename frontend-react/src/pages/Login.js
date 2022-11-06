import React, { Component } from "react"

import AuthService from "../services/AuthService"
import VisibilityIcon from "@mui/icons-material/Visibility"
import VisibilityOffIcon from "@mui/icons-material/VisibilityOff"
import IconButton from "@mui/material/IconButton"
// Import CSS styling
import StorageHelper from "../services/StorageHelper"
import { Box } from "@mui/system"

import {
    FormControl,
    Grid,
    InputLabel,
    OutlinedInput,
    Button,
    Alert,
    InputAdornment,
} from "@mui/material"
class Login extends Component {
    constructor(props) {
        super(props)
        this.state = {
            showPassword: false,
            errorMessage: "",
            displayError: false,
            username: "",
            password: "",
        }
        this.loginClicked = this.loginClicked.bind(this)
        this.toggleShowPassword = this.toggleShowPassword.bind(this)
        this.handleChange = this.handleChange.bind(this)
    }

    componentDidMount() {
        if (localStorage.hasOwnProperty("user")) {
            this.props.history.push("/")
        }
    }

    handleChange = (field) => {
        this.setState(field)
    }
    loginClicked = (event) => {
        event.preventDefault()
        this.setState({ displayError: false })
        const { username, password } = this.state
        if (username.length === 0 || password.length === 0) {
            this.setState({
                errorMessage: "Username/Password is blank",
                displayError: true,
            })
            return
        }
        AuthService.signin(username, password)
            .then((response) => {
                StorageHelper.setUser(JSON.stringify(response.data))
                this.props.history.push("/recommendation")
            })
            .catch((err) => {
                this.setState({
                    errorMessage: "Username/Password is incorrect!",
                    displayError: true,
                })
            })
    }

    toggleShowPassword = () => {
        this.setState({ showPassword: !this.state.showPassword })
    }

    render() {
        return (
            <Box display="flex" sx={{ justifyContent: "center", padding: "5vw 0" }}>
                <Box
                    component="form"
                    display="flex"
                    sx={{
                        border: "1px solid",
                        flexDirection: "column",
                        padding: "1vw 0",
                        borderRadius: 2,
                        width: "50%",
                    }}>
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
                                    label="Username"
                                />
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%", margin: "1vh" }}>
                            <FormControl sx={{ width: "100%" }} variant="outlined">
                                <InputLabel htmlFor="outlined-adornment-password">
                                    Password
                                </InputLabel>
                                <OutlinedInput
                                    id="outlined-adornment-password"
                                    type={this.state.showPassword ? "text" : "password"}
                                    value={this.state.password}
                                    onChange={(event) =>
                                        this.handleChange({ password: event.target.value })
                                    }
                                    endAdornment={
                                        <InputAdornment position="end">
                                            <IconButton
                                                aria-label="toggle password visibility"
                                                onClick={this.toggleShowPassword}
                                                edge="end">
                                                {this.state.showPassword ? (
                                                    <VisibilityIcon />
                                                ) : (
                                                    <VisibilityOffIcon />
                                                )}
                                            </IconButton>
                                        </InputAdornment>
                                    }
                                    label="Password"
                                />
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <Button
                                color="success"
                                type="submit"
                                sx={{ marginRight: "2vh" }}
                                variant="contained"
                                onClick={this.loginClicked}>
                                Login
                            </Button>
                            <Button
                                variant="contained"
                                color="success"
                                onClick={() => this.props.history.push("/register")}>
                                Register
                            </Button>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        )
    }
}

export default Login
