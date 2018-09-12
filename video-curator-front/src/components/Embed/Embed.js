import React from 'react';
import './Embed.css';

export default (props) => {
  return (
    <div className="embed">
        <span onClick={props.embeds} className="glyphicon glyphicon-play" aria-hidden="true"></span>
        <br />
        <div className={props.css}>
          <div className="embedder">
            {props.embedded ? <iframe width="560" height="315" src={props.embedLink} frameborder="0" allowfullscreen></iframe> : ""}
          </div>
        </div>
    </div>
  )
}


