import fetch from 'isomorphic-unfetch';
import {Container} from "next/app";
import Link from 'next/link'
import Button from 'react-bootstrap/Button';
import { Form, FormControl } from 'react-bootstrap';


class VocabularyOverview extends React.Component {
    constructor() {
        super();
        this.state = {
            vocabulary: []
        };
    }

    async componentDidMount() {
        const data = await fetch('http://localhost:8080/api/vocabulary')
        const json = await data.json()
        this.setState({vocabulary: json})
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
                                <th scope="col">Vocabulary</th>
                                <th scope="col">Topic</th>
                                <th scope="col">Translations</th>
                                <th className="test_col" scope="col">test</th>
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
