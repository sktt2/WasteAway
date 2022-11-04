import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"
import { ref, uploadBytes, getDownloadURL, deleteObject } from "firebase/storage"

import ProductService from "../services/ProductService"
import StorageHelper from "../services/StorageHelper"
import storage from "../services/FirebaseConfig"
import { Box } from "@mui/system"
import {
    FormControl,
    FormHelperText,
    Grid,
    InputLabel,
    OutlinedInput,
    Button,
    Alert,
    TextField,
    Select,
    MenuItem,
} from "@mui/material"
// Import CSS styling

class EditProduct extends Component {
    constructor(props) {
        super(props)
        this.state = {
            id: this.props.match.params.id,
            validated: "",
            url: "/product/edit/",
            productName: "",
            condition: "",
            category: "",
            description: "",
            image: null,
            imageUrl: "",
            displayError: false,
            displaySuccess: false,
            errorMessage: "",
            messageDisplay: {
                productName: false,
                category: false,
                condition: false,
                description: false,
                image: false,
            },
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.handleChange = this.handleChange.bind(this)
        this.editDetails = this.editDetails.bind(this)
    }

    async componentDidMount() {
        const res = await ProductService.getProduct(this.state.id)
        this.setState({ productName: res.data.productName })
        this.setState({ category: res.data.category })
        this.setState({ condition: res.data.condition })
        this.setState({ description: res.data.description })
        this.setState({ imageUrl: res.data.imageUrl })
        this.setState({ oldImage: res.data.image })
        if (res.data.ownerName !== StorageHelper.getUsername()) {
            this.props.history.push("/profile")
        }
    }

    // Return proper error modal on invalid input
    validateInputs = (event) => {
        switch (event) {
            case "productName":
                if (this.state.productName.length === 0)
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, productName: true },
                    })
                else
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, productName: false },
                    })
                break
            case "description":
                if (this.state.description.length < 5)
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, description: true },
                    })
                else
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, description: false },
                    })
                break
            case "category":
                if (this.state.category.length === 0)
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, category: true },
                    })
                else
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, category: false },
                    })
                break
            case "condition":
                if (this.state.condition.length === 0)
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, condition: true },
                    })
                else
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, condition: false },
                    })
                break
            case "image":
                if (this.state.image === null)
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, image: true },
                    })
                else
                    this.setState({
                        messageDisplay: { ...this.state.messageDisplay, image: false },
                    })
                break
            default:
                return null
        }
    }
    handleChange = (field) => {
        this.setState(field)
    }
    editDetails = async (event) => {
        event.preventDefault()
        let imageUrl = this.state.imageUrl
        if (this.state.image != null) {
            const file = this.state.image
            const imageName = StorageHelper.getUserId() + "/" + this.state.imageUrl.split("/")[3]
            const storageRef = ref(storage, imageName)

            //upload new file
            const newImageUrl = await uploadBytes(storageRef, file)
                .then(() => {
                    getDownloadURL(storageRef).catch((error) => {
                        this.setState({
                            displayError: true,
                            errorMessage:
                                "There is a problem uploading the image. Please try again.",
                        })
                    })
                })
                .catch((error) => {
                    this.setState({
                        displayError: true,
                        errorMessage: "There is a problem uploading the image. Please try again.",
                    })
                })
            if (newImageUrl != null) {
                imageUrl = newImageUrl
            }
            //find old file to delete
            // TO FIX
            // const deleteRef = ref(storage, this.state.oldImage)
            // deleteObject(deleteRef).catch((error) => {
            //     //error for deleting old object
            //     console.log("Failed to delete old image")
            //     console.log(error)
            // })
        }
        let body = {
            id: this.state.id,
            productName: this.state.productName,
            condition: this.state.condition,
            dateTime: new Date().toISOString(),
            category: this.state.category,
            description: this.state.description,
            imageUrl,
        }

        ProductService.updateProductDetail(body)
            .then(() => {
                this.setState({ displaySuccess: true })
                setTimeout(() => {
                    this.props.history.push("/profile")
                }, 1000)
            })
            .catch((error) => {
                const { message } = error.response.data
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
                        padding: "2vw 0",
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
                                <InputLabel htmlFor="component-outlined">Product Name</InputLabel>
                                <OutlinedInput
                                    fullWidth={true}
                                    id="component-outlined"
                                    value={this.state.productName}
                                    onChange={(event) =>
                                        this.handleChange({ productName: event.target.value })
                                    }
                                    onBlur={(event) => this.validateInputs("productName")}
                                    label="Product Name"
                                />
                                <FormHelperText sx={{ color: "red" }} id="productName-error-text">
                                    {this.state.messageDisplay.productName
                                        ? "Product name cannot be empty"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <TextField
                                    id="description"
                                    label="Description"
                                    multiline
                                    rows={2}
                                    value={this.state.description}
                                    onChange={(event) =>
                                        this.handleChange({ description: event.target.value })
                                    }
                                    onBlur={() => this.validateInputs("description")}
                                />
                                <FormHelperText sx={{ color: "red" }} id="description-error-text">
                                    {this.state.messageDisplay.description
                                        ? "Description needs to be at least 5 characters"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel id="category-label">Category</InputLabel>
                                <Select
                                    labelId="category-label"
                                    id="category-select-standard"
                                    value={this.state.category}
                                    onChange={(event) =>
                                        this.handleChange({ category: event.target.value })
                                    }
                                    onBlur={() => this.validateInputs("category")}
                                    label="Category">
                                    <MenuItem value="BOOKS">Books</MenuItem>
                                    <MenuItem value="ELECTRONICS">Electronics</MenuItem>
                                    <MenuItem value="FASHION">Fashion</MenuItem>
                                    <MenuItem value="FOOD">Food</MenuItem>
                                    <MenuItem value="TOYS">Toys</MenuItem>
                                    <MenuItem value="UTILITY">Utility</MenuItem>
                                    <MenuItem value="VIDEO GAMES">Video Games</MenuItem>
                                    <MenuItem value="OTHERS">Others</MenuItem>
                                </Select>
                                <FormHelperText sx={{ color: "red" }} id="category-error-text">
                                    {this.state.messageDisplay.category
                                        ? "Category cannot be blank"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <FormControl sx={{ width: "100%" }}>
                                <InputLabel id="condition-label">Condition</InputLabel>
                                <Select
                                    labelId="condition-label"
                                    id="condition-simple-select-standard"
                                    value={this.state.condition}
                                    onChange={(event) =>
                                        this.handleChange({ condition: event.target.value })
                                    }
                                    onBlur={() => this.validateInputs("condition")}
                                    label="Condition">
                                    <MenuItem value="MINT">Mint</MenuItem>
                                    <MenuItem value="NEAR MINT">Near Mint</MenuItem>
                                    <MenuItem value="EXCELLENT">Excellent</MenuItem>
                                    <MenuItem value="VERY GOOD">Very Good</MenuItem>
                                    <MenuItem value="GOOD">Good</MenuItem>
                                    <MenuItem value="FAIR">Fair</MenuItem>
                                    <MenuItem value="POOR">Poor</MenuItem>
                                </Select>
                                <FormHelperText sx={{ color: "red" }} id="condition-error-text">
                                    {this.state.messageDisplay.condition
                                        ? "Condition cannot be blank"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "70%" }}>
                            <Button
                                variant="contained"
                                component="label"
                                onBlur={() => this.validateInputs("image")}
                                onChange={(event) =>
                                    this.handleChange({
                                        image: event.target.files[0],
                                        imageUrl: URL.createObjectURL(event.target.files[0]),
                                    })
                                }>
                                Upload Image
                                <input hidden accept="image/*" multiple type="file" />
                            </Button>
                            <Box
                                component="img"
                                sx={{
                                    border: 0,
                                    margin: "0 0 0 12vh ",
                                    height: 280,
                                    width: 350,
                                    maxHeight: { xs: 300 },
                                    maxWidth: { xs: 350 },
                                }}
                                alt={null}
                                src={this.state.imageUrl}
                            />
                            <FormHelperText sx={{ color: "red" }} id="condition-error-text">
                                {this.state.messageDisplay.image ? "Image cannot be empty" : " "}
                            </FormHelperText>
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
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        )
    }
}

export default EditProduct
