import React from 'react';

const SideDrawer = props => (
    <div className="backdrop">
        <nav className="sidedrawer">
            <ul>
                <li><a href="/">Home</a></li>
                <li><a href="/vocabulary">Vocabulary View</a></li>
                <li><a href="/study_interface">Study Interface</a></li>
                <li><a href="/testing_mode">Testing Mode</a></li>
                <li><a href="/">---</a></li>
                <li><a href="/">Login</a></li>
            </ul>
        </nav>
    </div>
    
);

export default SideDrawer;