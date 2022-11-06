import * as React from "react";
import ListItemAvatar from "@mui/material/ListItemAvatar";
import Avatar from "@mui/material/Avatar";
import Divider from "@mui/material/Divider";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import IconButton from "@mui/material/IconButton";
import Typography from "@mui/material/Typography";
import Menu from "@mui/material/Menu";
import MenuIcon from "@mui/icons-material/Menu";
import Container from "@mui/material/Container";
import Button from "@mui/material/Button";
import Tooltip from "@mui/material/Tooltip";
import Badge from "@mui/material/Badge";
import List from "@mui/material/List";
import ListItem from "@mui/material/ListItem";
import ListItemButton from "@mui/material/ListItemButton";
import ListItemIcon from "@mui/material/ListItemIcon";
import ListItemText from "@mui/material/ListItemText";
import NotificationsIcon from "@mui/icons-material/Notifications";
import MenuItem from "@mui/material/MenuItem";
import AccountCircle from "@mui/icons-material/AccountCircle";
import StorageHelper from "../services/StorageHelper";
import { useHistory } from "react-router-dom";
import AuthService from "../services/AuthService";
import styles from "../styles/ComponentStyle.module.css";
import Popper from "@mui/material/Popper";
import Paper from "@mui/material/Paper";
import DeleteIcon from "@mui/icons-material/Delete";
import NotificationService from "../services/NotificationService";

const pages = ["products"];
const settings = ["profile", "ChatNavigator", "logout"];
const loggedOut = ["login", "register"];

