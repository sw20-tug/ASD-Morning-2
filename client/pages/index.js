import React, { Component } from 'react';
import Navigationbar from '../components/Navigationbar';
import Button from 'react-bootstrap/Button';
import Link from 'next/link';

class Index extends React.Component {

  constructor(props) {
    super(props);

    //this.state = {}
  }

  render() {
    return (
      <div className="wrapper">
        <Navigationbar />

        <div className="home_buttons_wrap">
          <Link href="/vocabulary">
            <Button variant="secondary" size="md" block>
              <h3>Vocabulary Overview</h3>
              View and Edit your Vocabulary
            </Button>
          </Link>
          <Link href="/study_interface">
            <Button variant="secondary" size="md" block>
              <h3>Study Interface</h3>
              Start learning your Vocabulary
            </Button>
          </Link>
          <Link href="/testing_mode">
            <Button variant="secondary" size="md" block>
              <h3>Testing Mode</h3>
              Test your current skills!
            </Button>
          </Link>
          <Link href="/">
          <Button variant="secondary" size="md" block>
              <h3>WIP</h3>
              Work in Progress
            </Button>
          </Link>
        </div>
        
        <h1>blah</h1>
        <p>wut</p>

      </div>
    );
  };
}

export default Index;
