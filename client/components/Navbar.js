import Link from "next/link";
import React from 'react';

import SideDrawerButton from "./SideDrawerButton";


const Navbar = props => (
    <header className="navbar">
        <nav className="navbar_navigation">
            <div className="navbar_logo">
                <a href="/">VocTrain</a>
            </div>
            <div className="spacer"></div>
            <div className="navbar_navigation_items">
                <ul>
                    <li><a href="/">Login</a></li>
                </ul>
            </div>
            <div>
                <SideDrawerButton />
            </div>
        </nav>
    </header>
);

export default Navbar;