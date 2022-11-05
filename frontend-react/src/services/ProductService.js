import axios from "axios"
import { API_URL, header } from "./AxiosConfig"

class ProductService {
    async getProducts() {
        return axios.get(API_URL, header)
    }

    async getProduct(id) {
        return axios.get(API_URL + id, header)
    }

    async addProduct(body) {
        return axios.post(API_URL, body, header)
    }
    async getProductByOwner(id) {
        return axios.get(API_URL + "user/" + id, header)
    }
    async updateProductDetail(body) {
        return axios.put(API_URL + "update/", body, header)
    }
    async removeProduct(id) {
        return axios.delete(API_URL + "remove/" + id, header)
    }
    async giveProduct(body) {
        return axios.post(API_URL + "give", body, header)
    }
    async getGAProductByOwner(id) {
        return axios.get(API_URL + "give/" + id, header)
    }
}

export default new ProductService()
