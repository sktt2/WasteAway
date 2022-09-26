import { Component } from "react";
import { Navbar, Nav } from 'react-bootstrap';

class Header extends Component {

    render(){
        return (
            <Navbar bg="light" expand="lg">
                <Navbar.Brand href="/">Waste Away</Navbar.Brand>
                <Navbar.Toggle aria-controls="navbarScroll" />
                <Navbar.Collapse id="navbarScroll">
                    <Nav
                        className="mr-auto my-2 my-lg-0"
                        style={{ maxHeight: '100px' }}
                        navbarScroll
                    >
                    <Nav.Link href='/list-users'>Users</Nav.Link>
                    <Nav.Link href='/login'>Login</Nav.Link>
                    <Nav.Link href='/logout'>Logout</Nav.Link>
                    <Nav.Link href='/product'>Product</Nav.Link>
                    <Nav.Link href='/addproduct'>Add Product</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        )
    }
}

export default Header;