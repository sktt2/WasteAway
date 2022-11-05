import axios from "axios"
import StorageHelper from "./StorageHelper"
import { API_URL, header } from "./AxiosConfig"

class AuthService {
    signin(username, password) {
        return axios.post(
            API_URL + "signin",
            {
                username,
                password,
            },
            header
        )
    }

    logout() {
        StorageHelper.removeUser()
        return axios.post(API_URL + "signout", {}, header)
    }

    register(username, name, email, password, address, phoneNumber) {
        return (
            axios.post(API_URL + "signup", {
                username,
                email,
                password,
                name,
                address,
                phoneNumber,
            }),
            header
        )
    }

    async changePassword(body) {
        return axios.post(API_URL + "changepassword", body, header)
    }
}

export default new AuthService()
