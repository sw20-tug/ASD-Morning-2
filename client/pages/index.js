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
                <p>View, edit, and add to your vocabulary!</p>
              </a>
            </Link>

            <Link href="/study_interface">
              <a className="card">
                <h3> Study Interface </h3>
                <p>Start learning all the vocabulary!</p>
              </a>
            </Link>

            <Link href="/testing_mode">
              <a className="card">
      	        <h3>Testing Mode</h3>
                <p>Test your current skills! </p>
              </a>
            </Link>
            <Link href="/export">
              <a className="card">
                <h3>Import/Export</h3>
                <p>Import/Export your vocabularies</p>
              </a>
            </Link>
            <Link href="/share">
              <a className="card">
                <h3>Share</h3>
                <p>Share your library</p>
              </a>
            </Link>
          </div>
        </main>
      </div>
);

export default Home;
