import React from 'react';
import Video from '../Video/Video'

export default (props) => {
  const videos = props.videos || [];
  return (
    <div className="videoList">
      <div className="row">
        <div className="col-sm-12">
          {
            videos.map((v,i) => {
              return <Video key={i} title={v.title} link={v.link} timeSince={v.timeSince} duration={v.duration} thumbnail={v.thumbnail} votes={v.votes} vote={(vote) => props.vote(vote,v.id,props.host)} id={v.id} created={v.created} embedLink={v.embedLink} user={v.user} css={v.css} embeds={() => props.embeds(v.id)} embedded={v.embedded} place={i+1} />
            })
          }
        </div>
      </div>
    </div>
  )
}
