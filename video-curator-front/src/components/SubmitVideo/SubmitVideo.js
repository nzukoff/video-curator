import React from 'react';
import axios from 'axios';
import './SubmitVideo.css';

export default class SubmitVideo extends React.Component{
  constructor(props){
    super(props);
    this.state = {};
    this.submit = this.submit.bind(this);
  }

  submit(e){
    console.log("Submit");
    e.preventDefault();
    this.setState({error: null});
    const title = this.title.value;
    const link = this.link.value;
    console.log("TITLE IS ",title);
    console.log("LINK IS ",this.link.value);
    if(title.length < 5){
      this.setState({error: 'Title is too short'});
      return;
    }
    const url = this.props.host + '/videos';
    const payload = {title:title,link:link};
    const authorization = localStorage.getItem('token');
    axios.post(url, payload, { headers: { authorization: authorization } })
    .then(rsp => {
      this.title.value = "";
      this.link.value = "";
      const video = rsp.data;

    }).catch(e => this.setState({error: e.message}));
  }

  render(){
    return (
      <div className="submitVideo">
        <div className="row">
          <div className="col-sm-4">
          </div>
          <div className="col-sm-4">
            <div className="panel panel-default">
              <div className="panel-body">
                <div className={this.state.error ? "error" : ""}>
                  {this.state.error}
                </div>
                <h3> Submit Video </h3>
                <form>
                  <div className="form-group">
                    <input placeholder="title" className="form-control" ref={n => this.title = n}/>
                    <br />
                    <input placeholder="link" className="form-control" ref={n => this.link = n}/>
                  </div>
                  <button className="btn btn-success btn-small" onClick={this.submit}>Submit Video</button>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>

    );
  }
}
