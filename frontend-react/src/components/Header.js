/* eslint-disable react-hooks/exhaustive-deps */
import * as React from "react"
import ListItemAvatar from "@mui/material/ListItemAvatar"
import Avatar from "@mui/material/Avatar"
import Divider from "@mui/material/Divider"
import AppBar from "@mui/material/AppBar"
import Box from "@mui/material/Box"
import Toolbar from "@mui/material/Toolbar"
import IconButton from "@mui/material/IconButton"
import CircleIcon from "@mui/icons-material/Circle"
import Typography from "@mui/material/Typography"
import Menu from "@mui/material/Menu"
import MenuIcon from "@mui/icons-material/Menu"
import Container from "@mui/material/Container"
import Button from "@mui/material/Button"
import Tooltip from "@mui/material/Tooltip"
import Badge from "@mui/material/Badge"
import List from "@mui/material/List"
import ListItemButton from "@mui/material/ListItemButton"
import ListItemText from "@mui/material/ListItemText"
import NotificationsIcon from "@mui/icons-material/Notifications"
import MenuItem from "@mui/material/MenuItem"
import AccountCircle from "@mui/icons-material/AccountCircle"
import StorageHelper from "../services/StorageHelper"
import { useHistory } from "react-router-dom"
import AuthService from "../services/AuthService"
import styles from "../styles/ComponentStyle.module.css"
import Popper from "@mui/material/Popper"
import Paper from "@mui/material/Paper"
import DeleteIcon from "@mui/icons-material/Delete"
import NotificationService from "../services/NotificationService"

const pages = ["products"]
const settings = ["profile", "chats", "logout"]
const loggedOut = ["login", "register"]

