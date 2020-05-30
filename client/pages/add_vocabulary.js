import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import Rating from 'react-rating';
import { Form, Dropdown } from 'react-bootstrap';
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
counterpart.setLocale('en');


class AddVocabulary extends React.Component {
    constructor() {
        super();
        this.sendData = this.sendData.bind(this);
        this.state = {
            vocabulary: '',
            translationsDE: '',
            translationsEN: '',
            translationsFR: '',
            tmpLanguage: '',
            topic_array: [],
            Topic: '',
            language: 'en'
        };
    };

    onLangChange = (e) => {
      this.setState({language: e.target.value});
      counterpart.setLocale(e.target.value);
    }

    async componentDidMount() {
        const data = await fetch('http://localhost:8080/api/vocabulary/topics')
        const json = await data.json()
        this.setState({topic_array: json})
        console.log(this.state.topic_array)            
    }
    async sendData() {
        var body = this.prepareData();
        if(body == -1)
            return;
        console.log(body)
        var result = await fetch('http://localhost:8080/api/vocabulary', {
            method: 'POST',
            headers: new Headers({'content-type': 'application/json'}),
            body: body
        });
        if(result.status == 200){
            alert("OK saved!")
        }
        else{
            alert("An error occured!")
        }
    }
    prepareData(){
        if(this.state.translationsDE == '' || this.state.translationsEN=='' || this.state.translationsFR=='')
        {
            alert('Pleas provied the necessary input!')
            return -1;
        }
        else{
            console.log(this.state.tmpLanguage);
            if(this.state.tmpLanguage == 'DE')
            {
                this.state.vocabulary = this.state.translationsDE;
            }
            if(this.state.tmpLanguage == 'FR')
            {
                this.state.vocabulary = this.state.translationsFR;
            }
            if(this.state.tmpLanguage == 'EN')
            {
                this.state.vocabulary = this.state.translationsEN;
            } 
            return JSON.stringify({
                vocabulary: this.state.vocabulary,
                topic: this.state.Topic,
                translations: {
                    'DE' : this.state.translationsDE,
                    'EN' : this.state.translationsEN,
                    'FR' : this.state.translationsFR
                }
            })
        }
        
    }
    handleChange_DE = (e) => {
        this.setState({translationsDE: e.target.value});
    }
    handleChange_EN = (e) => {
        this.setState({translationsEN: e.target.value});
    }
    handleChange_FR = (e) => {
        this.setState({translationsFR: e.target.value});
    }
    handleChange_Language= (e) => {
        this.setState({tmpLanguage: e.target.value});
    }
    handleChange_Topic= (e) => {
        this.setState({Topic: e.target.value});
    }

 
    render() {
        return(
            <main className="edit_main">
                <select value={this.state.language} onChange={this.onLangChange}>
                  <option value="en" >EN</option>
                  <option value="de" >DE</option>
                  <option value="fr" >FR</option>
                </select>
                <Form.Group className="edit_container">
                    <Translate content="add_vocab" component="h2"></Translate>
                    <Form.Group style={{ width: 220 }} controlId="select_language" onChange={this.handleChange_Language}>
                        <select variant="primary"  name="translation_language">
                            <option value="sel_lang"><Translate content="select_key_lang"></Translate> </option>
                            <option value="DE">German</option>
                            <option value="EN">English</option>
                            <option value="FR">French</option>
                        </select>
                    </Form.Group>

                    <Form.Group className="edit_input_wrapper">
                        <InputGroup size="sm" className="mb-3">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="inputGroup-sizing-sm">DE</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl type="text" name="DE" onChange={ this.handleChange_DE }aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
                        </InputGroup>
                        <InputGroup size="sm" className="mb-3">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="inputGroup-sizing-sm">EN</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl type="text" name="EN" onChange={ this.handleChange_EN } aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
                        </InputGroup>
                        <InputGroup  size="sm" className="mb-3">
                            <InputGroup.Prepend>
                                <InputGroup.Text id="inputGroup-sizing-sm">FR</InputGroup.Text>
                            </InputGroup.Prepend>
                            <FormControl type="text" name="FR" onChange={ this.handleChange_FR } aria-label="Small" aria-describedby="inputGroup-sizing-sm" />
                        </InputGroup>
                    </Form.Group>

                    <Form.Group style={{ width: 220 }} controlId="select_language" onChange={this.handleChange_Topic}>
                        <select variant="primary"  name="translation_language">
                            <option value="Default">Default</option>
                            <option value="USER_GENERATED">USER_GENERATED</option>
                            <option value="Sport">Sport</option>
                            <option value="Home">Home</option>
                            <option value="Food">Food</option>
                            <option value="Human">Human</option>
                            <option value="Electronic">Electronic</option>
                        </select>
                    </Form.Group>

                    <hr/>
                    <div className="edit_button_wrap">
                        <Link href="/vocabulary" replace>
                            <Button variant="primary"><Translate content="return"></Translate> </Button>
                        </Link>
                        <Button type="button" onClick={ this.sendData } variant="primary" disabled = {!this.state.translationsDE || 
                            !this.state.translationsEN ||!this.state.translationsFR || !this.state.tmpLanguage || !this.state.Topic}><Translate content="add_new_vocab"></Translate> </Button>
                    </div>
                </Form.Group>
            </main>
        );
    }
}

export default AddVocabulary;