class StorageHelper {
    static setUser(user) {
        sessionStorage.setItem("user", user)
    }
    static getUser() {
        return JSON.parse(sessionStorage.getItem("user"))
    }
    static getUserId() {
        return JSON.parse(sessionStorage.getItem("user")).id
    }
    static getUsername() {
        return JSON.parse(sessionStorage.getItem("user")).username
    }
    static getName() {
        return JSON.parse(sessionStorage.getItem("user")).userInfo.name
    }
    static getFirstTime() {
        return JSON.parse(localStorage.getItem("user")).firstTime
    }
    static removeUser() {
        sessionStorage.removeItem("user")
    }
}
export default StorageHelper
