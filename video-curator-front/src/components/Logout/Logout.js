import React from 'react';
import './Logout.css';

export default (props) => {
  return (
    <div className="logout">
      <button className="btn btn-default btn-small" onClick={props.logout}>Logout</button>
    </div>
  )
}
