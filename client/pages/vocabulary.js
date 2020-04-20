import { Container, Row, Col, Button } from 'react-bootstrap';
import Navigationbar from '../components/Navigationbar';
import fetch from 'isomorphic-unfetch';
import Link from 'next/link'
import Head from 'next/head'

class VocabularyOverview extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div className="wrapper">
                <Head>
                <title>Vocabulary</title>
                <link rel="icon" href="/favicon.ico" />
                </Head>
                <Navigationbar />
      
                <ul>
                    {this.props.shows.map(show => (
                        <li key={show.id}>
                            <Link href="/p/[id]" as={`/p/${show.id}`}>
                                <a>{show.name}</a>
                            </Link>
                        </li>
                    ))}
                </ul>
              
                <h1>blah</h1>
                <p>wut</p>
      
            </div>
        );
    }
}

VocabularyOverview.getInitialProps = async function () {
    const res = await fetch('https://api.tvmaze.com/search/shows?q=batman');
    const data = await res.json();

    console.log(data);

    return {
        shows: data.map(entry => entry.show)
    };
}

export default VocabularyOverview;
