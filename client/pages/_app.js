import React from 'react'

import 'bootstrap/dist/css/bootstrap.min.css';
import '../components/base.css';
import '../components/Navbar.css';
import '../components/SideDrawerButton.css';
import '../components/SideDrawer.css';

export default function MyApp({Component, pageProps}){
  return <Component {...pageProps} />
}
