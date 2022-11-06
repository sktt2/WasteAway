import axios from "axios"

const API_URL =
    process.env.REACT_APP_API_URL + ":" + process.env.REACT_APP_API_PORT + "/api/notifications/"

class NotificationService {
    async getNotificationsByUsername(username) {
        return axios.get(API_URL + username)
    }
    async updateNotificationIfRead(notifid) {
        return axios.put(API_URL + "update/" + notifid)
    }
}

export default new NotificationService()
