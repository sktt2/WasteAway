import React, { Component } from 'react';
import BookService from '../services/BookService';

class UpdateBook extends Component {
    constructor(props) {
        super(props);

        this.state = {
            id: this.props.match.params.id,
            title: '',
        };

        this.changeTitleHandler = this.changeTitleHandler.bind(this);
        this.saveBook = this.saveBook.bind(this);
        this.cancel = this.cancel.bind(this);
    }

    componentDidMount() {
        BookService.getBookById(this.state.id).then((res) => {
            let book = res.data;
            this.setState(
                {title: book.title}
            );
        });
    }

    changeTitleHandler = (event) => {
        this.setState({title: event.target.value});
    }

    saveBook = (event) => {
        event.preventDefault();
        let book = {title: this.state.title};
        let bookId = this.state.id;
        BookService.updateBook(book, bookId)
            .then(res => {
                this.props.history.push('/list-books');
            })
            .catch(() => {
                this.props.history.push('/error');
            });
    }

    cancel() {
        this.props.history.push('/list-books');
    }

    render() {
        return (
            <div className="container">
                <br></br>
                <div className="card col-md-6 offset-md-3 offset-md-3">
                    <h4 className="text-center">Update Book</h4>
                    <div className = "card-body">
                        <form>
                            <div>
                                <label>Title</label>
                                <input placeholder="title" name="title" className="form-control" value={this.state.title} onChange={this.changeTitleHandler}/>
                            </div>
                            <br></br>
                            <button className="btn btn-success" onClick={this.saveBook}>Save</button>
                            <button className="btn btn-danger" onClick={this.cancel} style={{marginLeft: "10px"}}>Cancel</button>
                        </form>
                    </div>
                </div>
            </div>
            
        );
    }
}

export default UpdateBook;