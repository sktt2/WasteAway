import * as React from "react";
import { useState } from "react";
import Card from "@mui/material/Card";
import CardHeader from "@mui/material/CardHeader";
import CardMedia from "@mui/material/CardMedia";
import CardContent from "@mui/material/CardContent";
import CardActions from "@mui/material/CardActions";
import Avatar from "@mui/material/Avatar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import { red } from "@mui/material/colors";
import FavoriteIcon from "@mui/icons-material/Favorite";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import { Button } from "@mui/material";
import Menu from "@mui/material/Menu";
import MenuItem from "@mui/material/MenuItem";
import { useHistory } from "react-router-dom";
import { Box } from "@mui/system";
import FavoriteBorderIcon from "@mui/icons-material/FavoriteBorder";

// Import CSS styling
import styles from "../styles/ComponentStyle.module.css";
import ProductService from "../services/ProductService"
import StorageHelper from "../services/StorageHelper"

export default function CardComponent(props) {
	const [fav, setFav] = useState(props.favourite);
	const title = props.title;
	const id = props.id;
	//   const description = props.description;
	const buttonLink = props.buttonLink;
	const imgSource = props.imgSource;
	const address = props.address;
	const condition = props.condition;
	const triggerPopUp = props.triggerPopUp;
	const { buttons = 1 } = props;
	const editDetailLink = props.editDetailLink;
	const dateTime = props.dateTime;
	const ownerName = props.ownerName;
	const [year, month, day] = dateTime.split("-");
	const date = new Date(+year, month - 1, +day.slice(0, 2));

	const isOwner = props.isOwner;

	const [anchorEl, setAnchorEl] = React.useState(null);
	const open = Boolean(anchorEl);
	const history = useHistory();

	const handleClick = (event) => {
		setAnchorEl(event.currentTarget);
	};
	const handleClose = () => {
		setAnchorEl(null);
	};

    const favouriteButtonClicked = async () => {
        if (StorageHelper.getUser()){
            if (fav) {
                await ProductService.removeProductInterest({
                    interestedUserId: StorageHelper.getUserId(),
                    productId: id,
                })
                    .then(() => {
                        setFav(false)
                    })
                    .catch((error) => {
                        console.log("error");
                    });
            } else {
                await ProductService.addProductInterest({
                    interestedUserId: StorageHelper.getUserId(),
                    productId: id,
                })
                    .then(() => {
                        setFav(true)
                    })
                    .catch((error) => {
                        console.log("error");
                    });
            }
        }
    }

	return (
		<Card sx={{ maxWidth: 345 }} className={styles.productCard}>
			{buttons === 4 && (
				<CardHeader
					avatar={
						<Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
							{ownerName.charAt(0).toUpperCase()}
						</Avatar>
					}
					action={
						<Box>
							<IconButton
								aria-label="settings"
								aria-controls={open ? "basic-menu" : undefined}
								aria-haspopup="true"
								aria-expanded={open ? "true" : undefined}
								onClick={handleClick}
								variant="contained"
							>
								<MoreVertIcon id="basic-button" />
							</IconButton>
							<Menu
								id="basic-menu"
								anchorEl={anchorEl}
								open={open}
								onClose={handleClose}
								MenuListProps={{
									"aria-labelledby": "basic-button",
								}}
							>
								<MenuItem
									onClick={() => {
										history.push("/" + editDetailLink);
									}}
								>
									Edit Details
								</MenuItem>
								<MenuItem
									onClick={() => {
										handleClose();
										triggerPopUp(id);
									}}
								>
									Delete
								</MenuItem>
							</Menu>
						</Box>
					}
					title={ownerName}
					subheader={date.toDateString()}
				/>
			)}
			{buttons !== 4 && (
				<CardHeader
					avatar={
						<Avatar sx={{ bgcolor: red[500] }} aria-label="recipe">
							{ownerName.charAt(0).toUpperCase()}
						</Avatar>
					}
					title={ownerName}
					subheader={date.toDateString()}
				/>
			)}

			<CardMedia
				component="img"
				style={{ minHeight: 300, maxHeight: 300 }}
				image={imgSource || ""}
			/>
			<CardContent>
				<Typography variant="body2" color="text.secondary">
					{title}
				</Typography>
				<Typography>{condition}</Typography>
				<Typography>{address}</Typography>
			</CardContent>
			{buttons === 4 && (
				<Box
					style={{
						display: "flex",
						alignItems: "center",
						justifyContent: "space-between",
						padding: "5px 10px",
						marginBottom: "10px",
					}}
				>
					<Button
						style={{ align: "left" }}
						variant="contained"
						href={buttonLink}
					>
						More Details
					</Button>
					<Button
						variant="contained"
						onClick={() => {
							triggerPopUp(id, "giveaway");
						}}
					>
						Giveaway
					</Button>
				</Box>
			)}
			{buttons === 1 && (
				<CardActions disableSpacing>
					
						{isOwner ? (
							<></>
						) : fav ? (
							<FavoriteIcon 
                                id = "favouriteIcon"
                                color={"success"}
								onClick={() => {favouriteButtonClicked()}}
							/>
						) : (
							<FavoriteBorderIcon 
                                id = "favouriteIcon"
                                color={"default"}
								onClick={() => {favouriteButtonClicked()}}
							/>
						)}

					<Button
						variant="contained"
						style={{ marginLeft: "auto" }}
						href={buttonLink}
					>
						details
					</Button>
				</CardActions>
			)}
		</Card>
	);
}
