import React, { Component } from "react";
import "react-multi-carousel/lib/styles.css";
import {
	Button,
	Card,
	CardContent,
	CardMedia,
	Typography,
	Alert,
} from "@mui/material";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";
import FavoriteIcon from "@mui/icons-material/Favorite";
import { Box } from "@mui/system";
import ProductService from "../services/ProductService";
import ChatService from "../services/ChatService";
import StorageHelper from "../services/StorageHelper";
import styles from "../styles/ComponentStyle.module.css";

class ProductDetail extends Component {
	constructor(props) {
		super(props);
		this.state = {
			id: this.props.match.params.id,
			data: [],
			fav: false,
			isError: "",
			messageDisplay: false,
			errormessage: "",
			ownerItem: false,
			userExist: false,
		};
	}

	async componentDidMount() {
		// Load data of product
		const res = await ProductService.getProduct(this.state.id);
		this.setState({ data: res.data });
		if(StorageHelper.getUser()){
			this.setState({userExist: true})
			if (res.data.ownerName === StorageHelper.getUsername()) {
				this.setState({ ownerItem: true });
			}
	
			const userid = StorageHelper.getUserId();
	
			const currentProductInterest =
				await ProductService.getProductInterestByProduct(this.state.id);
	
			// Make fav icon lighted
			currentProductInterest.data.forEach((element) => {
				if (element.interestedUserId === userid) this.setState({ fav: true });
			});
		}
	}

	favouriteButtonClicked = async () => {
		// Change like/unlike
		if (StorageHelper.getUser()){
			if (this.state.fav) {
				await ProductService.removeProductInterest({
					interestedUserId: StorageHelper.getUserId(),
					productId: this.state.id,
				})
					.then(() => {
						document.getElementById("talkToUser").style.display = "none";
						this.setState({ fav: false });
					})
					.catch((error) => {
						this.setState({
							errormessage: "Action can't be done as you are the owner",
							messageDisplay: true,
						});
					});
			} else {
				await ProductService.addProductInterest({
					interestedUserId: StorageHelper.getUserId(),
					productId: this.state.id,
				})
					.then(() => {
						document.getElementById("talkToUser").style.display = "block";
						this.setState({ fav: true });
					})
					.catch((error) => {
						this.setState({
							errormessage: "Action can't be done as you are the owner",
							messageDisplay: true,
						});
					});
			}
			setTimeout(() => {
				this.setState({ messageDisplay: false });
			}, 2000);
		}
	};

	linkChat = (event) => {
		event.preventDefault();
		let newChat = {
			takerId: StorageHelper.getUserId(),
			productId: this.state.id,
		};
		ChatService.createChat(newChat)
			.then((response) => {
				this.props.history.push("/chat/" + response.data.chatId);
			})
			.catch(() => {
				this.setState({
					errormessage: "Action can't be done as you are the owner",
					messageDisplay: true,
				});
			});
	};

	render() {
		return (
			<Box className="container" style={{ width: "55%" }}>
				<Card className={styles.productDetails}>
					<CardMedia
						component="img"
						variant="top"
						image={this.state.data.imageUrl || ""}
					/>
					<CardContent>
						<Typography>{this.state.data.productName}</Typography>
						<Typography>{this.state.data.condition}</Typography>
						<Typography>{this.state.data.address}</Typography>
						<Typography>{this.state.data.description}</Typography>
						<Typography>{this.state.data.ownerName}</Typography>
						{this.state.userExist? (
						this.state.ownerItem ? (
							<></>
						) : this.state.fav ? (
							<FavoriteIcon
								color={"success"}
								onClick={this.favouriteButtonClicked}
							/>
						) : (
							<FavoriteBorderIcon
								color={"default"}
								onClick={this.favouriteButtonClicked}
							/>
						)) : <></>}
					</CardContent>
					<Button
						id="talkToUser"
						sx={{ display: this.state.fav ? "block" : "none" }}
						variant="contained"
						onClick={this.linkChat}
					>
						Talk to owner
					</Button>
					{this.state.messageDisplay ? (
						<Alert severity="error">{this.state.errormessage}</Alert>
					) : (
						<Box />
					)}
				</Card>
			</Box>
		);
	}
}

export default ProductDetail;
