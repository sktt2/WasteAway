import 'bootstrap/dist/css/bootstrap.min.css';
import ListBooks from './components/ListBooks.js';
import {HashRouter as Router, Route, Switch} from 'react-router-dom'
import Header from './components/Header.js';
import AddBook from './components/AddBook.js';
import UpdateBook from './components/UpdateBook.js';
import ListUsers from './components/ListUsers.js';
import AddUser from './components/AddUser.js';
import Login from './components/Login.js';
import React, { Component } from 'react';
import { Container } from 'react-bootstrap';
import Logout from './components/Logout.js';
import Error from './components/Error.js';

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
              <Header />
              <Switch> 
                <Route path = "/login" component={Login}/> 
                <Route path = "/list-books" component = {ListBooks}></Route>
                <Route path = "/add-book" component = {AddBook}></Route>
                <Route path = "/list-users" component = {ListUsers}></Route>
                <Route path = "/add-user" component = {AddUser}></Route>  
                <Route path = "/logout" component = {Logout}></Route>
                <Route path = "/error" component = {Error}></Route>
                <Route path = "/" component={Login}/> 
              </Switch>
          </Router>
        </Container>
    );
  }


}

export default App;