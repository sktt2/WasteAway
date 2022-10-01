import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import AuthService from '../services/AuthService';


class Login extends Component {
    constructor(props) {
        super(props)
        this.state = {
            username: '',
            password: '',
            showpassword: 'password',
            isrememberme: false,
        }
        this.changeUsername = this.changeUsername.bind(this);
        this.changePassword = this.changePassword.bind(this);
        this.showPasswordClicked = this.showPasswordClicked.bind(this);
    }

    componentDidMount() {
        if (localStorage.hasOwnProperty('rememberme')) {
            var retrieveUsername = JSON.parse(localStorage.getItem('rememberme')) ;
            let user = retrieveUsername.username;
            this.setState({ username: user });
        }
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
        let rememberme = this.state.isrememberme;
        localStorage.setItem('user', JSON.stringify(user));
        localStorage.setItem('rememberme', rememberme ? JSON.stringify(user) : '');
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

    isRememberMeClicked = (event) => {
        if (this.state.isrememberme === false) {
            this.setState({ isrememberme: true });
        } else {
            this.setState({ isrememberme: false });
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
                            <label for="password-input">Password</label>
                            <div class="input-group mb-3">
                                <input type={this.state.showpassword} id="password-input" required className="form-control" value={this.state.password} onChange={this.changePassword} placeholder="Password"/>
                                <div class="input-group-append">
                                <button class="btn bg-transparent btn-outline-secondary" type="button" onClick={this.showPasswordClicked}>
                                {this.state.showpassword==="password"? <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-slash" viewBox="0 0 16 16">
  <path d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7.028 7.028 0 0 0-2.79.588l.77.771A5.944 5.944 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.134 13.134 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755-.165.165-.337.328-.517.486l.708.709z"/>
  <path d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829l.822.822zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829z"/>
  <path d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z"/>
</svg> : <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
  <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
  <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
</svg> }
                                </button>
                                </div>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" value="" id="flexCheckDefault" onChange={this.isRememberMeClicked}/>
                                <label class="form-check-label" for="flexCheckDefault">
                                Remember me
                                </label>
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