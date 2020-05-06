import {Button, Row, Col, Dropdown, DropdownButton, Form} from 'react-bootstrap';
import { Container } from "next/app";
import Link from 'next/link'


class TestingMode extends React.Component {
    constructor() {
        super();
        this.handleChange_Given_Language = this.handleChange_Given_Language.bind(this);
        this.handleChange_Tested_Language = this.handleChange_Tested_Language.bind(this);
        this.handleChange_Repetitions = this.handleChange_Repetitions.bind(this);
        this.state = {
            given_language: 'Select the given language',
            tested_language: 'Select the tested language',
            repetitions: '1',
            dis_given: true,
            dis_tested: true,
            disabled: true
        };
    }

    /*async componentDidMount() {
        const data = await fetch('http://localhost:8080/api/vocabulary')
        //const data = await fetch('http://localhost:8080/api/vocabulary/random')
        const json = await data.json()
        this.setState({ vocabulary: json })
    }*/
    handleChange_Given_Language({ target }) {
        this.setState({
            given_language: target.textContent
        });
        this.state.dis_given = false
        if(this.state.dis_tested === false)
        {
            this.state.disabled = false
        }
    }
    handleChange_Tested_Language({ target }) {
        this.setState({
            tested_language: target.textContent
        });
        this.state.dis_tested = false
        if (this.state.dis_given === false)
        {
            this.state.disabled = false
        }
    }
    handleChange_Repetitions({ target }) {
        this.setState({
            repetitions: target.value
        })

    }


    
    render() {
        return (
            <main>
                
                <h1 style={{ marginBottom: "3%", marginTop: "10%" }}>Choose the language you want to study</h1>
                <Row style={{ marginBottom: "2%" }}>
                <Container>
                    <Col>
                    <DropdownButton id="dropdown-basic-button" title={this.state.given_language}>
                    <Dropdown.Item value="DE" onClick={this.handleChange_Given_Language}>German</Dropdown.Item>
                    <Dropdown.Item value="EN" onClick={this.handleChange_Given_Language}>English</Dropdown.Item>
                    <Dropdown.Item value="FR" onClick={this.handleChange_Given_Language}>French</Dropdown.Item>
                </DropdownButton>
                </Col>
                <Col>
                <DropdownButton id="dropdown-basic-button" title={this.state.tested_language}>
                    <Dropdown.Item value="DE" onClick={this.handleChange_Tested_Language}>German</Dropdown.Item>
                    <Dropdown.Item value="EN" onClick={this.handleChange_Tested_Language}>English</Dropdown.Item>
                    <Dropdown.Item value="FR" onClick={this.handleChange_Tested_Language}>French</Dropdown.Item>
                </DropdownButton>
                </Col>
            </Container>
                </Row>
                <Row>
                    <Form>
                        <Form.Group controlId="formBasicEmail">
                            <Form.Label>Enter the number of repetitions you want to repeat your wrong guesses</Form.Label>
                            <Form.Control type="number" placeholder="1" onChange={ this.handleChange_Repetitions } />
                        </Form.Group>
                    </Form>
                </Row>
                <Row>
                    <Col></Col>
                    <div style={{ width: "100%", padding: "0.75rem", marginLeft: "2rem" }}>
                    <Col><Link href={{ pathname: '/testing_mode/test', query: { 
                                                    given_language: this.state.given_language, 
                                                    tested_language: this.state.tested_language, 
                                                    repetitions: this.state.repetitions }}}>
                                                    <Button variant="outline-primary" disabled={this.state.disabled} width="100%">Let's start!</Button>
                                                </Link></Col>
                    </div>
                    <Col></Col>
                </Row>
            </main>
        );
    }
}

    export default TestingMode;