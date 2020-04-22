import React from 'react'
import 'bootstrap/dist/css/bootstrap.min.css';
import App from 'next/app'
import Layout from '../components/Layout'


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