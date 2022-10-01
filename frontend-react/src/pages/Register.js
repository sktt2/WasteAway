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

            // Input Validation
            formErrors: { email: '', password: '', address: '', mobile: '' },
            emailValid: false,
            passwordValid: false,
            addressValid: false,
            mobileValid: false,
            formValid: false
        }
        this.changeUsername = this.changeUsername.bind(this);
        this.showPasswordClicked = this.showPasswordClicked.bind(this);
    }

    changeUsername(event) {
        this.setState({ username: event.target.value });
    }

    registerClicked = (event) => {
        let username = this.state.username;
        let email = this.state.email;
        let password = this.state.password;
        let address = this.state.address;
        let mobile = this.state.mobile;
        let authHeader = window.btoa(username + ':' + email + ':' + password + ':' + address + ':' + mobile);
        let user = { 'username': username, 'authHeader': authHeader };
        localStorage.setItem('user', JSON.stringify(user));
        this.props.history.push('/register');
    }

    loginClicked = (event) => {
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
        let addressValid = this.state.addressValid;
        let mobileValid = this.state.mobileValid;

        switch (fieldName) {
            // Email must contain both '@' sign and '.com'
            case 'email':
                emailValid = value.match(/^([\w.%+-]+)@([\w-]+\.)+([\w]{2,})$/i);
                fieldValidationErrors.email = emailValid ? '' : ' is invalid';
                break;
            // Password must be at least length 8
            case 'password':
                passwordValid = value.length >= 8;
                fieldValidationErrors.password = passwordValid ? '' : ' is too short';
                break;
            // Address must be at least length 15
            case 'address':
                addressValid = value.length >= 15;
                fieldValidationErrors.address = addressValid ? '' : ' is invalid';
                break;
            // Mobile must have only numbers and have length 8
            case 'mobile':
                mobileValid = value.length === 8 && value.match(/^[0-9\b]+$/);
                fieldValidationErrors.mobile = mobileValid ? '' : ' is invalid';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            emailValid: emailValid,
            passwordValid: passwordValid,
            addressValid: addressValid,
            mobileValid: mobileValid
        }, this.validateForm);
    }

    validateForm() {
        this.setState({ formValid: this.state.emailValid && this.state.passwordValid && this.state.addressValid && this.state.mobileValid });
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
                            <div className={`form-group ${this.errorClass(this.state.formErrors.address)}`}>
                                <label>Home Address</label>
                                <input placeholder="address" name="address" required className="form-control" value={this.state.address} onChange={this.handleUserInput} />
                            </div>
                            <div className={`form-group ${this.errorClass(this.state.formErrors.mobile)}`}>
                                <label>Mobile Phone</label>
                                <input placeholder="mobile" name="mobile" required className="form-control" value={this.state.mobile} onChange={this.handleUserInput} />
                            </div>
                            <br></br>
                            <button className="btn btn-success" onClick={this.loginClicked} disabled={!this.state.formValid}>Register</button>
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