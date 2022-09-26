import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import Header from './components/Header.js';
import ListUsers from './components/ListUsers.js';
import AddUser from './components/AddUser.js';
import Login from './pages/Login.js';
import React, { Component } from 'react';
import { Container } from 'react-bootstrap';
import Logout from './pages/Logout.js';
import Error from './components/Error.js';
import Product from './pages/Product.js';
import Register from './pages/Register.js'
import AddProduct from './pages/AddProduct.js';
import ForgotPass from './pages/ForgotPass.js';
/**
 * app component render a router, the router is responsible to change the routes in the browser.
 * the switch component switches between routes, the path decides the route in browser
 * component decides which component to render for that route.
*/
class App extends Component {

  render() {
    return (
        <Container>
          <Router>
              <Header/>
              <Switch> 
                <Route path = "/login" component={Login}/> 
                <Route path = "/list-users" component = {ListUsers}></Route>
                <Route path = "/add-user" component = {AddUser}></Route>  
                <Route path = "/logout" component = {Logout}></Route>
                <Route path = "/error" component = {Error}></Route>
                <Route path = "/product" component = {Product}></Route>
                <Route path = "/addproduct" component = {AddProduct}></Route>
                <Route path = "/register" component = {Register}></Route>
                <Route path = "/forgotpass" component = {ForgotPass}></Route>
                <Route path = "/" component={Login}/> 
              </Switch>
          </Router>
        </Container>
    );
  }


}

export default App;