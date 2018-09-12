import React from 'react';
import './Video.css';
import Counter from '../Counter/Counter';
import Embed from '../Embed/Embed'

export default (props) => {

  return (
    <div className="video">
    {/*if not logged in, add div className="container"*/}
        <div className="panel panel-default">
          <div className="panel-body">
            <div className="row">
              <div className="col-sm-1 place">
                {props.place}
              </div>
              <div className="col-sm-1">
                <Counter votes={props.votes} vote={(vote) => props.vote(vote)}/>
              </div>
              <div className="col-sm-2">
                <img className="thumbnail" src={props.thumbnail} onClick={props.embeds}/>
              </div>
              <div className="col-sm-7 title">
                <a href={props.link}>{props.title} - [{props.duration}]</a>
                <br/>
                <div className="details">
                  Submitted {props.timeSince} ago by {props.user.username}
                </div>
                <div className="embeds">
                  <Embed embeds={props.embeds} embedded={props.embedded} embedLink={props.embedLink} css={props.css}/>
                </div>
              </div>
            </div>
          </div>
        </div>
    </div>
  );
};
