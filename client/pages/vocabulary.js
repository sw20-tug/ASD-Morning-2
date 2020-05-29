import fetch from 'isomorphic-unfetch';
import {Container} from "next/app";
import Link from 'next/link'
import Button from 'react-bootstrap/Button';
import InputGroup from 'react-bootstrap/InputGroup';
import Rating from 'react-rating';
import { Form, FormControl } from 'react-bootstrap';


class VocabularyOverview extends React.Component {
    constructor() {
        super();
        this.handleChange_Topic = this.handleChange_Topic.bind(this);
        this.state = {
            vocabulary: []
        };
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
                    <h1 className="title">
                        Vocabulary Overview
                    </h1>
                    <p className="description">
                        View and Edit your Vocabulary
                    </p>

                    <div style={{ width: "100%", padding: "0.75rem", marginLeft: "2rem" }}>
                        <Link href="/add_vocabulary">
                            <Button variant="outline-primary">+</Button>
                        </Link>
                    </div>

                    <Container>
                        <table className="table">
                            <thead>
                            <tr>
                                <th scope="col">Vocabulary
                                    <button type="submit" onClick={() => {this.componentDidMount("a")}} class="btn btn-outline-dark filter_buttons"  >▲</button>
                                    <button type="submit" onClick={() => {this.componentDidMount("z")}} class="btn btn-outline-dark filter_buttons" >▼</button>
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
                                <th scope="col">Translations</th>
                                <th scope="col">Rating
                                    <button type="submit" onClick={() => {this.componentDidMount("c")}} class="btn btn-outline-dark filter_buttons"  >▲</button>
                                    <button type="submit" onClick={() => {this.componentDidMount("d")}} class="btn btn-outline-dark filter_buttons" >▼</button>
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
                                                    <Button variant="outline-primary">Edit</Button>
                                                </Link>
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
