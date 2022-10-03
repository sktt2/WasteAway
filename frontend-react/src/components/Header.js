import { Component } from "react";
import { Navbar, Nav } from "react-bootstrap";

import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Button from "react-bootstrap/Button";
import AuthService from "../services/AuthService";


// Import CSS styling
import styles from "../features/ComponentStyle.module.css";


// Import CSS styling
import styles from "../features/ComponentStyle.module.css";


class Header extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isLoggedIn: false,
        };
    }

    componentDidMount() {
        if (localStorage.hasOwnProperty("user")) {
            this.setState({ isLoggedIn: true });
        }
    }

    render() {
        var isLoggedIn = this.state.isLoggedIn;
        var nav;
        if (isLoggedIn) {
            nav = (
                <>
                    <Nav.Link href="/profile">Profile</Nav.Link>
                    <Nav.Link href="/product">Products</Nav.Link>
                    <Nav.Link href="/addproduct">Add Product</Nav.Link>
                    <Nav.Link
                        href="/logout"
                        onClick={() => {
                            AuthService.logout();
                        }}>
                        Logout
                    </Nav.Link>
                </>
            );
        } else {
            nav = (
                <>
                    <Nav.Link href="/login">Login</Nav.Link>
                </>
            );
        }
        return (
            <Navbar className={styles.navbar} expand="lg">
                <Container>
                    <Navbar.Brand href="/">Waste Away</Navbar.Brand>
                    <Navbar.Toggle aria-controls="navbarScroll" />
                    <Navbar.Collapse className="me-auto justify-content-end" id="navbarScroll">
                        <Form className="d-flex">
                            <Form.Control type="search" placeholder="Search" className="me-2" aria-label="Search" />
                            {/* <Button variant="outline-success">Search</Button> */}
                        </Form>
                        <Nav className="mr-auto my-2 my-lg-0" style={{ maxHeight: "100px" }} navbarScroll>
                            {nav}
                        </Nav>
                    </Navbar.Collapse>
                </Container>
            </Navbar>
        );
    }
}

export default Header;
