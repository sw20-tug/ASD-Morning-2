import { Container, Row, Col, Button } from 'react-bootstrap';
import fetch from 'isomorphic-unfetch';
import Link from 'next/link'

function Vocabulary(props) {
    return (
            <Container>
                <main>
                    <h1 className="title">
                        Study Interface
                    </h1>

                    <p className="description">
                        Train your Vocabulary
                    </p>
                </main>
                <ul>
                    {props.shows.map(show => (
                        <li key={show.id}>
                            <Link href="/p/[id]" as={`/p/${show.id}`}>
                                <a>{show.name}</a>
                            </Link>
                        </li>
                    ))}
                </ul>
            </Container>
    );
}
Vocabulary.getInitialProps = async function () {
    const res = await fetch('https://api.tvmaze.com/search/shows?q=batman');
    const data = await res.json();

    console.log(data);

    return {
        shows: data.map(entry => entry.show)
    };
}

export default Vocabulary