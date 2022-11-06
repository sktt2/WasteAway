import axios from "axios"

const API_URL = "http://localhost:8080/api/chat/"
const config = {
    headers: { "Content-Type": "application/json" },
    withCredentials: true,
}

class ChatService {
    async createChat(body) {
        return axios.post(API_URL, body)
    }

    async getChatByUser(username) {
        return axios.get("http://localhost:8080/api/chat?username=" + username)
    }

    async getChatById(id) {
        return axios.get("http://localhost:8080/api/chat/" + id)
    }

    async getMessagesByChat(id) {
        return axios.get(API_URL + id + "/messages")
    }

    async addMessage(id, body) {
        return axios.post(API_URL + id + "/messages", body)
    }
}

export default new ChatService()