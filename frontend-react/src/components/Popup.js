import React, { Fragment, Component } from "react";
import styles from "../styles/popup.module.css";
import "bootstrap/dist/css/bootstrap.min.css";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogContentText from "@mui/material/DialogContentText";
import DialogTitle from "@mui/material/DialogTitle";
import FormControl from "@mui/material/FormControl";
import MenuItem from "@mui/material/MenuItem";
import Select from "@mui/material/Select";
import Box from "@mui/material/Box";

import ProductService from "../services/ProductService";
import { SliderMark } from "@mui/material";

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
      dialogpopup: true,
      fullwidth: true,
      maxWidth: "sm",
      listusers: [],
    };
  }

  async componentDidMount() {
    const interesteduser = await ProductService.getProductInterestByProduct(
      this.props.productId
    );
    this.setState({ listusers: interesteduser.data });
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
        errorMessage: "Username need to be more than 4 characters",
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

  handleClose = () => {
    this.setState({ dialogpopup: false });
    this.props.closePopUp();
  };
  render() {
    return (
      <div>
        <div>
          {this.state.buttons === 2 && (
            <div>
              <span className="close" onClick={this.handleClick}>
                &times;
              </span>
              <h3>{this.state.title}</h3>
              <button onClick={this.handleDelete}>YES</button>
              <button onClick={this.handleClick}>NO</button>
              <br />
            </div>
          )}
          {/*this chunk is for giveaway */}
          {this.state.buttons === 1 && (
            <div>
              <Dialog
                open={this.state.dialogpopup}
                onClose={this.handleClose}
                fullWidth={this.fullwidth}
                maxWidth={this.maxWidth}
              >
                <DialogTitle>{this.state.title}</DialogTitle>
                <DialogContent>
                  <DialogContentText>{this.state.label}</DialogContentText>
                </DialogContent>
                <Box style={{ marginLeft: "10px" }} sx={{ minWidth: 300 }}>
                  <FormControl
                    sx={{ minWidth: 200 }}
                    fullWidth={this.fullWidth}
                  >
                    <Select
                      id="select-username"
                      value={this.username}
                      onChange={this.handleUserInput}
                    >
                      {this.state.listusers.map((user) =>
                      <MenuItem value={user}> {user} </MenuItem>
                      )}
                    </Select>
                  </FormControl>
                </Box>
                <DialogContent>
                  <DialogContentText>
                    {this.state.error && (
                      <p style={{ color: "red" }}>{this.state.errorMessage}</p>
                    )}
                  </DialogContentText>
                </DialogContent>
                <DialogActions>
                  <Button onClick={this.handleSubmit}>Submit</Button>
                </DialogActions>
              </Dialog>

              {/*<form onSubmit={this.handleSubmit}>
                <h3>{this.state.title}</h3>
                <label style={{ marginBottom: "5px" }}>
                  {this.state.label}
                  <input
                    style={{ marginLeft: "10px" }}
                    type="text"
                    value={this.state.username}
                    onChange={this.handleUserInput}
                  />
                </label>
                <br />
                <input type="submit" />
              </form>*/}
            </div>
          )}{" "}
          {/* ga chunk ends here */}
        </div>
      </div>
    );
  }
}
