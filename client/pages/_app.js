import React from 'react'

import 'bootstrap/dist/css/bootstrap.min.css';
import '../components/base.css';
import '../components/Navigationbar.css';

export default function MyApp({Component, pageProps}){
  return <Component {...pageProps} />
}
