import Link from 'next/link'
import counterpart from 'counterpart'
import Translate from 'react-translate-component'
import en from './languages/en'
import de from './languages/de'
import fr from './languages/fr'

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('de', de);
counterpart.registerTranslations('fr', fr);



class Home extends React.Component {
  state = {
    language: 'en'
  }
  
  render() {
    return (
        <div className="container">
          <main>
            
            <h1 className="title">
              Voc-Trainer
            </h1>
            <Translate content="title_discription" component="p" className="description" ></Translate>

            <div className="grid">
              <Link href="/vocabulary">
                <a className="card">
                  <Translate content="title2" component="h3"></Translate>
                  <Translate content="title2_discription" component="p"></Translate>
                </a>
              </Link>

              <Link href="/study_interface">
                <a className="card">
                  <Translate content="title3" component="h3"></Translate>
                  <Translate content="title3_discription" component="p"></Translate>
                </a>
              </Link>

              <Link href="/testing_mode">
                <a className="card">
                  <Translate content="title4" component="h3"></Translate>
                  <Translate content="title4_discription" component="p"></Translate>
                </a>
              </Link>
              <Link href="/export">
                <a className="card">
                  <Translate content="title5" component="h3"></Translate>
                  <Translate content="title5_discription" component="p"></Translate>
                </a>
              </Link>
              <Link href="/share">
              <a className="card">
                <Translate content="share" component="h3"></Translate>
                <Translate content="share_lib" component="p"></Translate>
              </a>
            </Link>
            </div>
          </main>
        </div>
    );
  }
}
export default Home;
