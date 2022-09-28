import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

class Register extends Component {
    constructor(props){
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
        this.setState({username: event.target.value});
    }

    changePassword(event) {
        this.setState({password: event.target.value});
    }

    registerClicked = (event) => {
        event.preventDefault();
        let username = this.state.username;
        let password = this.state.password;
        let authHeader = window.btoa(username + ':' + password);
        let user = {'username': username, 'authHeader': authHeader};
        localStorage.setItem('user', JSON.stringify(user));
        this.props.history.push('/login');
    }

    loginClicked = (event) => {
        event.preventDefault();
        this.props.history.push('/login');
    }

    showPasswordClicked = (event) => {
        if (this.state.showpassword === 'password') {
            this.setState({showpassword: 'text'});
        } else {
            this.setState({showpassword: 'password'});
        }
    }

    render() {
        return (
            <div className="container">
                <br></br>
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <div className = "card-body">
                        <form>
                            <div>
                                <label>Username</label>
                                <input placeholder="username" name="username" className="form-control" value={this.state.username} onChange={this.changeUsername}/>
                            </div>
                            <div>
                                <label>Password</label>
                                <input placeholder="password" name="password" className="form-control" type = {this.state.showpassword} value={this.state.password} onChange={this.changePassword}/>
                            </div>
                            <div>
                                <label>Show Password</label>
                                <input type="checkbox" name="showpassword" clasName="form-control" onChange={this.showPasswordClicked}></input>
                            </div>
                            <br></br>
                            <button className="btn btn-success" onClick={this.registerClicked}>Register</button>
                            {" "}
                            <button className="btn btn-success" onClick={this.loginClicked}>Return to Login</button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default Register