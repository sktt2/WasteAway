class AxiosConfig {
    API_URL = process.env.REACT_APP_API_URL + ":" + process.env.REACT_APP_API_PORT + "/api/auth/"
    header = {
        "Access-Control-Allow-Origin": "*",
    }
}
export default AxiosConfig
