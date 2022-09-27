import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import "react-multi-carousel/lib/styles.css";

// From components
import CardComponent from '../components/Card';
import ProductService from '../services/ProductService'

class ProductDetail extends Component {
    constructor(props){
        super(props)
        console.log(this.props.match)
        this.state = {
            id: this.props.match.params.id,
            data: []
        }
    }
    
    async componentDidMount(){
        const res = await ProductService.getProduct(this.state.id);
        console.log(res.data)
        this.setState({data: res.data})
    }

    render() {
        return (
            <div className="container">
                <CardComponent title={this.state.data.name} description={this.state.data.description} 
                    address={this.state.data.address} condition={this.state.data.conditions}
                    imgSource="address" buttonLink={this.state.url+this.state.data.id}></CardComponent>
            </div>
        );
    }
}


export default ProductDetail
