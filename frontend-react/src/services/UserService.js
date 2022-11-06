import axios from "axios"

const API_URL = process.env.REACT_APP_API_URL + ":" + process.env.REACT_APP_API_PORT + "/api/users/"

class UserService {
    async getUser(username) {
        return axios.get(API_URL + username)
    }
    async updateUser(body) {
        return axios.put(API_URL + "update/", body)
    }
}

export default new UserService()
