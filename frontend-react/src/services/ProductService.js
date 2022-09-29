import axios from 'axios';

const API_URL = "http://localhost:8080/api"; 
// const config = { headers: {'Content-Type' : 'application/json'}, withCredentials: true }

class ProductService {
    async getProducts() {
        return axios.get(API_URL + '/products')
    }

    async getProduct(id) {
        return axios.get(API_URL + '/products/' + id)
    }
}

export default new ProductService()