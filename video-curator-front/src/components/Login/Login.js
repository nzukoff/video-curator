import React from 'react';
import axios from 'axios';
import './Login.css';

export default class Login extends React.Component{
  constructor(props){
    super(props);
    this.state = {};
    this.login = this.login.bind(this);
  }

  login(e){
    console.log("LOGIN");
    e.preventDefault();
    this.setState({error: null});
    const username = this.username.value;
    const password = this.password.value;
    if(username.length < 5){
      this.setState({error: 'Email too short'});
      return;
    }
    const url = this.props.host + '/authenticate';
    const payload = {username, password};
    axios.post(url, payload)
    .then(res => {
      localStorage.clear();
      localStorage.setItem('token', res.headers.authorization);
      console.log("TOKEN IS ",res.headers.authorization);
      this.username.value="";
      this.password.value="";
      window.dispatchEvent(new Event('login'));
    }).catch(e => this.setState({error: e.message}));
  }

  render(){
    return (
      <div className="login">
        <div className="row">
          <div className="col-sm-4">
          </div>
          <div className="col-sm-4">
            <div className="panel panel-default">
              <div className="panel-body">
                <div className={this.state.error ? "error" : ""}>
                  {this.state.error}
                </div>
                <h3> Login </h3>
                <form>
                  <div className="form-group">
                    <input placeholder="username" className="form-control" ref={n => this.username = n}/>
                    <br />
                    <input placeholder="password" className="form-control" ref={n => this.password = n}/>
                  </div>
                  <button className="btn btn-success btn-small" onClick={this.login}>Login</button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
