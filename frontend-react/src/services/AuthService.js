import axios from "axios"
import StorageHelper from "./StorageHelper"

const API_URL = process.env.REACT_APP_API_URL + ":" + process.env.REACT_APP_API_PORT + "/api/auth/"

class AuthService {
    signin(username, password) {
        return axios.post(
            API_URL + "signin",
            {
                username,
                password,
            },
            {
                "Access-Control-Allow-Origin": "*",
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

    async changePassword(body) {
        return axios.post(API_URL + "changepassword", body)
    }
}

export default new AuthService()