const Header = (props) => {
  const [anchorElNav, setAnchorElNav] = React.useState(null);
  const [anchorElUser, setAnchorElUser] = React.useState(null);
  const [anchorElNotif, setAnchorElNotif] = React.useState(null);
  const [notifs, setNotifs] = React.useState([]);
  const [isLoggedIn, setLoggedIn] = React.useState(false);
  const history = useHistory();

  const handleOpenNotifMenu = (event) => {
    setAnchorElNotif(anchorElNotif ? null : event.currentTarget);
  };
  const handleNotifClick = (chat) => {
    history.push("/chat/" + chat.chatid)
  }

  const openNotif = Boolean(anchorElNotif);
  const popperid = openNotif ? "notif-popper" : undefined;

  const handleOpenNavMenu = (event) => {
    setAnchorElNav(event.currentTarget);
  };
  const handleOpenUserMenu = (event) => {
    setAnchorElUser(event.currentTarget);
  };

  const handleCloseNavMenu = () => {
    setAnchorElNav(null);
  };

  const handleCloseUserMenu = () => {
    setAnchorElUser(null);
  };
  const handleChangePage = (url) => {
    history.push("/" + url);
  };
  const handleStatusChange = () => {
    if (StorageHelper.getUser()) {
      setLoggedIn(true);
    }
  };
  const handleLogOut = () => {
    AuthService.logout();
    setLoggedIn(false);
    history.push("/");
  };
  React.useEffect(() => {
    handleStatusChange();
  });

  React.useEffect(() => {
    (async () => {
      if(localStorage.getItem("user")){
        const username = StorageHelper.getUsername();
        const notifications = await NotificationService.getNotificationsByUsername(username);
        setNotifs(notifications.data);
      }
    })()
  }, [])

  const loggedInMenu = (
    <Box sx={{ flexGrow: 0, display: { xs: "flex", md: "flex" } }}>
      <IconButton
        id={popperid}
        size="large"
        aria-haspopup="true"
        aria-label="show 17 new notifications"
        onClick={handleOpenNotifMenu}
        color="primary"
      >
        <Badge badgeContent={notifs.length} color="error">
          <NotificationsIcon />
        </Badge>
      </IconButton>

      <Popper id={popperid} open={openNotif} anchorEl={anchorElNotif}>
        <Paper variant="outlined" elevation={1}>
          <List
            sx={{ width: "100%", maxWidth: 480, bgcolor: "background.paper" }}
          >
            <ListItem>
              <ListItem>
                <ListItemText primary="Notifications" />
              </ListItem>
            </ListItem>
            <Divider variant="inset" />
            {/* <ListItemButton alignItems="flex-start">
              <ListItemAvatar>
                <Avatar
                  alt="Remy Sharp"
                  src="https://material-ui.com/static/images/avatar/1.jpg"
                />
              </ListItemAvatar>
              <ListItemText
                primary="tester2"
                secondary={
                  <React.Fragment>
                    <Typography
                      sx={{ display: "inline" }}
                      component="span"
                      variant="body2"
                      color="text.primary"
                    >
                      tester2
                    </Typography>
                    {" - Why does your item not match the description?"}
                  </React.Fragment>
                }
              />
              <Box
                component="img"
                sx={{
                  height: 100,
                  width: 100,
                  maxHeight: { xs: 100, md: 78 },
                  maxWidth: { xs: 100, md: 78 },
                }}
                alt="The house from the offer."
                src="https://images.unsplash.com/photo-1512917774080-9991f1c4c750?auto=format&w=350&dpr=2"
              />
            </ListItemButton> */}
            {notifs.map((data, i) => (
              <ListItemButton alignItems="flex-start" onClick={() => handleNotifClick(data)}>
                <ListItemAvatar>
                  <Avatar
                    alt={data.takerName}
                    src={
                      "https://material-ui.com/static/images/avatar/" +
                      (i + 1) +
                      ".jpg"
                    }
                  />
                </ListItemAvatar>
                <ListItemText
                  primary={data.takeruserame}
                  secondary={
                    <React.Fragment>
                      <Typography
                        sx={{ display: "inline" }}
                        component="span"
                        variant="body2"
                        color="text.primary"
                      >
                        {data.takerusername}
                        <br></br>
                        {data.productName}
                      </Typography>
                      <br></br>
                      {data.chatMessage}
                    </React.Fragment>
                  }
                />
                {/* <Box
                  component="img"
                  sx={{
                    height: 100,
                    width: 100,
                    maxHeight: { xs: 100, md: 78 },
                    maxWidth: { xs: 100, md: 78 },
                  }}
                  alt="Product image"
                  src={data.productImageUrl}
                /> */}
              </ListItemButton>
            ))}
          </List>
        </Paper>
      </Popper>

      <Tooltip title="Open settings">
        <IconButton
          size="large"
          aria-label="account of current user"
          aria-controls="primary-search-account-menu"
          aria-haspopup="true"
          onClick={handleOpenUserMenu}
          color="primary"
        >
          <AccountCircle />
        </IconButton>
      </Tooltip>
      <Menu
        sx={{ mt: "45px" }}
        MenuListProps={{ sx: { backgroundColor: "#C7D8C6" } }}
        id="menu-appbar"
        anchorEl={anchorElUser}
        anchorOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        keepMounted
        transformOrigin={{
          vertical: "top",
          horizontal: "right",
        }}
        open={Boolean(anchorElUser)}
        onClose={handleCloseUserMenu}
      >
        {settings.map((setting) => {
          let clickFunction = () => handleChangePage(setting);
          if (setting === "logout") {
            clickFunction = () => handleLogOut();
          }
          return (
            <MenuItem
              key={setting}
              sx={{ backgroundColor: "#C7D8C6" }}
              onClick={() => {
                handleCloseUserMenu();
                clickFunction();
              }}
            >
              <Typography textAlign="center">
                {setting.charAt(0).toUpperCase() + setting.slice(1)}
              </Typography>
            </MenuItem>
          );
        })}
      </Menu>
    </Box>
  );
  const loggedOutMenu = (
    <Box sx={{ flexGrow: 0, display: { xs: "flex", md: "flex" } }}>
      {loggedOut.map((page) => (
        <Button
          key={page}
          onClick={() => {
            handleCloseNavMenu();
            handleChangePage(page);
          }}
          sx={{
            my: 2,
            display: "block",
            fontWeight: "bold",
            // "&:hover": {
            //     backgroundColor: "#fff",
            //     color: "#C7D8C6",
            // },
          }}
        >
          {page}
        </Button>
      ))}
    </Box>
  );

  return (
    <AppBar
      position="static"
      className={styles.navbar}
      sx={{ backgroundColor: "#C7D8C6" }}
    >
      <Container maxWidth="xl">
        <Toolbar disableGutters>
          <DeleteIcon
            sx={{ display: { xs: "none", md: "flex" }, mr: 1 }}
            color="primary"
          />
          <Typography
            variant="h6"
            noWrap
            component="a"
            href="/"
            sx={{
              mr: 2,
              display: { xs: "none", md: "flex" },
              fontFamily: "monospace",
              fontWeight: 700,
              color: "primary",
              // letterSpacing: "rem",
              textDecoration: "none",
            }}
          >
            Waste Away
          </Typography>

          <Box sx={{ flexGrow: 1, display: { xs: "flex", md: "none" } }}>
            <IconButton
              size="large"
              aria-label="account of current user"
              aria-controls="menu-appbar"
              aria-haspopup="true"
              onClick={handleOpenNavMenu}
            >
              <MenuIcon />
            </IconButton>
            <Menu
              id="menu-appbar"
              MenuListProps={{ sx: { backgroundColor: "#C7D8C6" } }}
              anchorEl={anchorElNav}
              anchorOrigin={{
                vertical: "bottom",
                horizontal: "left",
              }}
              keepMounted
              transformOrigin={{
                vertical: "top",
                horizontal: "left",
              }}
              open={Boolean(anchorElNav)}
              onClose={handleCloseNavMenu}
              sx={{
                display: { xs: "block", md: "none" },
              }}
            >
              {pages.map((page) => (
                <MenuItem
                  key={page}
                  sx={{ backgroundColor: "#C7D8C6" }}
                  onClick={() => {
                    handleCloseNavMenu();
                    handleChangePage(page);
                  }}
                >
                  <Typography textAlign="center">
                    {page.charAt(0).toUpperCase() + page.slice(1)}
                  </Typography>
                </MenuItem>
              ))}
            </Menu>
          </Box>
          <DeleteIcon
            sx={{ display: { xs: "flex", md: "none" }, mr: 1 }}
            color="primary"
          />
          <Typography
            variant="h5"
            noWrap
            component="a"
            href=""
            sx={{
              display: { xs: "flex", md: "none" },
              position: "absolute",
              flexGrow: 1,
              fontFamily: "monospace",
              fontWeight: 700,
              textDecoration: "none",
              marginLeft: "auto",
              marginRight: "auto",
              left: "40%",
              right: 0,
              textAlign: "center",
              color: "primary",
            }}
          >
            Waste Away
          </Typography>
          <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
            {pages.map((page) => (
              <Button
                key={page}
                onClick={() => handleChangePage(page)}
                sx={{ my: 2, display: "block", fontWeight: "bold" }}
              >
                {page}
              </Button>
            ))}
          </Box>
          {isLoggedIn && loggedInMenu}
          {!isLoggedIn && loggedOutMenu}
        </Toolbar>
      </Container>
    </AppBar>
  );
};
export default Header;
