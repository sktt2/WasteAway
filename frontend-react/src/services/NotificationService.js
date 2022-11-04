import axios from "axios"

const API_URL = "http://localhost:8080/api/notifications/"
const config = {
    headers: { "Content-Type": "application/json" },
    withCredentials: true,
}

class NotificationService {
    async getNotificationsByUsername(username) {
        return axios.get(API_URL + username)
    }
}

export default new NotificationService()