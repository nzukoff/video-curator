import React from 'react';
import './Counter.css';

export default (props) => {
  return (
    <div className="counter">
      <a onClick={() => props.vote("upvote")} className="upvote"><span className="glyphicon glyphicon-menu-up" aria-hidden="true"></span></a>
      <div className="votes">{props.votes}</div>
      <a onClick={() => props.vote("downvote")} className="downvote"><span className="glyphicon glyphicon-menu-down" aria-hidden="true"></span></a>
      {/*glyphicon-triangle-top glyphicon-menu-up glyphicon-chevron-up glyphicon-arrow-up glyphicon-hand-up*/}
    </div>
  )
}
