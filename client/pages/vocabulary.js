import fetch from 'isomorphic-unfetch';
import Link from 'next/link'
import Button from 'react-bootstrap/Button';


class VocabularyOverview extends React.Component {
    constructor() {
        super();
        this.state = {
            vocabulary: []
        };
    }

    async componentDidMount(args = "b") {
        if(args == "b")
        {
            const data = await fetch('http://localhost:8080/api/vocabulary')
            const json = await data.json()
            this.setState({vocabulary: json}) 
            console.log(this.state.vocabulary)           
        }
        else if(args == "a" || args == "z")
        {
            const data = await fetch('http://localhost:8080/api/vocabulary/alphabetically/' + args)
            const json = await data.json()
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


                    <div>
                    <div style={{ width: "100%", padding: "0.75rem"}}>
                        <Link href="/add_vocabulary">
                            <Button variant="outline-primary">+</Button>
                        </Link>
                    </div>

                        <table className="table">
                            <thead>
                            <tr>
                            <th scope="col">Vocabulary
                                <button type="submit" onClick={() => {this.componentDidMount("a")}} className="btn btn-outline-dark filter_buttons" style={{marginLeft: "0.5rem"}} >Up</button>
                                <button type="submit" onClick={() => {this.componentDidMount("z")}} className="btn btn-outline-dark filter_buttons" >Down</button>
                                </th>
                                
                                <th scope="col">Topic</th>
                                <th scope="col">Translations</th>
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
                    </div>

                </main>
        );
    }
}

export default VocabularyOverview;