const Header = (props) => {
    const [anchorElNav, setAnchorElNav] = React.useState(null)
    const [anchorElUser, setAnchorElUser] = React.useState(null)
    const [anchorElNotif, setAnchorElNotif] = React.useState(null)
    const [notifs, setNotifs] = React.useState([])
    const [isLoggedIn, setLoggedIn] = React.useState(false)
    const history = useHistory()

    const handleOpenNotifMenu = (event) => {
        setAnchorElNotif(anchorElNotif ? null : event.currentTarget)
    }
    const handleNotifClick = async (data) => {
        if (data.read === false) {
            await NotificationService.updateNotificationIfRead(data.notificationId).catch((error) => {
                console.log(error)
            })
        }
        history.push("/chat/" + data.chatId)
    }

    const openNotif = Boolean(anchorElNotif)
    const popperid = openNotif ? "notif-popper" : undefined

    const handleOpenNavMenu = (event) => {
        setAnchorElNav(event.currentTarget)
    }
    const handleOpenUserMenu = (event) => {
        setAnchorElUser(event.currentTarget)
    }

    const handleCloseNavMenu = () => {
        setAnchorElNav(null)
    }

    const handleCloseUserMenu = () => {
        setAnchorElUser(null)
    }
    const handleChangePage = (url) => {
        history.push("/" + url)
    }
    const handleStatusChange = () => {
        if (StorageHelper.getUser()) {
            setLoggedIn(true)
        }
    }
    const handleLogOut = () => {
        AuthService.logout()
        setLoggedIn(false)
        history.push("/")
    }
    const getNotifications = async () => {
        const username = StorageHelper.getUsername()
        const messages = await NotificationService.getNotificationsByUsername(username).catch(
            () => {}
        )
        return filterChat(messages.data)
    }
    const filterChat = (messages) => {
        const chats = [...new Map(messages.map((item) => [item["chatId"], item])).values()]
        chats.sort((a, b) => {
            return b.notificationId - a.notificationId
        })
        return chats
    }
    React.useEffect(() => {
        handleStatusChange()
        if (isLoggedIn) {
            const setNotifications = async () => {
                const notifications = await getNotifications()
                setNotifs(notifications)
            }
            setNotifications()
        }
    }, [isLoggedIn])

    const getTotalNotifs = () => {
        let notifications = notifs
        let counter = 0
        notifications.map((data) => {
            console.log(data)
            if (!data.read) counter++
            return data
        })
        return counter
    }
    const loggedInMenu = (
        <Box sx={{ flexGrow: 0, display: { xs: "flex", md: "flex" } }}>
            <IconButton
                id={popperid}
                size="large"
                aria-haspopup="true"
                aria-label="notifications"
                onClick={handleOpenNotifMenu}
                color="primary">
                <Badge badgeContent={getTotalNotifs()} color="error">
                    <NotificationsIcon />
                </Badge>
            </IconButton>

            <Popper id={popperid} open={openNotif} anchorEl={anchorElNotif} sx={{ width: "18%" }}>
                <Paper
                    variant="outlined"
                    elevation={1}
                    style={{ maxHeight: "30vh", overflow: "auto" }}>
                    <List
                        display="flex"
                        sx={{
                            width: "100%",
                            bgcolor: "background.paper",
                            padding: 0,
                        }}>
                        {notifs.map((data, i) => {
                            return (
                                <Box>
                                    <Divider />
                                    <ListItemButton
                                        alignItems="flex-start"
                                        display="flex"
                                        onClick={() => handleNotifClick(data)}
                                        sx={{
                                            padding: "0",
                                            alignItems: "center",
                                            justifyContent: "center",
                                            // border: "1px solid",
                                            // borderColor: "grey"
                                        }}>
                                        <ListItemAvatar
                                            sx={{
                                                minWidth: "0",
                                                padding: "0 10px 10px 10px",
                                            }}>
                                            <Avatar sx={{ bgcolor: "#f44336" }}>
                                                {data.userUsername.charAt(0).toUpperCase()}
                                            </Avatar>
                                        </ListItemAvatar>
                                        <ListItemText
                                            sx={{ minWidth: "0" }}
                                            primary={data.userUserame}
                                            secondary={
                                                <React.Fragment>
                                                    <Typography
                                                        sx={{
                                                            display: "inline",
                                                            fontWeight: "bold",
                                                        }}
                                                        component="span"
                                                        variant="body2"
                                                        color="text.primary">
                                                        {data.productName}
                                                    </Typography>
                                                    <Typography color="text.primary">
                                                        {data.userUsername}
                                                    </Typography>
                                                    {data.notificationContent || "  "}
                                                </React.Fragment>
                                            }
                                        />{" "}
                                        {data.read ? null : (
                                            <CircleIcon
                                                sx={{
                                                    float: "right",
                                                    color: "#c7d8c6",
                                                    border: "2px",
                                                }}
                                            />
                                        )}
                                    </ListItemButton>
                                </Box>
                            )
                        })}
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
                    color="primary">
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
                onClose={handleCloseUserMenu}>
                {settings.map((setting) => {
                    let clickFunction = () => handleChangePage(setting)
                    if (setting === "logout") {
                        clickFunction = () => handleLogOut()
                    }
                    return (
                        <MenuItem
                            key={setting}
                            sx={{ backgroundColor: "#C7D8C6" }}
                            onClick={() => {
                                handleCloseUserMenu()
                                clickFunction()
                            }}>
                            <Typography textAlign="center">
                                {setting.charAt(0).toUpperCase() + setting.slice(1)}
                            </Typography>
                        </MenuItem>
                    )
                })}
            </Menu>
        </Box>
    )
    const loggedOutMenu = (
        <Box sx={{ flexGrow: 0, display: { xs: "flex", md: "flex" } }}>
            {loggedOut.map((page) => (
                <Button
                    key={page}
                    onClick={() => {
                        handleCloseNavMenu()
                        handleChangePage(page)
                    }}
                    sx={{
                        my: 2,
                        display: "block",
                        fontWeight: "bold",
                        // "&:hover": {
                        //     backgroundColor: "#fff",
                        //     color: "#C7D8C6",
                        // },
                    }}>
                    {page}
                </Button>
            ))}
        </Box>
    )

    return (
        <AppBar
            position="static"
            className={styles.navbar}
            sx={{ backgroundColor: "#C7D8C6", padding: "0" }}>
            <Container maxWidth="xl">
                <Toolbar disableGutters>
                    <img
                        src={"./logo.png"}
                        style={{ maxHeight: "30px", maxWidth: "30px" }}
                        alt=""></img>
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
                        }}>
                        WasteAway
                    </Typography>

                    <Box sx={{ flexGrow: 1, display: { xs: "flex", md: "none" } }}>
                        <IconButton
                            size="large"
                            aria-label="account of current user"
                            aria-controls="menu-appbar"
                            aria-haspopup="true"
                            onClick={handleOpenNavMenu}>
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
                            }}>
                            {pages.map((page) => (
                                <MenuItem
                                    key={page}
                                    sx={{ backgroundColor: "#C7D8C6" }}
                                    onClick={() => {
                                        handleCloseNavMenu()
                                        handleChangePage(page)
                                    }}>
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
                        }}>
                        Waste Away
                    </Typography>
                    <Box sx={{ flexGrow: 1, display: { xs: "none", md: "flex" } }}>
                        {pages.map((page) => (
                            <Button
                                key={page}
                                onClick={() => handleChangePage(page)}
                                sx={{ my: 2, display: "block", fontWeight: "bold" }}>
                                {page}
                            </Button>
                        ))}
                    </Box>
                    {isLoggedIn && loggedInMenu}
                    {!isLoggedIn && loggedOutMenu}
                </Toolbar>
            </Container>
        </AppBar>
    )
}
export default Header
