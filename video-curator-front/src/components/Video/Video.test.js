import React from 'react';
import {shallow, mount, render} from 'enzyme';
import {expect} from 'chai';
import sinon from 'sinon';

import Video from './Video';

describe('Video', () => {
  it('should render without error', () => {
    const wrapper = shallow(<Video />);
    expect(wrapper).to.be.ok;
  });

  it('should find component using its class name', () => {
    const wrapper = shallow(<Video />);
    expect(wrapper.find(".video").length).to.equal(1);
  });

  it('should get the title from component', () => {
    const wrapper = shallow(<Video title="video1" />);
    expect(wrapper.find(".title").text()).to.equal('video1');
  });

  // it('should get the primary key (id) from component', () => {
  //   const wrapper = shallow(<Video id="3" />);
  //   const html = wrapper.html();
  //   console.log("HTML is ")
  //   expect(html).to.equal('<div class="video" data-id="3"><div class="container"><div class="panel panel-default movie-panel"><div class="panel-body"><div class="row"><div class="col-sm-2"><p>Counter goes here</p></div><div class="col-sm-2"><p>Thumbail image here</p></div><div class="col-sm-8 text-left title"></div></div></div></div></div></div>');
  // });

  it('should get the thumbnail url from component', () => {
    const wrapper = shallow(<Video thumbnail="image.com" />);
    const html = wrapper.html();
    const thumb = wrapper.find(".video").find('div > div > div > div > div').at(2).html();
    expect(thumb).to.equal('<div class="col-sm-3"><img class="thumbnail" src="image.com"/></div>');
  });

  it('should get the link from component', () => {
    const wrapper = shallow(<Video link="youtube.com" title="video1"/>);
    const html = wrapper.html();
    const link = wrapper.find(".video").find('div > div > div > div > div').at(3).find('a').html();
    expect(link).to.equal('<a href="youtube.com">video1</a>');
  });

  it('should render out full component', () => {
    const wrapper = shallow(<Video title="video1" link="youtube.com" thumbnail="image.com" id="3"/>);
    const html = wrapper.html();
    expect(html).to.equal('<div class="video" data-id="3"><div class="container"><div class="panel panel-default movie-panel"><div class="panel-body"><div class="row"><div class="col-sm-3"><div class="counter"><button type="button" class="btn btn-outline-primary btn-sm" data-votetype="upvote">++</button><div></div><button type="button" class="btn btn-outline-primary btn-sm" data-votetype="downvote">--</button></div></div><div class="col-sm-3"><img class="thumbnail" src="image.com"/></div><div class="col-sm-6 text-left title"><a href="youtube.com">video1</a></div></div></div></div></div></div>');
  });

  // key={i} title={v.title} link={v.link} thumbnail={v.thumbnail} votes={v.votes} vote={props.vote} id={v.id}

  // it('should call the parents function when clicked', () => {
  //   const stub = sinon.stub();
  //   const wrapper = shallow(<Video click={stub} />);
  //   wrapper.find('.playButton').simulate('click');
  //   expect(stub.callCount).to.equal(1);
  // });
});
