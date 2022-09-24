import React, { Component } from 'react';
import BookService from '../services/BookService';
import 'bootstrap/dist/css/bootstrap.min.css';


class ListBooks extends Component {
    constructor(props) {
        super(props);
        //initialising the state of books as an empty array which eventally will contain the books list
        this.state = {
            books:[],
        };
        this.addBook = this.addBook.bind(this);
    }
/** 
 * componentDidMount is the lifecycle method which is called when component mounts
    and it is called only once. This is best place to call the api and get the books list.
 * below the books api is being called and the response which is the books list is set to the state
    and whenever the state of component changes the component rerenders itself
    so the user can see the book list 
*/
    componentDidMount() {
        BookService.getBooks()
            .then((res) => {
                this.setState({ books:res.data });
            })
            .catch(() => {
                this.props.history.push('/error');
            });
    }
    
    addBook() {
        this.props.history.push('/add-book');
    }

    updateBook(id) {
        this.props.history.push(`/update-book/${id}`);
    }

    deleteBook(id) {
        BookService.deleteBook(id)
            .then((res) => {
                this.setState({books: this.state.books.filter(book => book.id !== id)});
            })
            .catch(() => {
                this.props.history.push('/error');
            });
    }

    /**
     * TODO: Implement the method to push the list review route `/list-reviews/${id}`
     * into the props history
     */

    render() {
        return (
            <div>
                <br></br>
                <div>
                    <button className="btn btn-primary" onClick={this.addBook}>Add Book</button>
                </div>
                <br></br>
                <div className = "row">
                    <table className = "table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Title</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.books.map(
                                    book =>
                                    <tr key = {book.id}>
                                        <td>{book.id}</td>
                                        <td>{book.title}</td>
                                        <td>
                                            <button onClick={()=>this.updateBook(book.id)} className="btn btn-info">Update</button>
                                            <button style={{marginLeft: "10px"}} onClick={()=>this.deleteBook(book.id)} className="btn btn-danger">Delete</button>
                                        </td>
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

export default ListBooks