import Head from 'next/head';
import Navigationbar from './Navigationbar';

function Layout(props) {
  return (
    <div className="page-Layout">
        <Navigationbar/>
        <title>Voc-Trainer</title>
        <link rel="icon" href="/favicon.ico"/>
      {props.children}
    </div>
  )
}
export default Layout;
