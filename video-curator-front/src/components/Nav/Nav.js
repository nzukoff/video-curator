import React from 'react';
import {Link, browserHistory} from 'react-router-dom';
import axios from 'axios';
import Redirect from 'react-router/Redirect';

export default class Nav extends React.Component {
  constructor(props) {
    super(props);
    this.credentials = this.credentials.bind(this);
    this.logout = this.logout.bind(this);
    this.listen = this.listen.bind(this);
    this.state = { active: false, user: { } };
  }

  componentDidMount() {
    this.credentials();
    this.listen();
  }

  logout() {
    localStorage.clear();
    this.setState({ active: false });
  }

  listen() {
    window.addEventListener('login', () => {
      this.credentials();
    });
  }

  credentials() {
    const authorization = localStorage.getItem('token');
    const url = this.props.host + '/users/credentials';
    axios.get(url, { headers: { authorization } })
    .then(res => {
      console.log("USER IS ",res.data);
      this.setState({ user: res.data, active: true });
      let user = {...this.state.user, votes:1}
      this.setState({user:user})

    }).catch(() => {
      this.setState({ active: false });
    });
  }

  render() {
    const links = [];
      if (this.state.active) {
        links.push(<li key={0}><Link to="/submit-video">Submit Video</Link></li>);
        links.push(<li key={1}><a href="#" onClick={this.logout}>Logout</a></li>);
      } else {
        links.push(<li key={10}><Link to="/sign-up">Sign Up</Link></li>);
        links.push(<li key={11}><Link to="/login">Login</Link></li>);
      }
    return (
      <nav className="navbar navbar-default">
        <div className="container-fluid">
          <div className="navbar-header">
            <button type="button" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
              <span className="sr-only">Toggle navigation</span>
              <span className="icon-bar" />
              <span className="icon-bar" />
              <span className="icon-bar" />
            </button>
            <a className="navbar-brand" href="/">Video Curator</a>
          </div>

          <div className="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul className="nav navbar-nav" />
            <ul className="nav navbar-nav navbar-right">
              <li><Link to="/">Home</Link></li>
              {links}
            </ul>
          </div>
        </div>
      </nav>
    )
  }
}
