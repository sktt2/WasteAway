import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"
import { ref, uploadBytes, getDownloadURL, deleteObject } from "firebase/storage";

import Form from "react-bootstrap/Form"
import ProductService from "../services/ProductService"
import StorageHelper from "../services/StorageHelper"
import storage from "../services/FirebaseConfig";

// Import CSS styling
import styles from "../features/ComponentStyle.module.css";

class EditProduct extends Component {
    constructor(props) {
        super(props)
        this.state = {
            id: this.props.match.params.id,
            data: [],
            validated: "",
            url: "/product/edit/",
        }
        this.validateInputs = this.validateInputs.bind(this)
        this.editDetails = this.editDetails.bind(this)
    }

    async componentDidMount() {
        const res = await ProductService.getProduct(this.state.id)
        this.setState({ data: res.data })
        if (res.data.ownerName !== StorageHelper.getUserName()) {
            this.props.history.push("/profile")
        }
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
        // String productName, String condition, String dateTime, String category, String description
        if (this.state.image != null){
            const file = this.state.image;
            const imageName = JSON.parse(localStorage.getItem("user")).id + "/" + this.state.urlImage.split('/')[3];
            const storageRef = ref(storage, imageName);

            //upload new file
            uploadBytes(storageRef, file)
            .then((snapshot) => {
                console.log('Uploaded a blob or file!');

                //get new image URL and store all data in SQL DB
                getDownloadURL(storageRef)
                .then((imageURL) => {

                    let body = {
                        id: this.state.id,
                        productName: this.state.productname || this.state.data.productName,
                        condition: this.state.conditions || this.state.data.condition,
                        dateTime: new Date().toISOString(),
                        category: this.state.category || this.state.data.category,
                        description: this.state.description || this.state.data.description,
                        imageUrl: imageURL,
                    }
                    
                    ProductService.updateProductDetail(body)
                    .then(() => {
                        document.getElementById("errorMessage").style.display = "none";
                        document.getElementById("successMessage").style.display = "block";
                        setTimeout(function(){
                            window.location.reload('false')
                        }, 2000);
                    })
                    .catch(() => {
                        this.props.history.push('/error')
                    });
                    
                    //find old file to delete
                    const deleteRef = ref(storage, this.state.data.imageUrl);
                    deleteObject(deleteRef)
                    .then(() => {
                        console.log("Old image deleted from firebase");
                    })
                    .catch((error) => { //error for deleting old object
                        console.log("Failed to delete old image");
                        console.log(error);
                    });
                })
                .catch((error) => { //error for failing to get URL for new image
                    document.getElementById("errorMessage").style.display = "block";
                });
            })
            .catch(() => { // error for upload
                document.getElementById("errorMessage").style.display = "block";
            });
        }else {
            let body = {
                id: this.state.id,
                productName: this.state.productname || this.state.data.productName,
                condition: this.state.conditions || this.state.data.condition,
                dateTime: new Date().toISOString(),
                category: this.state.category || this.state.data.category,
                description: this.state.description || this.state.data.description,
                imageUrl: this.state.data.imageUrl,
            }
            
            ProductService.updateProductDetail(body)
            .then(() => {
                document.getElementById("successMessage").style.display = "block";
                setTimeout(function(){
                    window.location.reload('false')
                }, 2000);
            })
            .catch(() => {
                this.props.history.push('/error')
            });
        }
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
                                    <Form.Label>Product Name</Form.Label>
                                    <Form.Control
                                        placeholder="Product name"
                                        name="productname"
                                        value={
                                            this.state.productname || this.state.data.productName
                                        }
                                        onChange={(event) =>
                                            this.setState({ productname: event.target.value })
                                        }
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Please provide a product name.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Category</Form.Label>
                                    <Form.Select
                                        value={this.state.category || this.state.data.category}
                                        onChange={(event) =>
                                            this.setState({ category: event.target.value })
                                        }
                                        required>
                                        <option value="" hidden>
                                            Select category
                                        </option>
                                        <option value="BOOKS">Books</option>
                                        <option value="ELECTRONICS">Electronics</option>
                                        <option value="FASHION">Fashion</option>
                                        <option value="FOOD">Food</option>
                                        <option value="TOYS">Toys</option>
                                        <option value="UTILITY">Utility</option>
                                        <option value="VIDEOGAMES">Video Games</option>
                                        <option value="OTHERS">Others</option>
                                    </Form.Select>
                                    <Form.Control.Feedback type="invalid">
                                        Select a category.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Description</Form.Label>
                                    <Form.Control
                                        placeholder="Description"
                                        name="description"
                                        as="textarea"
                                        rows={3}
                                        value={
                                            this.state.description || this.state.data.description
                                        }
                                        onChange={(event) =>
                                            this.setState({ description: event.target.value })
                                        }
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Please provide the product description.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group>
                                    <Form.Label>Condition of Product</Form.Label>
                                    <Form.Control
                                        placeholder="Conditions"
                                        name="conditions"
                                        as="textarea"
                                        rows={3}
                                        value={this.state.condition || this.state.data.condition}
                                        onChange={(event) =>
                                            this.setState({ conditions: event.target.value })
                                        }
                                        required
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Please provide the product condition.
                                    </Form.Control.Feedback>
                                </Form.Group>
                            </div>
                            <div>
                                <Form.Group controlId="formFile" className="mb-3">
                                    <Form.Label>Product Image</Form.Label>
                                    <Form.Control
                                        type="file"
                                        accept="image/*"
                                        onChange={(event) =>
                                            this.setState({
                                                image: event.target.files[0],
                                                urlImage: URL.createObjectURL(event.target.files[0]),
                                            })
                                        }
                                    />
                                    <Form.Control.Feedback type="invalid">
                                        Please provide the product image.
                                    </Form.Control.Feedback>
                                </Form.Group>
                                <div>
                                    <img
                                        className="container-fluid w-100"
                                        src={this.state.urlImage || this.state.data.imageUrl}
                                        value={this.state.urlImage}
                                    />
                                </div>
                            </div>
                            <br></br>
                            <div id="successMessage" style={{ display: "none", color: "green" }}>
                                SAVED SUCCESSFULLY
                            </div>
                            <div id="errorMessage" style={{ display: "none", color: "red" }}>
                                ERROR! PLEASE TRY AGAIN!
                            </div>
                            <button className="btn btn-success" type="submit">
                                SAVE
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        )
    }
}

export default EditProduct
