import React, { Component } from "react";
import styles from "../styles/popup.module.css";
import ProductService from "../services/ProductService";
import CloseIcon from "@mui/icons-material/Close";
import { Button, Grid, IconButton } from "@mui/material";
import { Box } from "@mui/system";
import { FormControl, MenuItem, Select } from "@mui/material";

export default class PopUp extends Component {
	constructor(props) {
		super(props);
		this.state = {
			username: "",
			error: false,
			errorMessage: "",
			title: props.title,
			label: props.label,
			buttons: props.buttons,
			listInterestProducts: [],
		};
	}

	async componentDidMount() {
		if (this.state.buttons === 1) {
			const interesteduser = await ProductService.getProductInterestByProduct(
				this.props.productId
			);
      console.log(interesteduser.data);
			this.setState({ listInterestProducts: interesteduser.data });
		}
	}

	handleClick = () => {
		this.props.closePopUp();
	};

	handleSubmit = async (e) => {
		e.preventDefault();
		const { username } = this.state;
		if (username.length < 5)
			this.setState({
				error: true,
				errorMessage: "Please select a username",
			});
		else {
			ProductService.giveProduct({
				receiverUsername: username,
				productId: this.props.productId,
			})
				.then((response) => {
					if (response.status === 200) {
						this.props.closePopUp(true, true);
					}
				})
				.catch((e) => {
					this.setState({
						error: true,
						errorMessage: e.response.data.message,
					});
				});
		}
	};

	handleDelete = async (e) => {
		e.preventDefault();
		ProductService.removeProduct(this.props.productId)
			.then((response) => {
				if (response.status === 200) {
					this.props.closePopUp(true);
				}
			})
			.catch((e) => {
				this.setState({
					error: true,
					errorMessage: e.response.data.message,
				});
			});
	};

	handleUserInput = (e) => {
		const value = e.target.value;
		this.setState({ username: value });
	};

	render() {
		return (
			<div className={styles.popup_container}>
				<div className={styles.popup_content}>
					{this.state.buttons === 2 && (
						<Box>
							<IconButton
								aria-label="toggle password visibility"
								onClick={this.handleClick}
								edge="end"
							>
								<CloseIcon />
							</IconButton>
							<Box
								display="flex"
								sx={{ justifyContent: "center", alignItems: "center" }}
							>
								<h4>{this.state.title}</h4>
							</Box>
							<Grid
								container
								spacing={2}
								display="flex"
								sx={{ justifyContent: "center", alignItems: "center" }}
							>
								<Grid item>
									<Button
										variant="contained"
										color="success"
										onClick={this.handleDelete}
									>
										Yes
									</Button>
								</Grid>
								<Grid item>
									<Button
										variant="contained"
										color="error"
										onClick={this.handleClick}
									>
										No
									</Button>
								</Grid>
							</Grid>
							<br />
						</Box>
					)}
					{this.state.buttons === 1 && (
						<Box>
							<IconButton
								aria-label="toggle password visibility"
								onClick={this.handleClick}
								edge="end"
							><CloseIcon/>
							</IconButton>
              <Box display="flex" sx={{ justifyContent: "center", alignItems: "center" }}>
                <h4>{this.state.title}</h4>
              </Box>
              <Box display="flex" sx={{ marginTop: 2, alignItems: "center", justifyContent: "center"}} >
                <Box display="flex" sx={{ alignItems: "center", justifyContent: "center"}}>
                  <h6 >{this.state.label}</h6>
									<FormControl  sx={{ minWidth: 200 , marginLeft: '2%'}}>
										<Select
											id="select-username"
											value={this.state.username}
											onChange={this.handleUserInput}
										>
											{this.state.listInterestProducts.map((interestProduct) => (
												<MenuItem value={interestProduct.interestedUsername}>{interestProduct.interestedUsername}</MenuItem>
											))}
										</Select>
									</FormControl>
                  </Box>
								</Box>
                <Box display="flex" sx={{ justifyContent: "center", alignItems: "center", marginTop: "3vh" }}>
                    <Button variant="contained" onClick={this.handleSubmit}>Submit</Button>
                  </Box>
                {this.state.error && (
                  <Box display="flex" sx={{color: "red" , alignItems: "center", marginTop: 1}}>{this.state.errorMessage}</Box>
                )}
						</Box>
					)}
				</div>
			</div>
		);
	}
}