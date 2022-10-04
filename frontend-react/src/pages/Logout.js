import { Component } from "react";

class Logout extends Component {
    componentDidMount() {
        setTimeout(() => {
            this.props.history.push("/login");
        }, 5000);
    }

    render() {
        return <div>You have logged out. You will be redirected to the home page shortly after.</div>;
    }
}
export default Logout;
