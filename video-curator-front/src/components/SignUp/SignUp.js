import React from 'react';
import axios from 'axios';
import './SignUp.css';

export default class SignUp extends React.Component{
  constructor(props){
    super(props);
    this.state = {};
    this.signUp = this.signUp.bind(this);
  }

  signUp(e){
    console.log("Sign Up");
    e.preventDefault();
    this.setState({error: null});
    const username = this.username.value;
    const password = this.password.value;
    if(username.length < 5){
      this.setState({error: 'Email too short'});
      return;
    }

    const url = this.props.host + '/users';
    const payload = {username, password};
    axios.post(url, payload)
    .then(rsp => {
      console.log(rsp);
      this.username.value = "";
      this.password.value = "";
      
    }).catch(e => this.setState({error: e.message}));
  }

  render(){
    return (
      <div className="signup">
        <div className="row">
          <div className="col-sm-4">
          </div>
          <div className="col-sm-4">
            <div className="panel panel-default">
              <div className="panel-body">
                <div className={this.state.error ? "error" : ""}>
                  {this.state.error}
                </div>
                <h3> Sign Up </h3>
                <form>
                  <div className="form-group">
                    <input placeholder="username" className="form-control" ref={n => this.username = n}/>
                    <br />
                    <input placeholder="password" className="form-control" ref={n => this.password = n}/>
                  </div>
                  <button className="btn btn-success btn-small" onClick={this.signUp}>Sign Up</button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
