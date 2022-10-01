import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import AuthService from '../services/AuthService';

class Login extends Component {
    constructor(props) {
        super(props)
        this.state = {
            username: '',
            password: '',
            showpassword: 'password'
        }
        this.changeUsername = this.changeUsername.bind(this);
        this.changePassword = this.changePassword.bind(this);
        this.showPasswordClicked = this.showPasswordClicked.bind(this);
    }

    changeUsername(event) {
        this.setState({ username: event.target.value });
    }

    changePassword(event) {
        this.setState({ password: event.target.value });
    }

    loginClicked = (event) => {
        event.preventDefault();
        let username = this.state.username;
        let password = this.state.password;
        let authHeader = window.btoa(username + ':' + password);
        let user = { 'username': username, 'authHeader': authHeader };
        localStorage.setItem('user', JSON.stringify(user));
        AuthService.signin(username, password)
        .then(response => {
            console.log(response)
            this.props.history.push('/product');
        })
        .catch(response => {
            console.log(response)
            this.props.history.push('/login')
        })
    }

    registerClicked = (event) => {
        event.preventDefault();
        this.props.history.push('/register');
    }

    showPasswordClicked = (event) => {
        if (this.state.showpassword === 'password') {
            this.setState({ showpassword: 'text' });
        } else {
            this.setState({ showpassword: 'password' });
        }
    }

    render() {
        return (
            <div className="container">
                <br></br>
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <div className="card-body">
                        <form>
                            <div>
                                <label>Username</label>
                                <input placeholder="username" name="username" required className="form-control" value={this.state.username} onChange={this.changeUsername} />
                            </div>
                            <div>
                                <label>Password</label>
                                <input placeholder="password" name="password" type={this.state.showpassword} required className="form-control" value={this.state.password} onChange={this.changePassword} />
                            </div>
                            <div>
                                <label>Show Password</label>
                                <input type="checkbox" name="showpassword" className="form-control" onChange={this.showPasswordClicked}></input>
                            </div>
                            <a href="/forgotpass" className="card-link">Forgot password?</a>
                            <br></br>
                            <button className="btn btn-success" onClick={this.loginClicked}>Login</button>
                            {" "}
                            <button className="btn btn-success" onClick={this.registerClicked}>Register</button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default Login