import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

// From components
import { FormErrors } from '../components/FormErrors';
import AuthService from '../services/AuthService';

class Register extends Component {
    constructor(props) {
        super(props)
        this.state = {
            username: '',
            showpassword: 'password',

            // Input Validation
            formErrors: {
                username: '', name: '', email: '', password: '',
                confirmpassword: '', address: '', mobile: ''
            },
            usernameValid: false,
            nameValid: false,
            emailValid: false,
            passwordValid: false,
            confirmPasswordValid: false,
            addressValid: false,
            mobileValid: false,
            formValid: false
        }
        this.showPasswordClicked = this.showPasswordClicked.bind(this);
    }

    registerClicked = (event) => {
        let username = this.state.username;
        let name = this.state.name
        let email = this.state.email;
        let password = this.state.password;
        let confirmpassword = this.state.confirmpassword;
        let address = this.state.address;
        let mobile = this.state.mobile;
        let authHeader = window.btoa(username + ':' + name + ':' + email + ':' + password + ':' + confirmpassword + ':' + address + ':' + mobile);
        let user = { 'username': username, 'authHeader': authHeader };
        AuthService.register(username, name, email, password, confirmpassword, address, mobile)
            .then(response => {
                console.log(response)
                AuthService.signin(username, password)
                localStorage.setItem('user', JSON.stringify(user));
                this.props.history.push('/products')
            })
            .catch(response => {
                console.log(response)
                this.props.history.push('/register')
            })
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
        let usernameValid = this.state.usernameValid
        let nameValid = this.state.nameValid
        let emailValid = this.state.emailValid;
        let passwordValid = this.state.passwordValid;
        let confirmPasswordValid = this.state.confirmPasswordValid;
        let addressValid = this.state.addressValid;
        let mobileValid = this.state.mobileValid;

        switch (fieldName) {
            // Username must be at least length 5
            case 'username':
                usernameValid = value.length >= 5
                fieldValidationErrors.username = usernameValid ? '' : ' is too short'
                break
            // Name must not be blank
            case 'name':
                nameValid = value.length >= 1;
                fieldValidationErrors.name = nameValid ? '' : ' must not be blank'
                break
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
            // Confirmpassword must match password
            case 'confirmpassword':
                confirmPasswordValid = this.state.password === this.state.confirmpassword;
                fieldValidationErrors.confirmpassword = confirmPasswordValid ? '' : ' is invalid';
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
            usernameValid: usernameValid,
            nameValid: nameValid,
            emailValid: emailValid,
            passwordValid: passwordValid,
            confirmPasswordValid: confirmPasswordValid,
            addressValid: addressValid,
            mobileValid: mobileValid
        }, this.validateForm);
    }

    validateForm() {
        this.setState({
            formValid: this.state.usernameValid && this.state.nameValid
                && this.state.emailValid && this.state.passwordValid
                && this.state.confirmPasswordValid && this.state.addressValid
                && this.state.mobileValid
        });
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
                                <input placeholder="username" name="username" required className="form-control" value={this.state.username} onChange={this.handleUserInput} />
                            </div>
                            <div>
                                <label>Name</label>
                                <input placeholder="name" name="name" required className="form-control" value={this.state.name} onChange={this.handleUserInput} />
                            </div>
                            <div className={`form-group ${this.errorClass(this.state.formErrors.email)}`}>
                                <label>Email</label>
                                <input placeholder="email" name="email" required className="form-control" value={this.state.email} onChange={this.handleUserInput} />
                            </div>
                            <label>Password</label>
                            <div className={`input-group ${this.errorClass(this.state.formErrors.password)}`}>
                                <input placeholder="password" name="password" required className="form-control" type={this.state.showpassword} value={this.state.password} onChange={this.handleUserInput} />
                                <div class="input-group-append">
                                    <button class="btn bg-transparent btn-outline-secondary" type="button" onClick={this.showPasswordClicked}>
                                        {this.state.showpassword === "password" ? <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-slash" viewBox="0 0 16 16">
                                            <path d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7.028 7.028 0 0 0-2.79.588l.77.771A5.944 5.944 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.134 13.134 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755-.165.165-.337.328-.517.486l.708.709z" />
                                            <path d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829l.822.822zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829z" />
                                            <path d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z" />
                                        </svg> : <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                                            <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z" />
                                            <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z" />
                                        </svg>}
                                    </button>
                                </div>
                            </div>
                            <label>Confirm Password</label>
                            <div className={`input-group ${this.errorClass(this.state.formErrors.confirmpassword)}`}>
                                <input placeholder="confirm password" name="confirmpassword" required className="form-control" type={this.state.showpassword} value={this.state.confirmpassword} onChange={this.handleUserInput} />
                                <div class="input-group-append">
                                    <button class="btn bg-transparent btn-outline-secondary" type="button" onClick={this.showPasswordClicked}>
                                        {this.state.showpassword === "password" ? <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye-slash" viewBox="0 0 16 16">
                                            <path d="M13.359 11.238C15.06 9.72 16 8 16 8s-3-5.5-8-5.5a7.028 7.028 0 0 0-2.79.588l.77.771A5.944 5.944 0 0 1 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.134 13.134 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755-.165.165-.337.328-.517.486l.708.709z" />
                                            <path d="M11.297 9.176a3.5 3.5 0 0 0-4.474-4.474l.823.823a2.5 2.5 0 0 1 2.829 2.829l.822.822zm-2.943 1.299.822.822a3.5 3.5 0 0 1-4.474-4.474l.823.823a2.5 2.5 0 0 0 2.829 2.829z" />
                                            <path d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z" />
                                        </svg> : <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                                            <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z" />
                                            <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z" />
                                        </svg>}
                                    </button>
                                </div>
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
                            <button className="btn btn-success" disabled={!this.state.formValid} onClick={this.registerClicked}>Register</button>
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