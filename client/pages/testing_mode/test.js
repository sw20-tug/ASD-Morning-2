import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import { Form, Row } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import Link from 'next/link';
import {Container} from "next/app";



// Test wird 1x abgearbeitet
// Alle falschen Vokabel werden 'repetitions'-oft wiederholt
// Immer 1 Vokabel auf der Seite darstellen mit Text Feld für Eingabe darunter
// Test-Vokabel Liste Länge definiert Seiten Anzahl
// Mit Button nach vor und zurück im Test gehen
// Leeres Textfeld bei nach vor gilt als falsche Eingabe und Vokabel wird als falsch gewertet

class RandomTest extends React.Component {
    constructor(props) {
        super(props);
        console.log("URL Query: ", this.props.query);

        this.state = {
            vocabulary: [],
            given_list: [],
            testing_list: [],
            validation_list: [],
            given_language: this.props.query.given_language,
            tested_language: this.props.query.tested_language,
            repetitions: this.props.query.repetitions,
            given_lang_short: "",
            tested_lang_short: ""
        };
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
        
        console.log("URL Query: ", this.props.query);
        console.log("Shorts: ", this.state.given_lang_short);
    }

    /*parseVocabJSON() {
        this.state.vocabulary.map((element, id) => {
            this.state.given_list.add(element.vocabulary);
            if (this.state.given_lang_short == "DE") {
                this.state.testing_list.add(element.translations.DE)
            } else if (this.state.given_lang_short == "EN") {
                this.state.testing_list.add(element.translations.EN)
            } else {
                this.state.testing_list.add(element.translations.FR)
            }
            
        });

    }*/

    async componentDidMount() {
        const data = await fetch('http://localhost:8080/api/vocabulary/random')
        const json = await data.json()
        this.setState({ vocabulary: json })
        //this.parseVocabJSON();

        
        console.log("this_state: ", this.state);
        console.log("Vocabs: ", this.state.vocabulary);
    }

        static getInitialProps({query}) {
            return {query}
        }
        
        render() {
            //const inputLanguageAbbreviation = this.state.tested_lang_short;
            return(
                <main className="test_main">
                    <h1 style={{ fontSize: "2rem" }}>
                        Test your knowledge!
                    </h1>
                    <Container>
                        <div className="testing_vocab_display_wrapper" style={{ margin: "0 auto", width: "100%", textAlign: "center" }}>
                            <div style={{ textAlign: "center", padding: "2rem 0.3rem", fontSize: "2rem", display: "inline-block" }}> 
                                Haus 
                            </div>
                        </div>
                        
                        <Form.Group className="edit_input_wrapper">
                            <InputGroup size="sm" className="mb-3">
                                <InputGroup.Prepend>
                                        <InputGroup.Text id="inputGroup-sizing-sm"> 
                                            { this.state.test_lang_short } 
                                        </InputGroup.Text>
                                        
                                </InputGroup.Prepend>
                                
                                <FormControl type="text" name={ this.state.test_lang_short } aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
                                <Button variant="outline-primary" style={{ marginLeft: "10px", paddingLeft: "0.4rem", paddingRight: "0.4rem", paddingTop: "0.1rem", paddingBottom: "0.1rem" }}> Check </Button>
                                <Button variant="outline-primary" style={{ marginLeft: "10px", paddingLeft: "0.4rem", paddingRight: "0.4rem", paddingTop: "0.1rem", paddingBottom: "0.1rem" }}> Next </Button>
                            </InputGroup>
                        </Form.Group>
                    </Container>
                    </main>
            );
        }

}

export default RandomTest;