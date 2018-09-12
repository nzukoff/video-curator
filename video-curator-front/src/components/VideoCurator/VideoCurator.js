import React from 'react';
// import ReactDOM from 'react-dom';
import axios from 'axios';
import moment from 'moment';
import VideoList from '../VideoList/VideoList';
import Counter from '../Counter/Counter';
import Login from '../Login/Login';
import SignUp from '../SignUp/SignUp';
import Logout from '../Logout/Logout';
import Nav from '../Nav/Nav';
import SubmitVideo from '../SubmitVideo/SubmitVideo';

import { connect } from 'react-redux'
import { doInitialFetch, embeds, castVote } from '../../actions'
import { bindActionCreators } from 'redux'


const mapDispatchToProps = (dispatch) => {
  return bindActionCreators({ doInitialFetch, embeds, castVote }, dispatch);
}

const mapStateToProps = (state) => {
  return {
    videos: state.videos.videoData
  }
}

export class VideoCurator extends React.Component {
  constructor(props) {
    super(props)
    this.logout = this.logout.bind(this);
  }

  componentDidMount(){
    this.props.doInitialFetch(this.props.host);
  }

  logout() {
    localStorage.clear();
  }

  render() {
    return (
      <div className="videoCurator">
        <div className="container">
          <div className="row">
            <div className="col-sm-12">
              <VideoList videos={this.props.videos} vote={this.props.castVote} host={this.props.host} embeds={this.props.embeds}/>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(VideoCurator)