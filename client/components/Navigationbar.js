import Link from 'next/link';
import { Nav, Navbar, NavDropdown } from 'react-bootstrap';


function Navigationbar(props) {
  return(
    <Navbar bg="primary" variant="dark">
      <Nav className="mr-auto" style={{ margin: "0 auto", color: "#eaeaea" }}>
        <div className="navbar_link">
          <Link href="/"><a>Home</a></Link>
        </div>
        <div className="navbar_link">
          <Link href="/vocabulary"><a>Overview</a></Link>
        </div>
        <div className="navbar_link">
          <Link href="/study_interface"><a>Study</a></Link>
        </div>
        <div className="navbar_link">
          <Link href="/testing_mode"><a>Test Mode</a></Link>
        </div>
        <div className="navbar_link">
          <Link href="/share"><a>Share</a></Link>
        </div>
      </Nav>
    </Navbar>
  )
}
export default Navigationbar;
