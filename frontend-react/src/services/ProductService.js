import axios from "axios"

const API_URL = "http://localhost:8080/api/products/"
const config = {
    headers: { "Content-Type": "application/json" },
    withCredentials: true,
}

class ProductService {
    async getProducts() {
        return axios.get(API_URL)
    }

    async getProduct(id) {
        return axios.get(API_URL + id)
    }

    async addProduct(body) {
        return axios.post(API_URL, body)
    }
    async getProductByOwner(id) {
        return axios.get(API_URL + "user/" + id)
    }
    async updateProductDetail(body) {
        return axios.put(API_URL + "update/", body)
    }
    async removeProduct(id) {
        return axios.delete(API_URL + "remove/" + id)
    }
    async giveProduct(body) {
        return axios.post(API_URL + "give", body)
    }
    async getGAProductByOwner(id) {
        return axios.get(API_URL + "give/" + id)
    }

    async addProductInterest(body) {
        return axios.post(API_URL + "interest", body)
    }
    async removeProductInterest(body) {
        return axios.delete(API_URL + "interest/delete", { data: body })
    }
    async getProductInterestByUser(id) {
        return axios.get(API_URL + "interests/" + id)
    }
    async getProductInterestByProduct(id) {
        return axios.get(API_URL + "product/interests/" + id )
    }

    async getBooleanIfProductGAExist(productId) {
        return axios.get(API_URL + "give?productId=" + productId)
    }
}

export default new ProductService()

