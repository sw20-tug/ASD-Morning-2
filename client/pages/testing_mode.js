import {Button, Row, Col, Dropdown, DropdownButton, Form} from 'react-bootstrap';
import Link from 'next/link';

import counterpart from 'counterpart'
import Translate from 'react-translate-component'
import en from './languages/en'
import de from './languages/de'
import fr from './languages/fr'

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('de', de);
counterpart.registerTranslations('fr', fr);
counterpart.setLocale('en');

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
      random: false,
      dis_given: true,
      dis_tested: true,
      disabled: true,
<<<<<<< HEAD
      language: 'en'
=======
      disabled_continue: true,
      continue_button_class: "testing_start_buttons_continue_hidden"
>>>>>>> 50c127063aa41cd0975f81a7650b7893aae8ad7b
    };

    this.checkForSavedTest();
  }

  checkForSavedTest() {
    fetch("http://localhost:8080/api/testing_mode/check")
      .then((response) => response.json())
      .then((response) => this.setState({ disabled_continue: !response }))
      .catch((error) => console.log(error))
  }

  onLangChange = (e) => {
    this.setState({language: e.target.value});
    counterpart.setLocale(e.target.value);
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
    if (this.state.disabled_continue) {
      this.state.continue_button_class = "testing_start_buttons_continue_hidden";
    }
    else {
      this.state.continue_button_class = "testing_start_buttons_continue";
    }

    return (
      <main>
        <select value={this.state.language} onChange={this.onLangChange}>
          <option value="en" >EN</option>
          <option value="de" >DE</option>
          <option value="fr" >FR</option>
        </select>

        <Translate content="choose_testing_lang" className="testing_headline"></Translate>
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
              <Form.Label><Translate content="enter_number"></Translate></Form.Label>
              <Form.Control type="number" placeholder="1" onChange={ this.handleChange_Repetitions } style={{ maxWidth: "4rem", margin: "0 auto" }}/>
            </Form.Group>
          </Form>
        </div>

        <div className="testing_start_buttons">
          <Link href={{ pathname: '/testing_mode/test',
              query: {
                given_language: this.state.given_language,
                tested_language: this.state.tested_language,
                repetitions: this.state.repetitions, 
                random: true }}}>
<<<<<<< HEAD
            <Button variant="outline-primary" disabled={this.state.disabled}><Translate content="random"></Translate></Button>
=======
            <Button variant="outline-primary" className="testing_start_buttons_item" disabled={this.state.disabled}>Random</Button>
>>>>>>> 50c127063aa41cd0975f81a7650b7893aae8ad7b
          </Link>
          <Row></Row>
          <Link href={{ pathname: '/testing_mode/select_test_vocab',
              query: {
                given_language: this.state.given_language,
                tested_language: this.state.tested_language,
                repetitions: this.state.repetitions, 
                random: false }}}>
<<<<<<< HEAD
            <Button variant="outline-primary" disabled={this.state.disabled}><Translate content="select_vocab"></Translate></Button>
=======
            <Button variant="outline-primary" className="testing_start_buttons_item" disabled={this.state.disabled}>Select vocabulary</Button>
          </Link>
        </div>
        <div className="testing_start_buttons_continue">
        <Link href={{ pathname: '/testing_mode/test', query: { continue: true }}}>
            <Button variant="outline-primary" className={ this.state.continue_button_class } disabled={ this.state.disabled_continue }>Continue Last Test</Button>
>>>>>>> 50c127063aa41cd0975f81a7650b7893aae8ad7b
          </Link>
        </div>
      </main>
    );
  }
}

export default TestingMode;