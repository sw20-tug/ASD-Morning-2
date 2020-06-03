import fetch from 'isomorphic-unfetch';
import { DropdownButton, Dropdown } from "react-bootstrap";
import counterpart from 'counterpart'
import Translate from 'react-translate-component'
import en from './languages/en'
import de from './languages/de'
import fr from './languages/fr'

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('de', de);
counterpart.registerTranslations('fr', fr);

class StudyInterface extends React.Component {
    constructor() {
        super();
        this.state = {
            vocabulary: [],
            language: 'en'
        };
    }

async componentDidMount() {
    const data = await fetch('http://localhost:8080/api/vocabulary')
    const json = await data.json()
    this.setState({ vocabulary: json })
}

render() {
    return (
        <main>

          <Translate content="title3" component="h1"></Translate>
            <p>
            </p>
            <div>
                <DropdownButton id="dropdown-basic-button" title={<Translate content="select_lang"></Translate>}>
                    <Dropdown.Item href="/study_interface/DE" >German</Dropdown.Item>
                    <Dropdown.Item href="/study_interface/EN">English</Dropdown.Item>
                    <Dropdown.Item href="/study_interface/FR" >French</Dropdown.Item>
                </DropdownButton>
            </div>
        </main>
    );
}
}

export default StudyInterface;
