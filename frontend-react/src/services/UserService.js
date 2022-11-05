import axios from "axios"
import { API_URL } from "./AxiosConfig"
const url = API_URL + "users/"
class UserService {
    async getUser(username) {
        return axios.get(url + username)
    }
    async updateUser(body) {
        return axios.put(url + "update/", body)
    }
}

export default new UserService()
