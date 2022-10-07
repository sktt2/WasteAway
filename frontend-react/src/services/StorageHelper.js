class StorageHelper {
    static getUserId() {
        return JSON.parse(localStorage.getItem("user")).id
    }
    static getUserName() {
        return JSON.parse(localStorage.getItem("user")).username
    }
    static getName() {
        return JSON.parse(localStorage.getItem("user")).userInfo.name
    }
}
export default StorageHelper
