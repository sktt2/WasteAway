import { Component } from "react";

class Logout extends Component {
    componentDidMount() {
        localStorage.removeItem('user');
        this.props.history.push('/');
    }
    render(){
        return (
            <div>You have logged out</div>
        )
    }
}
export default Logout;