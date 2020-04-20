import React from 'react';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';

class Navigationbar extends React.Component {

    render() {
        return (
            <Navbar collapseOnSelect expand="sm" bg="dark" variant="dark">
                <Navbar.Brand href="/">VocTrain</Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="nav_left_side">
                        <Nav.Link href="/">Home</Nav.Link>
                        <Nav.Link href="/vocabulary">Overview</Nav.Link>
                        
                        <Nav.Link href="/study_interface">Study</Nav.Link>
                        <Nav.Link href="/testing_mode">Testing</Nav.Link>
                    </Nav>
                    <div className="nav_spacer" />
                    <Nav className="nav_right_side">
                        <Nav.Link eventKey={1} href="#memes">
                            Register
                        </Nav.Link>
                        <Nav.Link eventKey={2} href="#memes">
                            Login
                        </Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    };
}

export default Navigationbar;