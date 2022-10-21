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

}

export default new ChatService()