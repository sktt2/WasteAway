import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "react-multi-carousel/lib/styles.css";
import Card from "react-bootstrap/Card";
import ProductService from "../services/ProductService";
import bulbasaur from "../bulbasaur.jpg";

// Import CSS styling
import styles from "../features/ComponentStyle.module.css";

class ProductDetail extends Component {
    constructor(props) {
        super(props);
        this.state = {
            id: this.props.match.params.id,
            data: [],
        };
    }

    async componentDidMount() {
        const res = await ProductService.getProduct(this.state.id);
        this.setState({ data: res.data });
    }

    render() {
        return (
            <div className="container" style={{ width: "55%" }}>
                <Card className={styles.productDetails}>
                    <Card.Img variant="top" src={this.state.data.imageUrl || bulbasaur} />
                    <Card.Body>
                        <Card.Title>{this.state.data.productName}</Card.Title>
                        <Card.Text>
                            {this.state.data.condition}
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

export default ProductDetail;
