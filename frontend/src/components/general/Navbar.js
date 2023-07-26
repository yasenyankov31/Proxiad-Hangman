import React from 'react';
import { Link } from 'react-router-dom';
import { Navbar, Nav } from 'react-bootstrap';

const ParentNavbar = () => {
  return (
    <Navbar bg="dark" variant="dark" expand="lg" className="px-5" fixed="top">
      <Navbar.Brand as={Link} to="/" className="fs-3">Hangman</Navbar.Brand>
      <Navbar.Toggle aria-controls="navbarScroll" />
      <Navbar.Collapse id="navbarScroll">
        <Nav className="mr-auto my-2 my-lg-0" style={{ maxHeight: '100px' }} navbarScroll>
          <Nav.Link as={Link} to="/games/1" className="nav-link">On going games</Nav.Link>
          <Nav.Link as={Link} to="/users/1" className="nav-link">Users</Nav.Link>
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
}

export default ParentNavbar;
