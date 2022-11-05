import axios from "axios"
import StorageHelper from "./StorageHelper"
import { API_URL, header } from "./AxiosConfig"

const url = API_URL + "auth/"
class AuthService {
    signin(username, password) {
        return axios.post(
            url + "signin",
            {
                username,
                password,
            },
            { ...header }
        )
    }

    logout() {
        StorageHelper.removeUser()
        return axios.post(url + "signout", {})
    }

    register(username, name, email, password, address, phoneNumber) {
        return axios.post(url + "signup", {
            username,
            email,
            password,
            name,
            address,
            phoneNumber,
        })
    }

    async changePassword(body) {
        return axios.post(url + "changepassword", body)
    }
}

export default new AuthService()
