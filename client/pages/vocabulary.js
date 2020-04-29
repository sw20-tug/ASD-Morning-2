import fetch from 'isomorphic-unfetch';
import {Container} from "next/app";


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
                    <Container>
                        <table className="table">
                            <thead>
                            <tr>
                                <th scope="col">Vocabulary</th>
                                <th scope="col">Topic</th>
                                <th scope="col">Translations</th>
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
                                                    <li key={i}>{trans[0]}: {trans[1]}</li>
                                                ))}</ul>
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
