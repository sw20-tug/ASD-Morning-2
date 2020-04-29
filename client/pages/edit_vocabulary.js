import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import Link from 'next/link';

class EditVocabulary extends React.Component {
    constructor(props) {
        super(props);
        console.log("URL Query: ", this.props.query);

    this.state = {
        DE_OLD: this.props.query.de,
        EN_OLD: this.props.query.en,
        FR_OLD: this.props.query.fr,
        DE: this.props.query.de,
        EN: this.props.query.en,
        FR: this.props.query.fr
    };

    this.handleChange = this.handleChange.bind(this);
    }

    static getInitialProps({query}) {
        return {query}
    }

    saveVocabulary() {
        console.log(this.state.DE, this.state.EN, this.state.FR)

        var response = fetch('http://localhost:8080/api/vocabulary', {
            method: "PUT", 
            headers: { 'Accept': 'application/json, text/plain',
                'Content-Type': 'application/json;charset=UTF-8' },
            body: JSON.stringify({
                current_translations: {
                    'DE': this.state.DE_OLD,
                    'EN': this.state.EN_OLD,
                    'FR': this.state.FR_OLD
                },
                new_translations: {
                    'DE': this.state.DE,
                    'EN': this.state.EN,
                    'FR': this.state.FR
                }
            })
        })

        console.log("Aight, I got " + response.status + " as status back.")
        
    }

    handleChange({ target }) {
        this.setState({
            [target.name]: target.value
        });
        console.log("States: ", this.state.DE, this.state.EN, this.state.FR)
    }

    render() {
        return(
            <main className="edit_main">
                <Form.Group className="edit_container">
                    <h2>
                        Edit Vocabulary
                    </h2>
                    <Form.Group className="edit_input_wrapper">
                        <InputGroup size="sm" className="mb-3">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="inputGroup-sizing-sm">DE</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl type="text" name="DE" onChange={ this.handleChange } defaultValue={this.props.query.de} aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
                        </InputGroup>
                        <InputGroup size="sm" className="mb-3">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="inputGroup-sizing-sm">EN</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl type="text" name="EN" onChange={ this.handleChange } defaultValue={this.props.query.en} aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
                        </InputGroup>
                        <InputGroup  size="sm" className="mb-3">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="inputGroup-sizing-sm">FR</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl type="text" name="FR" onChange={ this.handleChange } defaultValue={this.props.query.fr} aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
                        </InputGroup>
                    </Form.Group>
                    <hr/>
                    <div className="edit_button_wrap">
                        <Link href="/vocabulary" replace>
                            <Button variant="outline-primary">Return</Button>
                        </Link>
                        <Button type="submit" onClick={ () => {this.saveVocabulary()} } variant="outline-success">Save Changes</Button>
                    </div>
                </Form.Group>
            </main>
        );
    }
}

export default EditVocabulary;
