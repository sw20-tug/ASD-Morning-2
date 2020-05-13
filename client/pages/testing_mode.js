import {Button, Row, Col, Dropdown, DropdownButton, Form} from 'react-bootstrap';
import { Container } from "next/app";
import Link from 'next/link';


class TestingMode extends React.Component {
  constructor() {
    super();
    this.handleChange_Given_Language = this.handleChange_Given_Language.bind(this);
    this.handleChange_Tested_Language = this.handleChange_Tested_Language.bind(this);
    this.handleChange_Repetitions = this.handleChange_Repetitions.bind(this);
    this.state = {
      given_language: 'Tested Language...',
      tested_language: 'Translate to...',
      repetitions: '1',
      dis_given: true,
      dis_tested: true,
      disabled: true
    };
  }


  handleChange_Given_Language({ target }) {
      this.setState({
          given_language: target.textContent
      });
      this.state.dis_given = false
      console.log("given_language: ", this.state.given_language);
      if (target.textContent == this.state.tested_language)
      {
          this.state.disabled = true;
      }
      if((this.state.dis_tested === false) && (target.textContent !== this.state.tested_language))
      {
          this.state.disabled = false
      }
  }
  handleChange_Tested_Language({ target }) {
      this.setState({
          tested_language: target.textContent
      });
      this.state.dis_tested = false
      console.log("tested_language: ", this.state.tested_language);
      if (target.textContent == this.state.given_language)
      {
          this.state.disabled = true;
      }
      if ((this.state.dis_given === false)  && (this.state.given_language !== target.textContent))
      {
          this.state.disabled = false
      }
  }
  handleChange_Repetitions({ target }) {
      this.setState({
          repetitions: target.value
      });
  }

  render() {
    return (
      <main>
        <div className="testing_headline">Choose the language you want to test...</div>
        <div className="testing_lang_choice_container">
          <div className="testing_lang_choice_dropdown_item">
            <DropdownButton id="dropdown-basic-button" title={this.state.given_language}>
              <Dropdown.Item value="DE" onClick={this.handleChange_Given_Language}>German</Dropdown.Item>
              <Dropdown.Item value="EN" onClick={this.handleChange_Given_Language}>English</Dropdown.Item>
              <Dropdown.Item value="FR" onClick={this.handleChange_Given_Language}>French</Dropdown.Item>
            </DropdownButton>
          </div>
          <div className="testing_lang_choice_dropdown_item">
            <DropdownButton id="dropdown-basic-button" title={this.state.tested_language}>
              <Dropdown.Item value="DE" onClick={this.handleChange_Tested_Language}>German</Dropdown.Item>
              <Dropdown.Item value="EN" onClick={this.handleChange_Tested_Language}>English</Dropdown.Item>
              <Dropdown.Item value="FR" onClick={this.handleChange_Tested_Language}>French</Dropdown.Item>
            </DropdownButton>
          </div>
        </div>

        <div className="testing_repetitions_choice_container">
          <Form>
            <Form.Group controlId="formBasicEmail">
              <Form.Label>Enter the number of times you want to repeat your wrong guesses:</Form.Label>
              <Form.Control type="number" placeholder="1" onChange={ this.handleChange_Repetitions } style={{ maxWidth: "4rem", margin: "0 auto" }}/>
            </Form.Group>
          </Form>
          <Link href={{ pathname: '/testing_mode/test',
              query: {
                given_language: this.state.given_language,
                tested_language: this.state.tested_language,
                repetitions: this.state.repetitions }}}>
            <Button variant="outline-primary" disabled={this.state.disabled}>Let's start!</Button>
          </Link>
        </div>
        
      </main>
    );
  }
}

export default TestingMode;

/*
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


  handleChange_Given_Language({ target }) {
      this.setState({
          given_language: target.textContent
      });
      this.state.dis_given = false
      console.log("given_language: ", this.state.given_language);
      if (target.textContent == this.state.tested_language)
      {
          this.state.disabled = true;
      }
      if((this.state.dis_tested === false) && (target.textContent !== this.state.tested_language))
      {
          this.state.disabled = false
      }
  }
  handleChange_Tested_Language({ target }) {
      this.setState({
          tested_language: target.textContent
      });
      this.state.dis_tested = false
      console.log("tested_language: ", this.state.tested_language);
      if (target.textContent == this.state.given_language)
      {
          this.state.disabled = true;
      }
      if ((this.state.dis_given === false)  && (this.state.given_language !== target.textContent))
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
*/
