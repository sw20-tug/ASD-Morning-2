import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import { Form, Row } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import Link from 'next/link';


class RandomTest extends React.Component {
    constructor(props) {
        super(props);
        console.log("URL Query: ", this.props.query);

        this.state = {
            given_language: this.props.query.given_language,
            tested_language: this.props.query.tested_language,
            repetitions: this.props.query.repetitions
        };
        console.log("strings: ", this.props.query.given_language, this.props.query.tested_language)
    }

    async componentDidMount() {
        const data = await fetch('http://localhost:8080/api/vocabulary/random')
        const json = await data.json()
        this.setState({ vocabulary: json })
    }

        static getInitialProps({query}) {
            return {query}
        }

        render() {
            return(
                <main className="edit_main">
                    <h2>
                        Test your knowledge!
                        <Row></Row>
                        In progress!!
                    </h2>
                    </main>
            );
        }

}

export default RandomTest;