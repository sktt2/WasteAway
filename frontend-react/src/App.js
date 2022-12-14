import { BrowserRouter as Router, Route, Switch, Redirect } from "react-router-dom"
import Header from "./components/Header.js"
import Login from "./pages/Login.js"
import React, { Component } from "react"
import { Container } from "react-bootstrap"
import Error from "./components/Error.js"
import Product from "./pages/Product.js"
import ProductDetail from "./pages/ProductDetail"
import Register from "./pages/Register.js"
import Recommendation from "./pages/Recommendation.js"
import AddProduct from "./pages/AddProduct.js"
import ChangePass from "./pages/ChangePass.js"
import Profile from "./pages/Profile"
import EditProduct from "./pages/EditProduct.js"
import Chat from "./pages/Chat.js"
import ChatNavigator from "./pages/ChatNavigator.js"
import EditProfile from "./pages/EditProfile.js"
import { ThemeProvider, CssBaseline } from "@mui/material"
import { appTheme } from "./styles/appTheme"

/**
 * app component render a router, the router is responsible to change the routes in the browser.
 * the switch component switches between routes, the path decides the route in browser
 * component decides which component to render for that route.
 */
class App extends Component {
    render() {
        return (
            <ThemeProvider theme={appTheme}>
                <CssBaseline enableColorScheme />
                <Container fluid style={{ padding: "0px 0px" }}>
                    <Router forceRefresh={true}>
                        <Header />
                        <Container style={{ marginLeft: "5vw", marginRight: "5vw" }}>
                            <Switch>
                                <Route path="/login" component={Login} />
                                <Route path="/error" component={Error}></Route>
                                <Route path="/product/edit/:id" component={EditProduct}></Route>
                                <Route path="/product/:id" component={ProductDetail}></Route>
                                <Route path="/editprofile" component={EditProfile}></Route>
                                <Route path="/changepass" component={ChangePass}></Route>
                                <Route path="/profile" component={Profile}></Route>
                                <Route path="/products" component={Product}></Route>
                                <Route path="/addproduct" component={AddProduct}></Route>
                                <Route path="/register" component={Register}></Route>
                                <Route path="/recommendation" component={Recommendation}></Route>
                                <Route path="/chat/:id" component={Chat}></Route>
                                <Route path="/chats" component={ChatNavigator}></Route>
                                <Route exact path="/" component={Product} />
                                <Redirect to="/error" />
                            </Switch>
                        </Container>
                    </Router>
                </Container>
            </ThemeProvider>
        )
    }
}

export default App
