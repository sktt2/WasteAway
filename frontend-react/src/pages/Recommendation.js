import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"
import ProductService from "../services/ProductService"
import StorageHelper from "../services/StorageHelper"

// Import CSS styling
import styles from "../styles/ComponentStyle.module.css"
import { Box } from "@mui/system"
import {
    FormControl,
    FormHelperText,
    Grid,
    InputLabel,
    Button,
    Alert,
    Select,
    MenuItem
} from "@mui/material"
class Recommendation extends Component {
    constructor(props) {
        super(props)
        this.state = {
            validated: "",
            recommend: "",
            messageDisplay: {
                recommend: false
            }
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.formSubmit = this.formSubmit.bind(this)
    }

    validateInputs = (event) => {
        switch (event) {
            case "recommend":
                if (this.state.recommend.length === 0) 
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, recommend: true},
                    })
                else
                    this.setState({
                        messageDisplay: {...this.state.messageDisplay, recommend: false},
                    })
                break
            default:
                return null
        }
    }

    handleChange = (field) => {
        this.setState(field)
    }

    formSubmit = (event) => {
        event.preventDefault()
        const { messageDisplay } = this.state

        for (let field in messageDisplay) {
            if(this.state[field].length === 0 || messageDisplay[field]) {
                this.setState({
                    errorMessage: "Recommend cannot be blank",
                    displayError: true,
                })
                return
            }
        }
        let newRecommendation = {
            username: StorageHelper.getUsername(),
            recommend: this.state.recommendation
        }
        ProductService.addRecommendation(newRecommendation)
            .then(() => {
                this.props.history.push("/products")
            })
            .catch((error) => {
                const { message } = error.response.data
                this.setState({
                    errorMessage: message,
                    displayError: true
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
                        padding: "2vw 0",
                        margin: "0vw 15vw",
                        borderRadius: 2,
                    }}
                    alignItems= "center"
                    autoComplete="off">
                    <Grid
                        container
                        direction="column"
                        alignItems="center"
                        justifyContent="center"
                        style= {{ width: "100% "}}
                        rowSpacing={1}>
                        <Grid item>
                            {this.state.displayError ? (
                                <Alert severity="error" sx= {{ margin: "0px 0px 2vh 0 "}}>
                                    {this.state.errorMessage}
                                </Alert>
                            ) : (
                                <Box />
                            )}
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel id="recommend-label">What are you looking for?</InputLabel>
                                <Select
                                    labelId="recommend-label"
                                    id="recommend-select-standard"
                                    value={this.state.recommend}
                                    onChange={(event) =>
                                        this.handleChange({ recommend: event.target.value })
                                    }
                                    onBlur={() => this.validateInputs("recommend")}
                                    label="Recommend">
                                    <MenuItem value="NONE">None</MenuItem>
                                    <MenuItem value="BOOKS">Books</MenuItem>
                                    <MenuItem value="ELECTRONICS">Electronics</MenuItem>
                                    <MenuItem value="FASHION">Fashion</MenuItem>
                                    <MenuItem value="FOOD">Food</MenuItem>
                                    <MenuItem value="TOYS">Toys</MenuItem>
                                    <MenuItem value="UTILITY">Utility</MenuItem>
                                    <MenuItem value="VIDEO GAMES">Video Games</MenuItem>
                                </Select>
                                <FormHelperText sx={{ color: "red" }} id="recommend-error-text">
                                    {this.state.messageDisplay.recommend
                                        ? "Recommendation cannot be blank"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid>
                            <Button
                                color="success"
                                type="submit"
                                sx={{ marginRight: "2vh "}}
                                variant="contained"
                                onClick={this.addRecommendation}>
                                Continue
                            </Button>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        )
    }
}

export default Recommendation