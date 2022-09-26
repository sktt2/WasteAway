import React, { Component } from 'react';
// import BookService from '../services/BookService';
import 'bootstrap/dist/css/bootstrap.min.css';

class ListUsers extends Component {
    constructor(props) {
        super(props);

        this.state = {
            users:[] 
        };

        this.addUser = this.addUser.bind(this);
    }
        
    // componentDidMount() {
    //     BookService.getUsers()
    //         .then((res) => {
    //             this.setState({ users:res.data });
    //         })
    //         .catch(() => {
    //             this.props.history.push('/error');
    //         });
    // }
    
    addUser() {
        this.props.history.push('/add-user');
    }

    // deleteuser(id) {
    //     BookService.deleteBook(id).then((res) => {
    //         this.setState({books: this.state.books.filter(book => book.id !== id)});
    //     });
    // }


    render() {
        return (
            <div>
                <br></br>
                <div>
                    <button className="btn btn-primary" onClick={this.addUser}>Add User</button>
                </div>
                <br></br>
                <div className = "row">
                    <table className = "table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>User</th>
                                {/* <th>Actions</th> */}
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.users.map(
                                    user =>
                                    <tr key = {user.id}>
                                        <td>{user.id}</td>
                                        <td>{user.username}</td>
                                        {/* <td>
                                            <button className="btn btn-danger">Delete</button>
                                        </td> */}
                                    </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }

}

export default ListUsers