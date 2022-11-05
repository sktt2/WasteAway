import axios from "axios"
import { API_URL } from "./AxiosConfig"
const url = API_URL + "/products/"
class ProductService {
    async getProducts() {
        return axios.get(url)
    }

    async getProduct(id) {
        return axios.get(url + id)
    }

    async addProduct(body) {
        return axios.post(url, body)
    }
    async getProductByOwner(id) {
        return axios.get(url + "user/" + id)
    }
    async updateProductDetail(body) {
        return axios.put(url + "update/", body)
    }
    async removeProduct(id) {
        return axios.delete(url + "remove/" + id)
    }
    async giveProduct(body) {
        return axios.post(url + "give", body)
    }
    async getGAProductByOwner(id) {
        return axios.get(url + "give/" + id)
    }
}

export default new ProductService()
