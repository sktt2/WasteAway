class StorageHelper {
    static setUser(user) {
        localStorage.setItem("user", user)
    }
    static getUser() {
        return JSON.parse(localStorage.getItem("user"))
    }
    static getUserId() {
        return JSON.parse(localStorage.getItem("user")).id
    }
    static getUsername() {
        return JSON.parse(localStorage.getItem("user")).username
    }
    static getName() {
        return JSON.parse(localStorage.getItem("user")).userInfo.name
    }
    static removeUser() {
        localStorage.removeItem("user")
    }
}
export default StorageHelper
