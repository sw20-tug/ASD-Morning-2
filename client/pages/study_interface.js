import fetch from 'isomorphic-unfetch';

import { Container, Button, useRouter, Link} from "next/app";
import { DropdownButton, Dropdown } from "react-bootstrap";


class StudyInterface extends React.Component {
    constructor() {
        super();
        this.state = {
            vocabulary: []
        };
    }

async componentDidMount() {
    const data = await fetch('http://localhost:8080/api/vocabulary')
    const json = await data.json()
    this.setState({ vocabulary: json })
}

render() {
    return (
        <main>
            <h1 className="title">
                Study Interface
                </h1>
            <p>
            </p>
            <Container>
                <DropdownButton id="dropdown-basic-button" title="Select your Language">
                    <Dropdown.Item href="/study_interface/DE" >German</Dropdown.Item>
                    <Dropdown.Item href="/study_interface/EN">English</Dropdown.Item>
                    <Dropdown.Item href="/study_interface/FR" >French</Dropdown.Item>
                </DropdownButton>
            </Container>
        </main>
    );
}
}

export default StudyInterface;
