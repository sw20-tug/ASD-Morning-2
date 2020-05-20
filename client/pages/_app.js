import React from 'react';
import App from 'next/app';
import Layout from '../components/Layout';
import 'bootstrap/dist/css/bootstrap.min.css';
import '../components/layout.css';
import '../components/edit.css';
import '../components/testing.css';

class MyApp extends App {
  render() {
    const { Component, pageProps } = this.props

    return (
      <Layout>
        <Component {...pageProps} />
      </Layout>
    )
  }
}

export default MyApp