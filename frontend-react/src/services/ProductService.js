import axios from 'axios';

const API_URL = "http://localhost:8080"; 

class ProductService {

//     authHeader = () => {
//         let user = JSON.parse(localStorage.getItem('user'));
//         return user ? user.authHeader : "";
//     }

//     getBooks() {
//         return axios.get(API_URL + '/books', {headers: {authorization:  'Basic ' + this.authHeader()}});
//     }

//     getBookById(id) {
//         return axios.get(API_URL + '/books/' + id, {headers: {authorization: 'Basic ' + this.authHeader()}});
//     }

//     addBook(book) {
//         return axios.post(API_URL + '/books', book, {headers: {authorization: 'Basic ' + this.authHeader()}});
//     }

//     /** TODO: Add method to call the Update Book api 
//      * Implement method updateBook(book, id) that returns the response of HTTP PUT request
//      * to update a book
//     */

//     deleteBook(bookId) {
//         return axios.delete(API_URL + '/books/' + bookId, {headers: {authorization: 'Basic ' + this.authHeader()}});
//     } 

//     //TODO: Add methods to call the Book Review CRUD APIs

//     getUsers() {
//         return axios.get(API_URL + '/users', {headers: {authorization: 'Basic ' + this.authHeader()}});
//     }

//     addUser(newUser) {
//         return axios.post(API_URL + '/users', newUser, {headers: {authorization: 'Basic ' + this.authHeader()}});
//     }

}

export default new ProductService()