import React, { Component } from "react"
import "bootstrap/dist/css/bootstrap.min.css"

import Col from "react-bootstrap/Col"
import Row from "react-bootstrap/Row"

import Carousel from "react-multi-carousel"
import "react-multi-carousel/lib/styles.css"

import { Select, MenuItem,FormControl, InputLabel } from "@mui/material"

import "../styles/MainStyle.css"

// From components
import CardComponent from "../components/Card"
import CarouselComponent from "../components/Carousel"
import ProductService from "../services/ProductService"

const responsive = {
    desktop: {
        breakpoint: { max: 3000, min: 1024 },
        items: 3,
        slidesToSlide: 2, // optional, default to 1.
    },
}


class Product extends Component {
    constructor(props) {
        super(props)
        this.state = {
            url: "product/",
            data: [],
            mainData:[],
            searchText: "",
            filter:""
        }
    }

    async componentDidMount() {
        const res = await ProductService.getProducts()
        this.setState({ mainData: res.data })
        this.setState({ data: res.data })
    }

    render() {
        return (
            <div className="container">

                <input
                    type="search"
                    id="search"
                    placeholder="Search for..."
                    value={this.state.searchText}
                    onChange={(e) => {
                        const dataSet = []
                        this.setState({searchText: e.target.value})
                        this.state.mainData.forEach(element => {
                            if(element.productName.toLowerCase().indexOf(e.target.value.toLowerCase())  > -1) {
                                dataSet.push(element)
                            }
                        })
                        this.setState({ data: dataSet })
                    }}
                />

                <FormControl style={{width: 200,  left: 100}}>
                    <InputLabel id="demo-simple-select-label">Category</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        label="Category"
                        value = {this.state.filter}
                        onChange={(e) => {
                            const dataSet = []
                            this.setState({ filter: e.target.value})
                            
                            this.state.mainData.forEach(element => {    
                                if(element.category.toLowerCase().indexOf(e.target.value.toLowerCase())  > -1) {
                                    dataSet.push(element)
                                }
                            })
                            this.setState({ data: dataSet })
                        }}
                    >
                        <MenuItem value={"BOOKS"}>Books</MenuItem>
                        <MenuItem value={"ELECTRONICS"}>Electronics</MenuItem>
                        <MenuItem value={"FASHION"}>Fashion</MenuItem>
                        <MenuItem value={"FOOD"}>Food</MenuItem>
                        <MenuItem value={"TOYS"}>Toys</MenuItem>
                        <MenuItem value={"UTILITY"}>Utility</MenuItem>
                        <MenuItem value={"VIDEO GAMES"}>Video Games</MenuItem>
                        <MenuItem value={"OTHERS"}>Others</MenuItem>
                    </Select>
                </FormControl>

                <br></br>
                <br></br>
                <Carousel responsive={responsive}>
                    {this.state.data.map((data, i) => (
                        <CarouselComponent
                            title={data.productName}
                            condition={data.condition}
                            address={data.address}
                            imgSource={data.imageUrl}
                            buttonLink={"hello"}></CarouselComponent>
                    ))}
                </Carousel>

                <React.Fragment>
                    <div className="row">
                        <Row xs={1} md={4} className="g-4">
                            {this.state.data.map((data, i) => (
                                <Col>
                                    <CardComponent
                                        title={data.productName}
                                        description={data.description}
                                        address={data.address}
                                        condition={data.condition}
                                        imgSource={data.imageUrl}
                                        buttonLink={this.state.url + data.id}
                                        dateTime={data.dateTime}
                                        ownerName={data.ownerName}>
                                    </CardComponent>
                                </Col>
                            ))}
                        </Row>
                    </div>
                </React.Fragment>
            </div>
        )
    }
}

export default Product
