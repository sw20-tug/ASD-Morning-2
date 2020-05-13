import Link from 'next/link';
import { Nav, Navbar } from 'react-bootstrap';


function Navigationbar(props) {
  return(
    <Navbar bg="primary" variant="dark">
      <Nav className="mr-auto" style={{ margin: "0 auto", color: "#eaeaea" }}>
        <div className="navbar_link">
          <Link href="/">Home</Link>
        </div>
        <div className="navbar_link">
          <Link href="/vocabulary">Overview</Link>
        </div>
        <div className="navbar_link">
          <Link href="/study_interface">Study</Link>
        </div>
        <div className="navbar_link">
          <Link href="/testing_mode">Test Mode</Link>
        </div>
      </Nav>
    </Navbar>
  )
}
export default Navigationbar;
