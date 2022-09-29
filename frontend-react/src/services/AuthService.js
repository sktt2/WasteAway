import axios from "axios";

const API_URL = "http://localhost:8080/api/auth/";

class AuthService {
  signin(username, password) {
    return axios
      .post(
        API_URL + "signin",
        {
          username,
          password,
        },
        {
          withCredentials: true,
        }
      )
      .then((data) => {
        console.log(data.data);
        localStorage.setItem("user_id", data.data.id);
        console.log(localStorage);
      });
  }
  logout() {
    localStorage.removeItem("user");
  }

  register(username, email, password, name, address, phoneNumber) {
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
