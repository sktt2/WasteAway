import axios from "axios"
import { API_URL, header } from "./AxiosConfig"
const url = API_URL + "/products/"
class ProductService {
    async getProducts() {
        return axios.get(url, header)
    }

    async getProduct(id) {
        return axios.get(url + id, header)
    }

    async addProduct(body) {
        return axios.post(url, body, header)
    }
    async getProductByOwner(id) {
        return axios.get(url + "user/" + id, header)
    }
    async updateProductDetail(body) {
        return axios.put(url + "update/", body, header)
    }
    async removeProduct(id) {
        return axios.delete(url + "remove/" + id, header)
    }
    async giveProduct(body) {
        return axios.post(url + "give", body, header)
    }
    async getGAProductByOwner(id) {
        return axios.get(url + "give/" + id, header)
    }
}

export default new ProductService()
