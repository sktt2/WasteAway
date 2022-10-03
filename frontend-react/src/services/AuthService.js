import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

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
    );
  }

  logout() {
    localStorage.removeItem("user");
    localStorage.removeItem("user_id");
  }

  register(username, name, email, password, address, phoneNumber) {
    return axios.post(API_URL + "signup", {
      username,
      email,
      password,
      name,
      address,
      phoneNumber,
    });
  }
}

export default new AuthService();
