import React, { Component } from 'react';
import BookService from '../services/BookService';

class ListReviews extends Component {
    constructor(props) {
        super(props);

        this.state = {
            bookId: this.props.match.params.bookId,
            title:'',
            reviews:[]
        };

    }

    componentDidMount() {
        BookService.getReviewsByBookId(this.state.bookId).then((res) => {
            this.setState({ reviews: res.data });
        });
        BookService.getBookById(this.state.bookId).then((res) => {
            console.log(res.data);
            this.setState({ title: res.data.title});
        });
    }

    addReview(bookId) {
        this.props.history.push(`/add-review/${bookId}`);
    }

    updateReview(bookId, reviewId) {
        this.props.history.push(`/update-review/${bookId}/${reviewId}`);
    }

    deleteReview(bookId, reviewId) {
        BookService.deleteReviewByBookIdReviewId(bookId,  reviewId)
            .then((res) => {
                this.setState({reviews: this.state.reviews.filter(review => review.id !== reviewId)});
                this.props.history.push(`/list-reviews/${bookId}`);
            })
            .catch(() => {
                this.props.history.push('/error');
            });
    }

    render() {
        return (
            <div>
                <br></br>
                <h4 className = "text-center">Reviews for book: {this.state.title}</h4>
                <div>
                    <button className="btn btn-primary" onClick={()=>this.addReview(this.state.bookId)}>Add review</button>
                </div>
                <br></br>
                <div className = "row">
                    <table className = "table table-striped table-bordered">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Reviews</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.reviews.map(
                                    review =>
                                    <tr key = {review.id}>
                                        <td>{review.id}</td>
                                        <td>{review.review}</td>
                                        <td>
                                            <button className="btn btn-info" onClick={()=>this.updateReview(this.state.bookId, review.id)}>Update</button>
                                            <button style={{marginLeft: "10px"}} className="btn btn-danger" onClick={()=>this.deleteReview(this.state.bookId, review.id)}>Delete</button>
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

export default ListReviews;