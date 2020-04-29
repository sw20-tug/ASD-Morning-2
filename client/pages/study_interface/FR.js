import fetch from 'isomorphic-unfetch';

import { Container, Button, useRouter, Link, List } from "next/app";
import { DropdownButton, Dropdown } from "react-bootstrap";

class StudyInterface extends React.Component {
    /* constructor(props) {
         super(props);
     }
 */
    constructor() {
        super();
        this.state = {
            vocabulary: []
        };
    }

    async componentDidMount() {
        const data = await fetch('http://localhost:8080/api/vocabulary')
        const json = await data.json()
        this.setState({ vocabulary: json })
    }

    render() {
        console.log(this.props.vocabs);

        return (

            <main>
                <h1 className="title">
                    Study Interface
                </h1>
                <p className="description">
                    Test your knowlegde about the French language!
                </p>
                <p className="description">
                    Current Language: French
                </p>
                <Container>
                    <table className="table">
                        <thead>
                            <tr>
                                <th scope="col">Vocabulary</th>
                                <th scope="col">Translations</th>
                            </tr>
                        </thead>
                        <tbody>
                            {
                                this.state.vocabulary.map((element, id) => (
                                    <tr key={id}>
                                        <td>{element.translations.FR}</td>
                                        <td>
                                            <Container>
                                                DE:<div className="overlay" id={id}>
                                                {element.translations.DE}
                                                    </div> 
                                                    <br/>
                                                EN:<div className="overlay" id={id}>
                                                    {element.translations.EN}
                                                    </div>
                                            </Container>
                                            
                                            
                                        </td>
                                    </tr>
                                )
                                )
                            }
                        </tbody>
                    </table>

                </Container>
            </main >
        );
    }
}
/*
StudyInterface.getInitialProps = async function () {
    const res = await fetch('http://localhost:8080/api/vocabulary')
    const data = await res.json()
    return {
        vocabs: data.vocabulary
    }
}*/

export default StudyInterface;
