import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Header from "./components/Header.js";
import Login from "./pages/Login.js";
import AlreadyLoggedIn from "./pages/AlreadyLoggedIn.js";
import React, { Component } from "react";
import { Container } from "react-bootstrap";
import Logout from "./pages/Logout.js";
import Error from "./components/Error.js";
import Product from "./pages/Product.js";
import ProductDetail from "./pages/ProductDetail";
import Register from "./pages/Register.js";
import AddProduct from "./pages/AddProduct.js";
import ForgotPass from "./pages/ForgotPass.js";
import Profile from "./pages/Profile";
import EditProduct from "./pages/EditProduct.js";

/**
 * app component render a router, the router is responsible to change the routes in the browser.
 * the switch component switches between routes, the path decides the route in browser
 * component decides which component to render for that route.
 */
class App extends Component {

    render() {
        return (
            <Container fluid>
                <Router forceRefresh={true}>
                    <Header />
                    <Container>
                        <Switch>
                            <Route path="/login" component={Login} />
                            <Route path="/alreadyloggedin" component={AlreadyLoggedIn} />
                            <Route path="/logout" component={Logout}></Route>
                            <Route path="/error" component={Error}></Route>
                            <Route path="/product/edit/:id" component={EditProduct}></Route>
                            <Route path="/product/:id" component={ProductDetail}></Route>
                            <Route path="/profile" component={Profile}></Route>
                            <Route path="/product" component={Product}></Route>
                            <Route path="/addproduct" component={AddProduct}></Route>
                            <Route path="/register" component={Register}></Route>
                            <Route path="/forgotpass" component={ForgotPass}></Route>
                            <Route path="/" component={Product} />
                        </Switch>
                    </Container>
                </Router>
            </Container>
        );
    }
}

export default App;
