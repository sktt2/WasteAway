import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

// From components
import { FormErrors } from '../components/FormErrors';

class Register extends Component {
    constructor(props) {
        super(props)
        this.state = {
            username: '',
            showpassword: 'password',
            address: '',
            mobile: '',

            // Input Validation
            formErrors: { email: '', password: '' },
            emailValid: false,
            passwordValid: false,
            formValid: false
        }
        this.changeUsername = this.changeUsername.bind(this);
        this.showPasswordClicked = this.showPasswordClicked.bind(this);
        this.changeAddress = this.changeAddress.bind(this);
        this.changeMobile = this.changeMobile.bind(this);
    }

    changeUsername(event) {
        this.setState({ username: event.target.value });
    }

    changeAddress(event) {
        this.setState({ address: event.target.value });
    }

    changeMobile(event) {
        this.setState({ mobile: event.target.value });
    }

    registerClicked = (event) => {
        //event.preventDefault();
        let username = this.state.username;
        let password = this.state.password;
        let authHeader = window.btoa(username + ':' + password);
        let user = { 'username': username, 'authHeader': authHeader };
        localStorage.setItem('user', JSON.stringify(user));
        this.props.history.push('/register');
    }

    loginClicked = (event) => {
        event.preventDefault();
        this.props.history.push('/login');
    }

    showPasswordClicked = (event) => {
        if (this.state.showpassword === 'password') {
            this.setState({ showpassword: 'text' });
        } else {
            this.setState({ showpassword: 'password' });
        }
    }

    handleUserInput = (e) => {
        const name = e.target.name;
        const value = e.target.value;
        this.setState({ [name]: value },
            () => { this.validateField(name, value) });
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let emailValid = this.state.emailValid;
        let passwordValid = this.state.passwordValid;

        switch (fieldName) {
            case 'email':
                emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
                fieldValidationErrors.email = emailValid ? '' : ' is invalid';
                break;
            // Password must contain at least 8 characters
            case 'password':
                passwordValid = value.length >= 8;
                fieldValidationErrors.password = passwordValid ? '' : ' is too short';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            emailValid: emailValid,
            passwordValid: passwordValid
        }, this.validateForm);
    }

    validateForm() {
        this.setState({ formValid: this.state.emailValid && this.state.passwordValid });
    }

    errorClass(error) {
        return (error.length === 0 ? '' : 'has-error');
    }

    render() {
        return (
            <div className="container">
                <br></br>
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <div className="card-body">
                        <form>
                            <div className="panel panel-default">
                                <FormErrors formErrors={this.state.formErrors} />
                            </div>
                            <div>
                                <label>Username</label>
                                <input placeholder="username" name="username" required className="form-control" value={this.state.username} onChange={this.changeUsername} />
                            </div>
                            <div className={`form-group ${this.errorClass(this.state.formErrors.email)}`}>
                                <label>Email</label>
                                <input placeholder="email" name="email" required className="form-control" value={this.state.email} onChange={this.handleUserInput} />
                            </div>
                            <div className={`form-group ${this.errorClass(this.state.formErrors.password)}`}>
                                <label>Password</label>
                                <input placeholder="password" name="password" required className="form-control" type={this.state.showpassword} value={this.state.password} onChange={this.handleUserInput} />
                            </div>
                            <div>
                                <label>Show Password</label>
                                <input type="checkbox" name="showpassword" clasName="form-control" onChange={this.showPasswordClicked}></input>
                            </div>
                            <div>
                                <label>Home Address</label>
                                <input placeholder="address" name="address" required className="form-control" value={this.state.address} onChange={this.changeAddress} />
                            </div>
                            <div>
                                <label>Mobile Phone</label>
                                <input placeholder="mobile" name="mobile" required className="form-control" value={this.state.mobile} onChange={this.changeMobile} />
                            </div>
                            <br></br>
                            <button className="btn btn-success" disabled={!this.state.formValid}>Register</button>
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