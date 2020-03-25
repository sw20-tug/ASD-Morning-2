# ASD-MORNING-2: Vocabulary Trainer (LANG)
[![Issues](https://img.shields.io/github/issues/sw20-tug/ASD-Morning-2)](https://github.com/sw20-tug/ASD-Morning-2/issues)

# Table of Contents:
- [Setup](#setup)
    - [Tools Used](#tools-used)
    - [Fork Setup for Members](#fork-setup-for-members)
    - [Installation](#installation)

# Setup:
## Tools Used:
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Visual Studio Code](https://code.visualstudio.com/)
- [Maven](https://maven.apache.org/)
- [Spring](https://spring.io/projects/spring-boot)
- [NextJS](https://nextjs.org/)
- [React Bootstrap](https://react-bootstrap.github.io/)
- [H2Database](https://www.h2database.com/html/main.html)

## Fork Setup for Members:
Fork the repository with the fork button above, then clone it:

    git clone git@github.com:YOUR_USERNAME/ASD-Morning-2.git

Add the upstream:

    git remote add upstream git@github.com:sw20-tug/ASD-Morning-2.git

Pull the latest commit:

    git pull upstream develop

After making changes / implementations, push to your feature branch:

    git push origin <featurebranch>

Create a pull request to the team repo and wait for review.

## Installation:
Before doing anything:
    
    sudo apt update

If you don't have Curl:

    sudo apt install curl

Install NodeJS if not present:

    curl -sL https://deb.nodesource.com/setup_13.x | sudo -E bash -
    sudo apt install nodejs

Check installation:

    node -v
    npm -v

Install Maven if not present:
    
    sudo apt install maven

Check version:

    mvn -version

Install the relevant node dependencies in the client folder, cd into client and execute:

    npm install

When everything is installed correctly, try to run the frontend:
    
    npm run dev