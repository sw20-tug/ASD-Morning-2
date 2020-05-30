import fetch from 'isomorphic-unfetch';
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
counterpart.setLocale('en');



class VocabularyOverview extends React.Component {
    constructor() {
        super();
        this.handleChange_Topic = this.handleChange_Topic.bind(this);
        this.state = {
            vocabulary: [],
            language: 'en'
        };
    }
    onLangChange = (e) => {
      this.setState({language: e.target.value});
      counterpart.setLocale(e.target.value);
    }

    async componentDidMount(args = "b") {
        document.getElementById("selectbox").value = "Default"
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

    async handleChange_Topic(e) {
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
            }
            const json = await response.json()
            this.setState({vocabulary: json}) 
        }

    }

    render() {
        return (
                <main>
                  <select value={this.state.language} onChange={this.onLangChange}>
                    <option value="en" >EN</option>
                    <option value="de" >DE</option>
                    <option value="fr" >FR</option>
                  </select>

                  <Translate content="title2" component="h1"></Translate>
                  <Translate content="title2_discription" component="p"></Translate>

                    <div>
                    <div style={{ width: "100%", padding: "0.75rem"}}>
                        <Link href="/add_vocabulary">
                            <Button variant="outline-primary">+</Button>
                        </Link>
                    </div>

                        <table className="table">
                            <thead>
                            <tr>
                                <th scope="col"> <Translate content="vocabulary" ></Translate>
                                    <button type="submit" onClick={() => {this.componentDidMount("a")}} className="btn btn-outline-dark filter_buttons" style={{marginLeft: "0.5rem"}} >▲</button>
                                    <button type="submit" onClick={() => {this.componentDidMount("z")}} className="btn btn-outline-dark filter_buttons" >▼</button>
                                </th>
                                <th scope="col">
                                    <Form.Group style={{ width: 220 }} controlId="select_language" onChange={this.handleChange_Topic}>
                                        <select id="selectbox" variant="primary"  name="translation_language">
                                            <option value="Default" > <Translate content="all_topics" ></Translate> </option>
                                            <option value="USER_GENERATED"> <Translate content="topic_user_generated" ></Translate></option>
                                            <option value="Sport"> <Translate content="topic_sport" ></Translate> </option>
                                            <option value="Home"> <Translate content="topic_home" ></Translate> </option>
                                            <option value="Food"> <Translate content="topic_food" ></Translate> </option>
                                            <option value="Human"> <Translate content="topic_human" ></Translate> </option>
                                            <option value="Electronic"> <Translate content="topic_electronic" ></Translate> </option>
                                        </select>
                                    </Form.Group>
                                </th>
                                <th scope="col"><Translate content="translation" ></Translate></th>
                                <th scope="col"><Translate content="rating" ></Translate>
                                    <button type="submit" onClick={() => {this.componentDidMount("c")}} className="btn btn-outline-dark filter_buttons"  >▲</button>
                                    <button type="submit" onClick={() => {this.componentDidMount("d")}} className="btn btn-outline-dark filter_buttons" >▼</button>
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
                                            <td>                                                <InputGroup  size="sm" className="mb-3">
                           <Rating readonly initialRating={element.rating} fractions="1"/>
                        </InputGroup></td>
                                            <td>
                                                {console.log(element.translations)}
                                                <Link href={{ pathname: 'edit_vocabulary', query: {
                                                    de: element.translations.DE, 
                                                    en: element.translations.EN, 
                                                    fr: element.translations.FR,
                                                    rating: element.rating}}}>
                                                    <Button variant="outline-primary"><Translate content="edit"></Translate></Button>
                                                </Link>
                                            </td>
                                        </tr>
                                    )
                                )
                            }
                            </tbody>
                        </table>
                    </div>

                </main>
        );
    }
}

export default VocabularyOverview;
