import { Container, Row, Col, Button } from 'react-bootstrap';
import fetch from 'isomorphic-unfetch';
import Link from 'next/link'
function Create(props) {
    return (
        <Container>
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
Create.getInitialProps = async function () {
    const res = await fetch('https://api.tvmaze.com/search/shows?q=batman');
    const data = await res.json();

    console.log(data);

    return {
        shows: data.map(entry => entry.show)
    };
}

export default Create