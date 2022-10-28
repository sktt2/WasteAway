import axios from "axios"

const API_URL = "http://localhost:8080/api/users/"

class UserService {
    async getUser(username) {
        return axios.get(API_URL + username)
    }
    async updateUser(body) {
        return axios.put(API_URL + "update/", body)
    }
}

export default new UserService()
