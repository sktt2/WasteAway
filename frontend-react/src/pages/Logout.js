import { Component } from "react";
import AuthService from "../services/AuthService";

class Logout extends Component {

    componentDidMount() {
        AuthService.logout()
        console.log(localStorage)
    }

    render(){
        return (
            <div>You have logged out</div>
        )
    }
}
export default Logout;