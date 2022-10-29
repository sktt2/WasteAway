import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "react-multi-carousel/lib/styles.css";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button"
import ProductService from "../services/ProductService";
import ChatService from "../services/ChatService"
import bulbasaur from "../bulbasaur.jpg";
import StorageHelper from "../services/StorageHelper";
import FavoriteIcon from "@mui/icons-material/Favorite";

// Import CSS styling
import styles from "../styles/ComponentStyle.module.css";
import StorageHelper from "../services/StorageHelper"
import { Alert } from "bootstrap"

class ProductDetail extends Component {
  constructor(props) {
    super(props);
    this.state = {
      id: this.props.match.params.id,
      data: [],
      fav: 0,
    };
  }

  async componentDidMount() {
    const userid = StorageHelper.getUserId();
    const allProductInterests = await ProductService.getProductInterestByUser(userid);
    if (sessionStorage.getItem("productinterests") == null) {
        sessionStorage.setItem("productinterests", JSON.stringify(allProductInterests));
    }
    const res = await ProductService.getProduct(this.state.id);
    this.setState({ data: res.data });
  }

  favouriteButtonClicked = () => {
    let interestedusername = StorageHelper.getUserName();
    let userid = StorageHelper.getUserId();
    let productid = this.state.id;
    let prodints = sessionStorage.getItem("productinterests");
    this.setState({ fav: this.state.fav === 0 ? 1 : 0 })
    if (this.state.fav === 1) {
      let prodintid;
      for (let i = 0; i < prodints.length; i++) {
        let prodint = prodints[i];
        if (prodint.hasOwnProperty('id') && prodint.id === productid) {
          prodintid = prodint.productinterestid;
        }
      }
      let req = {
        userid,
        productid
      }
      console.log(req);
      ProductService.removeProductInterest(req);
      console.log("meow");
    } else {
      let newProductInterest = {
        interestedusername,
        productid,
      };
      ProductService.addProductInterest(newProductInterest) 
      let newid = prodints[prodints.length -1].productinterestid + 1;
      prodints.push({"id": newid, "userid": userid, "productid": productid});
    }
  };

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
              <FavoriteIcon
                onClick={this.favouriteButtonClicked}
                color={this.state.fav === 1 ? "primary" : "default"}
              />
          </Card.Body>
        </Card>
      </div>
    );
  }
}

export default ProductDetail;
