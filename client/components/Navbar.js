import Link from 'next/link';
import {Container} from "next/app";

function Navbar(props) {
    return(
        <Container>
            <ul>
                <li>
                    <Link href="/">
                        <a>
                            Home
                        </a>
                    </Link>
                </li>
                <li>
                    <Link href="/vocabulary">
                        <a>
                            Vocabulary Overview
                        </a>
                    </Link>
                </li>
                <li>
                    <Link href="/study_interface">
                        <a>
                            Study Interface
                        </a>
                    </Link>
                </li>
                <li>
                    <Link href="/testing_mode">
                        <a>
                            Testing Mode
                        </a>
                    </Link>
                </li>
            </ul>
            <style jsx>{`
                ul {
                    list-style: none;
                    display: flex;
                    flex-direction: row;
                    justify-content: center;
                    align-items: center;
                }  
                ul li {
                    margin-right: 20px;
                }
            `}</style>
        </Container>

    )
}
export default Navbar;