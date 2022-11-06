import React, { Component } from "react"
import { ref, uploadBytes, getDownloadURL } from "firebase/storage"

import ProductService from "../services/ProductService"
import storage from "../services/FirebaseConfig"

import StorageHelper from "../services/StorageHelper"
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
class AddProduct extends Component {
    constructor(props) {
        super(props)
        this.state = {
            validated: "",
            productName: "",
            category: "",
            condition: "",
            description: "",
            image: null,
            imageUrl: "",
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
        this.addProductClicked = this.addProductClicked.bind(this)
    }

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
    addProductClicked = (event) => {
        event.preventDefault()
        const { messageDisplay } = this.state

        for (let field in messageDisplay) {
            if (this.state[field].length === 0 || messageDisplay[field]) {
                this.setState({
                    errorMessage: "There are invalid/blank fields",
                    displayError: true,
                })
                return
            }
        }
        const file = this.state.image
        const imageName = StorageHelper.getUserId() + "/" + this.state.imageUrl.split("/")[3]
        const storageRef = ref(storage, imageName)

        //upload new file
        uploadBytes(storageRef, file)
            .then((snapshot) => {
                //get new image URL and store all data in SQL DB
                getDownloadURL(storageRef).then((imageURL) => {
                    let newProduct = {
                        productName: this.state.productName,
                        category: this.state.category,
                        description: this.state.description,
                        condition: this.state.condition,
                        dateTime: new Date().toISOString(),
                        imageUrl: imageURL,
                        userId: StorageHelper.getUserId(),
                    }

                    ProductService.addProduct(newProduct)
                        .then(() => {
                            this.props.history.push("/products")
                        })
                        .catch((error) => {
                            const { message } = error.response.data
                            this.setState({
                                errorMessage: message,
                                displayError: true,
                            })
                        })
                })
            })
            .catch((error) => {
                // const { message } = error.response.data
                this.setState({
                    errorMessage: "There is a problem uploading the image. Please try again.",
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
                        margin: "1vw 15vw",
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
                                <Alert severity="error" sx={{ margin: "0px 0px 2vh 0" }}>
                                    {this.state.errorMessage}
                                </Alert>
                            ) : (
                                <Box />
                            )}
                        </Grid>
                        <Grid item sx={{ width: "80%" }}>
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
                                <FormHelperText sx={{ color: "red" }} id="productname-error-text">
                                    {this.state.messageDisplay.productName
                                        ? "Product name cannot be empty"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item sx={{ width: "80%" }}>
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
                        <Grid item sx={{ width: "80%" }}>
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
                        <Grid item sx={{ width: "80%" }}>
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
                                    <MenuItem value="BRAND NEW">Brand New</MenuItem>
                                    <MenuItem value="LIKE MINT">Like New</MenuItem>
                                    <MenuItem value="LIGHTLY Used">Lightly Used</MenuItem>
                                    <MenuItem value="WELL USED">Well Used</MenuItem>
                                    <MenuItem value="HEAVILY USED">Heavily Used</MenuItem>
                                </Select>
                                <FormHelperText sx={{ color: "red" }} id="condition-error-text">
                                    {this.state.messageDisplay.condition
                                        ? "Condition cannot be blank"
                                        : " "}
                                </FormHelperText>
                            </FormControl>
                        </Grid>
                        <Grid item 
                            display="flex"
                            sx={{ 
                                width: "80%",  
                                alignItems: "center",
                                justifyContent: "center"
                            }}>
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
                        </Grid>
                        <Grid item 
                            display="flex"
                            sx={{ 
                                width: "80%",  
                                alignItems: "center",
                                justifyContent: "center"
                            }}>
                            <Box
                                component="img"
                                sx={{
                                    border: 0,
                                    height: 280,
                                    width: 350,
                                    maxHeight: { xs: 350 },
                                    maxWidth: { xs: 400 },
                                }}
                                alt={null}
                                src={this.state.imageUrl}
                            />
                            <FormHelperText sx={{ color: "red" }} id="condition-error-text">
                                {this.state.messageDisplay.image ? "Image cannot be empty" : " "}
                            </FormHelperText>
                        </Grid>
                        <Grid item sx={{ width: "80%" }}>
                            <Button
                                color="success"
                                type="submit"
                                sx={{ marginRight: "2vh" }}
                                variant="contained"
                                onClick={this.addProductClicked}>
                                Add
                            </Button>
                        </Grid>
                    </Grid>
                </Box>
            </Box>
        )
    }
}

export default AddProduct
