import Link from 'next/link';
import { Nav, Navbar } from 'react-bootstrap';
import counterpart from 'counterpart'
import Translate from 'react-translate-component'
import en from '../pages/languages/en'
import de from '../pages/languages/de'
import fr from '../pages/languages/fr'

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('de', de);
counterpart.registerTranslations('fr', fr);
counterpart.setLocale('en');


class Navigationbar extends React.Component {
  state = {
    language: 'en'
  }
  onLangChange = (e) => {
    this.setState({language: e.target.value});
    counterpart.setLocale(e.target.value);
  }

  render()  {
    return(
      <Navbar bg="primary" variant="dark">
        <Nav className="mr-auto" style={{ margin: "0 auto", color: "#eaeaea" }}>
          
          <div className="navbar_link">
            <Link href="/">
              <a>
                <Translate content="home" component="p"></Translate>
              </a>
            </Link>
          </div>

          <div className="navbar_link">
            <Link href="/vocabulary">
              <a>
                <Translate content="overview" component="p"></Translate>
              </a>
            </Link>
          </div>

          <div className="navbar_link">
            <Link href="/study_interface">
              <a>
                <Translate content="study" component="p"></Translate>
              </a>
            </Link>
          </div>

          <div className="navbar_link">
            <Link href="/testing_mode">
              <a>
                <Translate content="test_mode" component="p"></Translate>
              </a>
            </Link>
          </div>

          <select value={this.state.language} onChange={this.onLangChange}>
            <option value="en" >EN</option>
            <option value="de" >DE</option>
            <option value="fr" >FR</option>
          </select>
        </Nav>
      </Navbar>
    )
  }
}
export default Navigationbar;
