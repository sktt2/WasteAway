import React, { Component } from 'react';
// import BookService from '../services/BookService';

class AddUser extends Component {
    constructor(props) {
        super(props);

        this.state = {
            username: '',
            password: '',
            authorities: ''
        };

        this.changeUsernameHandler = this.changeUsernameHandler.bind(this);
        this.changePasswordHandler = this.changePasswordHandler.bind(this);
        this.changeAuthoritiesHandler = this.changeAuthoritiesHandler.bind(this);
        this.saveUser = this.saveUser.bind(this);
        this.cancel = this.cancel.bind(this);
    }

    changeUsernameHandler = (event) => {
        this.setState({username: event.target.value});
    }

    changePasswordHandler = (event) => {
        this.setState({password: event.target.value});
    }

    changeAuthoritiesHandler = (event) => {
        this.setState({authorities: event.target.value});
    }

    saveUser = (event) => {
        // event.preventDefault();
        // let newuser = {username:this.state.username, password:this.state.password, authorities:this.state.authorities};
        // console.log("new user ", JSON.stringify(newuser));
        // BookService.addUser(newuser)
        //     .then(res => {
        //         this.props.history.push('/list-users');
        //     })
        //     .catch(() => {
        //         this.props.history.push('/error');
        //     });
        this.props.history.push('/list-users');
    }

    cancel() {
        this.props.history.push('/list-users');
    }

    render() {
        return (
            <div className="container">
                <br></br>
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <h4 className="text-center">Add User</h4>
                    <div className = "card-body">
                        <form>
                            <div>
                                <label>Username</label>
                                <input placeholder="username" name="username" className="form-control" value={this.state.username} onChange={this.changeUsernameHandler}/>
                            </div>
                            <div>
                                <label>Password</label>
                                <input placeholder="password" name="password" className="form-control" value={this.state.password} onChange={this.changePasswordHandler}/>
                            </div>
                            <div>
                                <label>Authorities</label>
                                <input placeholder="ROLE_ADMIN" name="authorities" className="form-control" value={this.state.authorities} onChange={this.changeAuthoritiesHandler}/>
                            </div>
                            <br></br>
                            <button className="btn btn-success" onClick={this.saveUser}>Save</button>
                            <button className="btn btn-danger" onClick={this.cancel} style={{marginLeft: "10px"}}>Cancel</button>
                        </form>
                    </div>
                </div>
            </div>
            
        );
    }
}

export default AddUser;