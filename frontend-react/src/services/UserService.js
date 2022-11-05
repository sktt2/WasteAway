import axios from "axios"
import { API_URL, header } from "./AxiosConfig"
const url = API_URL + "/users/"
class UserService {
    async getUser(username) {
        return axios.get(url + username, header)
    }
    async updateUser(body) {
        return axios.put(url + "update/", body, header)
    }
}

export default new UserService()
