import React, { Fragment, Component } from "react"
import styles from "../styles/popup.module.css"
import "bootstrap/dist/css/bootstrap.min.css"
import ProductService from "../services/ProductService"
import CloseIcon from "@mui/icons-material/Close"
import { Button, Grid, IconButton } from "@mui/material"
import { Box } from "@mui/system"

export default class PopUp extends Component {
    constructor(props) {
        super(props)
        this.state = {
            username: "",
            error: false,
            errorMessage: "",
            title: props.title,
            label: props.label,
            buttons: props.buttons,
        }
    }

    handleClick = () => {
        this.props.closePopUp()
    }

    handleSubmit = async (e) => {
        e.preventDefault()
        const { username } = this.state
        if (username.length < 5)
            this.setState({
                error: true,
                errorMessage: "Username need to be more than 4 characters",
            })
        else {
            ProductService.giveProduct({
                receiverUsername: username,
                productId: this.props.productId,
            })
                .then((response) => {
                    if (response.status === 200) {
                        this.props.closePopUp(true, true)
                    }
                })
                .catch((e) => {
                    this.setState({
                        error: true,
                        errorMessage: e.response.data.message,
                    })
                })
        }
    }

    handleDelete = async (e) => {
        e.preventDefault()
        ProductService.removeProduct(this.props.productId)
            .then((response) => {
                if (response.status === 200) {
                    this.props.closePopUp(true)
                }
            })
            .catch((e) => {
                this.setState({
                    error: true,
                    errorMessage: e.response.data.message,
                })
            })
    }

    handleUserInput = (e) => {
        const value = e.target.value
        this.setState({ username: value })
    }

    render() {
        return (
            <div className={styles.popup_container}>
                <div className={styles.popup_content}>
                    {this.state.buttons === 2 && (
                        <Box>
                            <IconButton
                                aria-label="toggle password visibility"
                                onClick={this.handleClick}
                                edge="end">
                                <CloseIcon />
                            </IconButton>
                            <Box
                                display="flex"
                                sx={{ justifyContent: "center", alignItems: "center" }}>
                                <h4>{this.state.title}</h4>
                            </Box>
                            <Grid
                                container
                                spacing={2}
                                display="flex"
                                sx={{ justifyContent: "center", alignItems: "center" }}>
                                <Grid item>
                                    <Button
                                        variant="contained"
                                        color="success"
                                        onClick={this.handleDelete}>
                                        Yes
                                    </Button>
                                </Grid>
                                <Grid item>
                                    <Button
                                        variant="contained"
                                        color="error"
                                        onClick={this.handleClick}>
                                        No
                                    </Button>
                                </Grid>
                            </Grid>
                            <br />
                        </Box>
                    )}
                    {this.state.buttons === 1 && (
                        <div>
                            <span className="close" onClick={this.handleClick}>
                                &times;
                            </span>
                            <form onSubmit={this.handleSubmit}>
                                <h3>{this.state.title}</h3>
                                <label style={{ marginBottom: "5px" }}>
                                    {this.state.label}
                                    <input
                                        style={{ marginLeft: "10px" }}
                                        type="text"
                                        value={this.state.username}
                                        onChange={this.handleUserInput}
                                    />
                                </label>
                                <br />
                                <input type="submit" />
                            </form>
                            {this.state.error && (
                                <p style={{ color: "red" }}>{this.state.errorMessage}</p>
                            )}
                        </div>
                    )}
                </div>
            </div>
        )
    }
}
