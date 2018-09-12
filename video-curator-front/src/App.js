import React, { Component } from 'react';
import Nav from './components/Nav/Nav';
import VideoCurator from './components/VideoCurator/VideoCurator';
import SignUp from './components/SignUp/SignUp';
import Login from './components/Login/Login';
import SubmitVideo from './components/SubmitVideo/SubmitVideo';
import { BrowserRouter as Router, Route, Link } from 'react-router-dom';

class App extends Component {
  render() {
    return (
      <div className="App">
        <Router>
          <div className="router">
            <Nav host="http://localhost:8080"/>
            <Route exact path="/" component={() => (<VideoCurator host="http://localhost:8080"/>)} />
            <Route path="/home" component={() => (<VideoCurator host="http://localhost:8080"/>)} />
            <Route path="/sign-up" component={() => (<SignUp host="http://localhost:8080"/>)} />
            <Route path="/login" component={() => (<Login host="http://localhost:8080"/>)} />
            <Route path="/submit-video" component={() => (<SubmitVideo host="http://localhost:8080"/>)} />
          </div>
        </Router>
      </div>
    );
  }
}

export default App;
