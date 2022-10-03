import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';

import Carousel from "react-multi-carousel";
import "react-multi-carousel/lib/styles.css";

// From components
import CardComponent from '../components/Card';
import CarouselComponent from '../components/Carousel';
import ProductService from '../services/ProductService'

const responsive = {
        desktop: {
            breakpoint: { max: 3000, min: 1024 },
            items: 3,
            slidesToSlide: 2 // optional, default to 1.
        }
    };

class Product extends Component {
    constructor(props){
        super(props)
        this.state = {
            url: "http://localhost:3000/product/",
            data: []
        }
        
    }
    

    async componentDidMount(){
        const res = await ProductService.getProducts();
        this.setState({data: res.data})
    }
    
        
    render() {
        return (
            <div className="container">
                <Carousel responsive={responsive}>
                    {this.state.data.map((data, i)=>(
                        <CarouselComponent title={data.productName} condition={data.condition} address={data.address} imgSource={data.imageUrl} buttonLink={"hello"} ></CarouselComponent>
                    ))}
                </Carousel>
                <br></br>
                <React.Fragment>
                    <div className="row">
                        <Row xs={1} md={4} className="g-4">
                            {this.state.data.map((data, i) => (
                                <Col>
                                <CardComponent 
                                    title={data.productName} description={data.description} 
                                    address={data.address} condition={data.condition}
                                    imgSource={data.imageUrl} buttonLink={this.state.url+data.id}>
                                </CardComponent>
                                </Col>
                            ))}
                        </Row>
                    </div>
                </React.Fragment>
            </div>
        );
    }
}


export default Product
