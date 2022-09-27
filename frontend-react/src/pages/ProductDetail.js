import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import "react-multi-carousel/lib/styles.css";
import Card from 'react-bootstrap/Card';
import ProductService from '../services/ProductService'
import bulbasaur from '../bulbasaur.jpg'

class ProductDetail extends Component {
    constructor(props){
        super(props)
        this.state = {
            id: this.props.match.params.id,
            data: []
        }
    }
    
    async componentDidMount(){
        const res = await ProductService.getProduct(this.state.id);
        this.setState({data: res.data})
    }

    render() {
        return (
            <div className="container" style={{width: '55%'}}>
                 <Card>
                    <Card.Img variant="top" src={bulbasaur}/>
                    <Card.Body>
                        <Card.Title>{this.state.data.name}</Card.Title>
                        <Card.Text>
                            {this.state.data.conditions}
                            <br></br>
                            {this.state.data.address}
                            <br></br>
                            {this.state.data.description}
                        </Card.Text>
                    </Card.Body>
                </Card>
            </div>
        );
    }
}


export default ProductDetail
