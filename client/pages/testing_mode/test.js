import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import { Form, Card } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import fetch from 'isomorphic-unfetch';

class RandomTest extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      vocabulary: [],
      given_language: this.props.query.given_language,
      tested_language: this.props.query.tested_language,
      given_lang_short: "",
      tested_lang_short: "",
      repetitions: this.props.query.repetitions,
      test_index: 0,
      current_vocab: "",
      translation_vocab: "",
      result_message: " "
    };

    this.buildTest();
  }

  static async getInitialProps({query}) {
    return {query}
  }

  buildTest() {
    this.setLanguages();

    fetch('http://localhost:8080/api/vocabulary/random')
      .then((response) => response.json())
      .then((data) => { this.state.vocabulary = data; })
      .then(() => { 
        this.setState({ current_vocab: this.state.vocabulary[this.state.test_index].vocabulary.toString() })
      })
      .catch((error) => {
        alert("Oops, I messed up something. ", error);
      });
  }

  setLanguages() {
    if (this.state.given_language == "German") {
      this.state.given_lang_short = "DE";
    } else if (this.state.given_language == "English") {
      this.state.given_lang_short = "EN";
    } else {
      this.state.given_lang_short = "FR";
    }
    if (this.state.tested_language == "German") {
      this.state.test_lang_short = "DE";
    } else if (this.state.tested_language == "English") {
      this.state.test_lang_short = "EN";
    } else {
      this.state.test_lang_short = "FR";
    }
  }

  updateTest() {
    console.log("lol, placeholder so react ain't complainin");
  }

  renderLog() {
    console.log("Render method fired again!");
  }

  render() {
    this.renderLog();
    return(
      <main className="test_main">
        <div className="test_active_headline">
            Test your knowledge!
        </div>
        <div className="test_active_wrapper">
          <div>
            <Card className="test_active_vocabulary_display">
              <Card.Body>{ this.state.current_vocab }</Card.Body>
            </Card>
          </div>

          <div className="test_active_result_message">
            { this.state.result_message }
          </div>
          
          <Form.Group className="test_active_input">
            <InputGroup size="sm" className="mb-3">
              <InputGroup.Prepend>
                <InputGroup.Text id="inputGroup-sizing-sm">
                    { this.state.test_lang_short }
                </InputGroup.Text>
              </InputGroup.Prepend>
              <FormControl type="text" name={ this.state.tested_lang_short } aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
            </InputGroup>
          </Form.Group>
          <div className="test_active_submit_wrapper">
            <Button variant="outline-primary" style={{ marginLeft: "10px", paddingLeft: "0.4rem", paddingRight: "0.4rem", paddingTop: "0.1rem", paddingBottom: "0.1rem" }}> Check </Button>
            <Button variant="outline-primary" style={{ marginLeft: "10px", paddingLeft: "0.4rem", paddingRight: "0.4rem", paddingTop: "0.1rem", paddingBottom: "0.1rem" }}> Continue </Button>
          </div>
        </div>
      </main>
    );
  }
}

export default RandomTest;
