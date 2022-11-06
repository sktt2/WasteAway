import axios from "axios"
import { API_URL } from "./AxiosConfig"
const url = API_URL + "products/"
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
    async addProductInterest(body) {
        return axios.post(url + "interest", body)
    }
    async removeProductInterest(body) {
        return axios.delete(url + "interest/delete", { data: body })
    }
    async getProductInterestByUser(id) {
        return axios.get(url + "interests/" + id)
    }
    async getProductInterestByProduct(id) {
        return axios.get(url + "product/interests/" + id)
    }

    async getBooleanIfProductGAExist(productId) {
        return axios.get(url + "give?productId=" + productId)
    }
    async getRecommendation(id) {
        return axios.get(url + "recommendation/" + id)
    }
    async updateRecommendation(body) {
        return axios.put(url + "recommendation/update", body)
    }
    async getProductsByUserRecommendation(id) {
        return axios.get(url + "product/recommendation/" + id)
    }
}

export default new ProductService()
