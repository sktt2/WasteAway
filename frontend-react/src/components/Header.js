import { Component } from "react";
import { Navbar, Nav } from 'react-bootstrap';

class Header extends Component {
    constructor(props) {
        super(props);
        this.state = {
          isLoggedIn: false
        };
      }
    componentDidMount() {
        if (localStorage.hasOwnProperty('user')) {
            this.setState({isLoggedIn: true});
        } else {
            this.setState({isLoggedIn: false});
        }
    }
    render(){
        var isLoggedIn = this.state.isLoggedIn;
        var nav;
        if (isLoggedIn) {
            nav = <Nav.Link href='/logout'>Logout</Nav.Link>;
        } else {
            nav = <Nav.Link href='/login'>Login</Nav.Link>
        }
        return (
            <Navbar bg="light" expand="lg">
                <Navbar.Brand href="/">Waste Away</Navbar.Brand>
                <Navbar.Toggle aria-controls="navbarScroll" />
                <Navbar.Collapse className="me-auto" id="navbarScroll">
                    <Nav
                        className="mr-auto my-2 my-lg-0"
                        style={{ maxHeight: '100px' }}
                        navbarScroll
                    >
                    <Nav.Link href='/product'>Products</Nav.Link>
                    <Nav.Link href='/addproduct'>Add Product</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
                {nav}
            </Navbar>
        )
    }
}

export default Header;