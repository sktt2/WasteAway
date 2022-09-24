import { Component } from "react";

class Error extends Component {    
    render() {
        return (
            <div>
                <br></br>
                <h3>Error: {this.props.location?.state?.errorMessage ?? "unauthorised access"}</h3>
            </div>
        )
    }
}

export default Error;