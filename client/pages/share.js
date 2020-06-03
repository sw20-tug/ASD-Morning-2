import fetch from 'isomorphic-unfetch';
import {Container} from "next/app";
import Link from 'next/link'
import Button from 'react-bootstrap/Button';
import InputGroup from 'react-bootstrap/InputGroup';
import Rating from 'react-rating';
import { Form, FormControl } from 'react-bootstrap';
import counterpart from 'counterpart'
import Translate from 'react-translate-component'
import en from './languages/en'
import de from './languages/de'
import fr from './languages/fr'

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('de', de);
counterpart.registerTranslations('fr', fr);

class VocabularyOverview extends React.Component {
    constructor() {
        super();
        this.addSelectedVocabulary = this.addSelectedVocabulary.bind(this);
        this.handleChange_Topic = this.handleChange_Topic.bind(this);
        this.passValues = this.passValues.bind(this);
        this.selectAll = this.selectAll.bind(this);
        this.unselectAll = this.unselectAll.bind(this);
        this.handleChangeEmail= this.handleChangeEmail.bind(this);
        this.prepareData = this.prepareData.bind(this);
        
        this.state = {
            vocabulary: [],
            selectedVocabulary: [],
            email: '',
            buttonState: false,
            language: 'en'
        };
    }

    async componentDidMount(args = "b") {
        document.getElementById("selectbox").value = "Default"
        this.state.selectedVocabulary.forEach(el => {
            document.getElementById(el).checked = false
        })
        this.state.selectedVocabulary = [];
        if(args == "b")
        {
            const data = await fetch('http://localhost:8080/api/vocabulary')
            if (data.status != 200) 
            return
            const json = await data.json()
            this.setState({vocabulary: json}) 
            console.log(this.state.vocabulary)           
        }
        else if(args == "a" || args == "z")
        {
            const data = await fetch('http://localhost:8080/api/vocabulary/alphabetically/' + args)
            console.log(data);
            const json = await data.json()
            this.setState({vocabulary: json})
        }
        else if(args == "c" || args == "d")
        {
            const data = await fetch('http://localhost:8080/api/vocabulary/rating/' + args)
            console.log(data);
            const json = await data.json()
            this.setState({vocabulary: json})
        }
    }

    addSelectedVocabulary(e) {
        let index = e.target.id;
        let isThere = this.state.selectedVocabulary.indexOf(index)
        console.log(isThere)
        if (isThere == -1) {
            let val = this.state.selectedVocabulary;
            val.push(index)
            this.setState({ selectedVocabulary: val });
        }
        else {
            this.state.selectedVocabulary.splice(isThere);
        }
        console.log(this.state.selectedVocabulary)
    }

    prepareData(){
        let vocabs = [];
        this.state.selectedVocabulary.forEach(el => {
            vocabs.push(this.state.vocabulary[el].vocabulary)
        })
        console.log(vocabs);
        console.log(this.state.email);

        return JSON.stringify({
            vocabs: vocabs,
            email: this.state.email
        })
        }

    async passValues() {
        if(this.state.selectedVocabulary.length <= 0)
        {
            alert("Please select at least one element!");
        }
        var body = this.prepareData();
        if(body == -1)
            return;
        console.log(body);
        var result = await fetch('http://localhost:8080/api/vocabulary/share', {
            method: 'POST',
            headers: new Headers({'content-type': 'application/json'}),
            body: body
        });
        if(result.status == 200){
            alert("Successfully sent!")
        }
        else{
            alert("An error occured!")
        }
    }

    async handleChange_Topic(e) {
        this.state.selectedVocabulary.forEach(el => {
            document.getElementById(el).checked = false
        })
        this.state.selectedVocabulary = [];
        if(e.target.value == "Default")
        {
            const data = await fetch('http://localhost:8080/api/vocabulary')
            console.log(data);
            const json = await data.json()
            this.setState({vocabulary: json})
        }
        else
        {
            
            const response = await fetch('http://localhost:8080/api/vocabulary/sort_topics/' + e.target.value)
            if(response.status != 200)
            {
                this.setState({vocabulary: []})
                return;
            }
            const json = await response.json()
            this.setState({vocabulary: json}) 
        }

    }

