import * as React from "react"
import AppBar from "@mui/material/AppBar"
import Box from "@mui/material/Box"
import Toolbar from "@mui/material/Toolbar"
import IconButton from "@mui/material/IconButton"
import Typography from "@mui/material/Typography"
import Menu from "@mui/material/Menu"
import MenuIcon from "@mui/icons-material/Menu"
import Container from "@mui/material/Container"
import Button from "@mui/material/Button"
import Tooltip from "@mui/material/Tooltip"
import Badge from "@mui/material/Badge"
import NotificationsIcon from "@mui/icons-material/Notifications"
import MenuItem from "@mui/material/MenuItem"
import AccountCircle from "@mui/icons-material/AccountCircle"
import StorageHelper from "../services/StorageHelper"
import { useHistory } from "react-router-dom"
import AuthService from "../services/AuthService"
import styles from "../styles/ComponentStyle.module.css"
// import { fontWeight } from "@mui/system"
import DeleteIcon from "@mui/icons-material/Delete"

const pages = ["products"]
const settings = ["profile", "logout", "settings"]
const loggedOut = ["login", "register"]

const Header = (props) => {
    const [anchorElNav, setAnchorElNav] = React.useState(null)
    const [anchorElUser, setAnchorElUser] = React.useState(null)
    const [isLoggedIn, setLoggedIn] = React.useState(false)
    const history = useHistory()

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
    React.useEffect(() => {
        handleStatusChange()
    })
    const loggedInMenu = (
        <Box sx={{ flexGrow: 0, display: { xs: "flex", md: "flex" } }}>
            <IconButton size="large" aria-label="show 17 new notifications" color="primary">
                <Badge badgeContent={17} color="error">
                    <NotificationsIcon />
                </Badge>
            </IconButton>
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
        <AppBar position="static" className={styles.navbar} sx={{ backgroundColor: "#C7D8C6" }}>
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
                        }}>
                        Waste Away
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
