import axios from "axios"
import StorageHelper from "./StorageHelper"

const API_URL = "http://localhost:8080/api/auth/"

class AuthService {
    signin(username, password) {
        return axios.post(
            API_URL + "signin",
            {
                username,
                password,
            },
            {
                withCredentials: true,
            }
        )
    }

    logout() {
        StorageHelper.removeUser()
        return axios.post(API_URL + "signout", {})
    }

    register(username, name, email, password, address, phoneNumber) {
        return axios.post(API_URL + "signup", {
            username,
            email,
            password,
            name,
            address,
            phoneNumber,
        })
    }
}

export default new AuthService()
