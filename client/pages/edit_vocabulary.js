import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import Rating from 'react-rating';
import { Form } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import Link from 'next/link';
import counterpart from 'counterpart'
import Translate from 'react-translate-component'
import en from './languages/en'
import de from './languages/de'
import fr from './languages/fr'

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('de', de);
counterpart.registerTranslations('fr', fr);


class EditVocabulary extends React.Component {
    constructor(props) {
        super(props);

        this.saveVocabulary = this.saveVocabulary.bind(this);
        console.log("URL Query: ", this.props.query);

        this.state = {
            DE_OLD: this.props.query.de,
            EN_OLD: this.props.query.en,
            FR_OLD: this.props.query.fr,
            DE: this.props.query.de,
            EN: this.props.query.en,
            FR: this.props.query.fr,
            initialRating: this.props.query.rating,
            language: 'en'
    };

      this.handleChange = this.handleChange.bind(this);
      this.rate = this.rate.bind(this);
    }

    static getInitialProps({query}) {
        return {query}
    }

    prepareData() {
        console.log(this.state.DE, this.state.EN, this.state.FR)
        if (this.state.DE == "" || this.state.EN == "" || this.state.FR == "") { return null; }
        
        return JSON.stringify({
            current_translations: {
                'DE': this.state.DE_OLD,
                'EN': this.state.EN_OLD,
                'FR': this.state.FR_OLD
            },
            new_translations: {
                'DE': this.state.DE,
                'EN': this.state.EN,
                'FR': this.state.FR
            },
            rating:this.state.initialRating
        })

    }

    async saveVocabulary() {
        var data_body = this.prepareData();
        if (data_body == null) { return };

        var response = await fetch('http://localhost:8080/api/vocabulary', {
            method: "PUT", 
            headers: new Headers({ 'Accept': 'application/json, text/plain',
                'Content-Type': 'application/json;charset=UTF-8' }),
            body: data_body
        });

        console.log("Aight, I got " + response.status + " as status back.")
        if(response.status == 200){
            alert("OK saved!")
        }
        else{
            alert("An error occured!")
        }
    }
    rate(value){
        this.setState({
            initialRating: value
        })
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
                    <Translate content="edit_vocab" component="h2"></Translate>

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

                        <InputGroup  size="sm" className="mb-3">
                           <Rating onChange={this.rate} initialRating={this.state.initialRating} fractions="1"/>
                        </InputGroup>
                    
                    </Form.Group>
                    <hr/>
                    <div className="edit_button_wrap">
                        <Link href="/vocabulary" replace>
                            <Button variant="outline-primary"><Translate content="return"></Translate></Button>
                        </Link>
                        <Button type="button" onClick={ this.saveVocabulary } variant="outline-success"> <Translate content="save_changes"></Translate> </Button>
                    </div>
                </Form.Group>
            </main>
        );
    }
}

export default EditVocabulary;