import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

// From components
import { FormErrors } from '../components/FormErrors';

class ForgotPass extends Component {
    constructor(props) {
        super(props)
        this.state = {
            username: '',
            showpassword: 'password',

            // Input Validation
            formErrors: { password: '' },
            passwordValid: false,
            formValid: false
        }
        this.changeUsername = this.changeUsername.bind(this);
        this.showPasswordClicked = this.showPasswordClicked.bind(this);
    }

    changeUsername(event) {
        this.setState({ username: event.target.value });
    }

    changePassClicked = (event) => {
        event.preventDefault();
        let username = this.state.username;
        let password = this.state.password;
        let authHeader = window.btoa(username + ':' + password);
        let user = { 'username': username, 'authHeader': authHeader };
        localStorage.setItem('user', JSON.stringify(user));
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
        let passwordValid = this.state.passwordValid;

        switch (fieldName) {
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
            passwordValid: passwordValid
        }, this.validateForm);
    }

    validateForm() {
        this.setState({ formValid: this.state.passwordValid });
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
                            <div classname={`form-group ${this.errorClass(this.state.formErrors.password)}`}>
                                <label>New Password</label>
                                <input placeholder="Your new password..." name="password" required className="form-control" type={this.state.showpassword} value={this.state.password} onChange={this.handleUserInput} />
                            </div>
                            <div>
                                <label>Show Password</label>
                                <input type="checkbox" name="showpassword" clasName="form-control" onChange={this.showPasswordClicked}></input>
                            </div>
                            <button className="btn btn-success" disabled={!this.state.formValid}>Change password</button>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default ForgotPass