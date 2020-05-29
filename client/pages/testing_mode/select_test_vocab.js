import fetch from 'isomorphic-unfetch';
import { Container } from "next/app";
import Link from 'next/link'
import Button from 'react-bootstrap/Button';
import { Form, FormControl } from 'react-bootstrap';
import Router from 'next/router'

class SelectVocabulary extends React.Component {
    constructor(props) {
        super(props);

        console.log("URL Query: ", this.props.query);
        this.addSelectedVocabulary = this.addSelectedVocabulary.bind(this)
        this.handleChange_Topic = this.handleChange_Topic.bind(this);
        this.passValues = this.passValues.bind(this)
        this.state = {
            vocabulary: [],
            selectedVocabulary: [],
            given_language: this.props.query.given_language,
            tested_language: this.props.query.tested_language,
            repetitions: this.props.query.repetitions,
            button_state: false
        };
    }

    static async getInitialProps({ query }) {
        return { query }
    }

    async componentDidMount(args = "b") {
        if (args == "b") {
            const data = await fetch('http://localhost:8080/api/vocabulary')
            const json = await data.json()
            this.setState({ vocabulary: json })
            console.log(this.state.vocabulary)
        }
        else if (args == "a" || args == "z") {
            this.state.selectedVocabulary.forEach(el => {
                document.getElementById(el).checked = false
            })
            this.setState({ selectedVocabulary: [] });
            const data = await fetch('http://localhost:8080/api/vocabulary/alphabetically/' + args)
            const json = await data.json()
            this.setState({ vocabulary: json })
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

    passValues() {
        var values = { vocabs: [] }
        this.state.selectedVocabulary.forEach(el => {
            values.vocabs.push(this.state.vocabulary[el])
        })
        console.log(values);

        Router.push({
            pathname: '/testing_mode/test',
            query: {
                given_language: this.state.given_language,
                tested_language: this.state.tested_language,
                repetitions: this.state.repetitions,
                vocabs: JSON.stringify(values),
                random: false
            }
        })
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
            }
            const json = await response.json()
            this.setState({vocabulary: json})
        }

    }


    render() {
        return (
            <main>
                <h1 className="title">
                    Test your knowledge
                    </h1>
                <p className="description">
                    Select the words you want to test!
                    </p>
                <Container>
                    <div style={{width: "100%", marginBottom: "1em", marginLeft: "1em"}}>
                    <button type="submit" onClick={() => { this.componentDidMount("a") }} class="btn btn-outline-dark filter_buttons"  >▲</button>
                    <button type="submit" onClick={() => { this.componentDidMount("z") }} class="btn btn-outline-dark filter_buttons" style={{marginLeft: "5px"}} >▼</button>
                    </div>
                    <table className="table">
                        <thead>
                            <tr>
                                <th scope="col">
                                    <span  style={{width:"100%",float:"right", fontSize: "25px"}}>{this.state.given_language}</span>
                                    
                                </th>
                                <th scope="col">
                                    <Form.Group style={{ width: "90" }} controlId="select_language" onChange={this.handleChange_Topic}>
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
                                <th scope="col" style={{fontSize:"25px"}} >{this.state.tested_language}</th>
                                <th className="test_col" scope="col"></th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.vocabulary.map((element, id) => (
                                    <tr key={id}>
                                        {
                                            this.state.given_language == "English" &&
                                            <td>{element.translations.EN}</td>
                                        }
                                        {
                                            this.state.given_language == "German" &&
                                            <td>{element.translations.DE}</td>
                                        }
                                          {
                                            this.state.given_language == "French" &&
                                            <td>{element.translations.FR}</td>
                                        }
                                        
                                        <td>{element.topic}</td>
                                    
                                        {
                                            this.state.tested_language == "English" &&
                                            <td>{element.translations.EN}</td>
                                        }
                                        {
                                            this.state.tested_language == "German" &&
                                            <td>{element.translations.DE}</td>
                                        }
                                          {
                                            this.state.tested_language == "French" &&
                                            <td>{element.translations.FR}</td>
                                        }
                                            
                                        
                                        <td>
                                            {console.log(element.translations)}
                                            <Form.Group controlId={id}>
                                                <Form.Check type="checkbox" label="Check me out" onChange={this.addSelectedVocabulary} />
                                            </Form.Group>
                                        </td>
                                    </tr>
                                )
                                )
                            }
                        </tbody>
                    </table>
                    

                </Container>
                <div style={{width: "100%",background: "#ffffffab",textAlign: "center",padding: "1em", position: "fixed", bottom: "0"}}>
                        <Button onClick={this.passValues}>
                            Start training
                        </Button>
                    </div>

            </main>
        );
    }
}

export default SelectVocabulary;
