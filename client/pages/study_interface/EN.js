import fetch from 'isomorphic-unfetch';

import { Container, Button, useRouter, Link, List } from "next/app";

import counterpart from 'counterpart'
import Translate from 'react-translate-component'
import en from '../study_interface/EN'
import de from '../study_interface/DE'
import fr from '../study_interface/FR'

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('de', de);
counterpart.registerTranslations('fr', fr);
counterpart.setLocale('en');

class StudyInterface extends React.Component {
    /* constructor(props) {
         super(props);
     }
 */
    constructor() {
        super();
        this.state = {
            vocabulary: [],
            language: 'en'
        };
    }

    async componentDidMount(args = "b") {
        if(args == "b")
        {
            const data = await fetch('http://localhost:8080/api/vocabulary')
            const json = await data.json()
            this.setState({vocabulary: json})            
        }
        else if(args == "a" || args == "z")
        {
            const data = await fetch('http://localhost:8080/api/vocabulary/alphabetically/EN/' + args)
            const json = await data.json()
            this.setState({vocabulary: json})
        }
    }

    render() {
        console.log(this.props.vocabs);

        return (

            <main>
              <Translate content="title3" component="h1"></Translate>

              <Translate content="title3_discription" component="p"></Translate>

              <Translate content="current_language" component="p"></Translate>
              
              <Container>
                  <table className="table">
                      <thead>
                          <tr>
                              <th scope="col"> <Translate content="vocabulary"></Translate>
                                  <button type="submit" onClick={() => {this.componentDidMount("a")}} className="btn btn-outline-dark filter_buttons" style={{marginLeft: "0.5rem"}} >▲</button>
                                  <button type="submit" onClick={() => {this.componentDidMount("z")}} className="btn btn-outline-dark filter_buttons" >▼</button>
                              </th>
                              <th scope="col"><Translate content="translation"></Translate></th>
                          </tr>
                      </thead>
                      <tbody>
                          {
                              this.state.vocabulary.map((element, id) => (
                                  <tr key={id}>
                                      <td>{element.translations.EN}</td>
                                      <td>
                                          <Container>
                                              DE:<div className="overlay" id={id}>
                                              {element.translations.DE}
                                                  </div> 
                                                  <br/>
                                              FR:<div className="overlay" id={id}>
                                                  {element.translations.FR}
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
