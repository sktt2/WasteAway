import { Component } from "react";
import AuthService from "../services/AuthService";

class Logout extends Component {

    componentDidMount() {
        AuthService.logout()
        setTimeout(() => { 
            this.props.history.push('/login');
        }, 5000)
    }

    render(){
        return (
            <div>You have logged out</div>
        )
    }
}
export default Logout;