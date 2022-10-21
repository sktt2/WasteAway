import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "react-multi-carousel/lib/styles.css";
import Card from "react-bootstrap/Card";
import ProductService from "../services/ProductService";
import bulbasaur from "../bulbasaur.jpg";
import StorageHelper from "../services/StorageHelper";
import Button from "@mui/material/Button";
import FavoriteIcon from "@mui/icons-material/Favorite";
// Import CSS styling
import styles from "../styles/ComponentStyle.module.css";

class ProductDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.match.params.id,
      data: [],
      favourite: false,
    };
    this.favouriteButtonClicked = this.favouriteButtonClicked.bind(this);
  }

  async componentDidMount() {
    const res = await ProductService.getProduct(this.state.id);
    this.setState({ data: res.data });
    var userid = StorageHelper.getUserId();

    if (res.getFavList != null && res.getFavList.contains(userid)) {
      this.setState({ favourite: true });
    }
  }
  favouriteButtonClicked = () => {
    let userid = StorageHelper.getUserId()
    let product = ProductService.getProduct(this.state.id);
    if (this.state.favourite === true) {
        this.setState({favourite: false})
      
    } else {
        this.setState({favourite: true})

    }
  };

  render() {
    let { isFavorited } = this.state.favourite;
    let favbutton;
    if (isFavorited) {
      favbutton = (
        <Button variant="contained" onClick={this.favouriteButtonClicked}>
          <FavoriteIcon style={{ color: "red" }} />
        </Button>
      );
    } else {
      favbutton = (
        <Button variant="outlined" onClick={this.favouriteButtonClicked}>
          <FavoriteIcon />
        </Button>
      );
    }
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
            {favbutton}
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default ProductDetail;
