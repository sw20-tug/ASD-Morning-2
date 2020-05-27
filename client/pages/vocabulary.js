import fetch from 'isomorphic-unfetch';
import Link from 'next/link'
import Button from 'react-bootstrap/Button';
import counterpart from 'counterpart'
import Translate from 'react-translate-component'
import en from './languages/en'
import de from './languages/de'
import fr from './languages/fr'

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('de', de);
counterpart.registerTranslations('fr', fr);
counterpart.setLocale('en');


class VocabularyOverview extends React.Component {
    constructor() {
        super();
        this.state = {
            vocabulary: [],
            language: 'en'
        };
    }
    onLangChange = (e) => {
      this.setState({language: e.target.value});
      counterpart.setLocale(e.target.value);
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
                  <select value={this.state.language} onChange={this.onLangChange}>
                    <option value="en" >EN</option>
                    <option value="de" >DE</option>
                    <option value="fr" >FR</option>
                  </select>

                  <Translate content="title2" component="h1"></Translate>
                  <Translate content="title2_discription" component="p"></Translate>

                    <div>
                    <div style={{ width: "100%", padding: "0.75rem"}}>
                        <Link href="/add_vocabulary">
                            <Button variant="outline-primary">+</Button>
                        </Link>
                    </div>

                        <table className="table">
                            <thead>
                            <tr>
                            <th scope="col"> <Translate content="vocabulary"></Translate>
                                <button type="submit" onClick={() => {this.componentDidMount("a")}} className="btn btn-outline-dark filter_buttons" style={{marginLeft: "0.5rem"}} ><Translate content="up"></Translate></button>
                                <button type="submit" onClick={() => {this.componentDidMount("z")}} className="btn btn-outline-dark filter_buttons" style={{marginRight: "4.5rem"}} ><Translate content="down"></Translate></button>
                                </th>
                                
                                <th scope="col"><Translate content="topic" style={{marginRight: "5rem"}}></Translate></th>
                                <th scope="col"><Translate content="translation" style={{marginRight: "3rem"}}></Translate></th>
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
                                                    <Button variant="outline-primary"><Translate content="edit"></Translate></Button>
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
