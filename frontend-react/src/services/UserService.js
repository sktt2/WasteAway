import axios from "axios"
import { API_URL, header } from "./AxiosConfig"

class UserService {
    async getUser(username) {
        return axios.get(API_URL + username, header)
    }
    async updateUser(body) {
        return axios.put(API_URL + "update/", body, header)
    }
}

export default new UserService()