    selectAll() {
        let isThere;
        let val;
        this.state.selectedVocabulary = [];
        this.state.vocabulary.map((el, id) => (
            document.getElementById(id).checked = true,
            isThere = this.state.selectedVocabulary.indexOf(id),
            val = this.state.selectedVocabulary,
            val.push(id),
            this.setState({ selectedVocabulary: val })
        ))

    }

    unselectAll() {
        this.state.selectedVocabulary = [];
        this.state.vocabulary.map((el, id) => (
            document.getElementById(id).checked = false
        ))
    }

    handleChangeEmail(e){
        e.preventDefault();
        this.state.buttonState = false;
        if (/^[a-zA-Z0-9]+@[a-zA-Z0-9]+\.[A-Za-z][A-Za-z]+$/.test(e.target.value))
        {
            this.setState({email: e.target.value});
            console.log(this.state.email);
            this.state.buttonState = true;
        }
    }

    render() {
        return (
                <main>
                    <Translate content="share" component="h1"></Translate>
                    <Translate content="select_words" component="p" ></Translate>
                    
                    <Container>
                        <Form>
                        <Form.Group controlId="formBasicEmail">
                            <Translate component="input" type="email" attributes={{ placeholder: 'enter_email'}} onChange={ this.handleChangeEmail }></Translate>
                            <Form.Text className="text-muted">
                              <Translate content="dont_share_email"> </Translate>
                            </Form.Text>
                        </Form.Group>
                        <Button onClick={this.passValues} disabled = {!this.state.buttonState} variant="primary">
                          <Translate content="send"></Translate>
                        </Button>
                        </Form>
                    </Container>
                    <Container>
                        <table className="table">
                            <thead>
                            <tr>
                                <th scope="col">
                                    <Translate content="vocabulary" ></Translate>
                                    <button type="submit" onClick={() => {this.componentDidMount("a")}} className="btn btn-outline-dark filter_buttons"  >▲</button>
                                    <button type="submit" onClick={() => {this.componentDidMount("z")}} className="btn btn-outline-dark filter_buttons" >▼</button>
                                </th>
                                <th scope="col">
                                    <Form.Group style={{ width: 220 }} controlId="select_language" onChange={this.handleChange_Topic}>
                                        <select id="selectbox" variant="primary"  name="translation_language">
                                            <option value="Default">All Topics</option>
                                            <option value="USER_GENERATED">USER_GENERATED</option>
                                            <option value="Sport">Sport</option>
                                            <option value="Home">Home</option>
                                            <option value="Food">Food</option>
                                            <option value="Human">Human</option>
                                            <option value="Electronic">Electronic</option>
                                        </select>
                                    </Form.Group>
                                </th>
                                <th scope="col"><Translate content="translation" ></Translate></th>
                                <th scope="col"><Translate content="rating" ></Translate>
                                    <button type="submit" onClick={() => {this.componentDidMount("c")}} className="btn btn-outline-dark filter_buttons"  >▲</button>
                                    <button type="submit" onClick={() => {this.componentDidMount("d")}} className="btn btn-outline-dark filter_buttons" >▼</button>
                                </th>
                                <th>
                                    <Button onClick={this.selectAll}>
                                        <Translate content="select_all"></Translate>
                                    </Button>
                                    <Button onClick={this.unselectAll} variant = "outline-primary">
                                      <Translate content="unselect_all"></Translate>
                                    </Button>
                                </th>
                                <th className="test_col" scope="col"></th>
                            </tr>
                            </thead>
                            <tbody>
                            {
                                this.state.vocabulary.map((element, id) => (
                                        <tr key={id}>
                                            <td>{element.vocabulary}</td>
                                            <td>{element.topic}</td>
                                            <td>
                                                <ul>{Object.entries(element.translations).map((trans, i) => (
                                                    <li key={i}>{trans[0]}: {trans[1]} </li>
                                                ))}
                                                </ul>
                                            </td>
                                            <td>                                                
                                            <InputGroup  size="sm" className="mb-3">
                                                <Rating readonly initialRating={element.rating} fractions="1"/>
                                            </InputGroup>
                                            </td>
                                            <td>
                                              <Form.Group controlId={id}>
                                                  <Form.Check type="checkbox" label="Select" onChange={this.addSelectedVocabulary} />
                                              </Form.Group>
                                            </td>
                                        </tr>
                                    )
                                )
                            }
                            </tbody>
                        </table>
                    </Container>

                </main>
        );
    }
}

export default VocabularyOverview;
