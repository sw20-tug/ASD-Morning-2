import Link from 'next/link'

const Home = () => (
      <div className="container">
        <main>
          <h1 className="title">
            Voc-Trainer
          </h1>

          <p className="description">
            Train your language skills
          </p>

          <div className="grid">
            <Link href="/vocabulary">
              <a className="card">
                <h3>Vocabulary Overview</h3>
                <p>View and Edit your Vocabulary</p>
              </a>
            </Link>

            <Link href="/study_interface">
              <a className="card">
                <h3> Study Interface </h3>
                <p> Start learning your Vocabulary</p>
              </a>
            </Link>

            <Link href="/testing_mode">
              <a className="card">
                <h3>Testing Mod &rarr;</h3>
                <p>Test your current skills! </p>
              </a>
            </Link>

            <a className="card">
              <h3> Next Feature &rarr;</h3>
              <p>
              </p>
            </a>
          </div>
        </main>
      </div>
);

export default Home;
