import React from 'react';
import {shallow, mount, render} from 'enzyme';
import {expect} from 'chai';
import sinon from 'sinon';

import VideoList from './VideoList';
import Video from '../Video/Video';

let testVideos;
let thumbnails;

describe('List', () => {
  beforeEach(() => {
    testVideos = [
      {id:1, title:"video1", thumbnail:"https://i.ytimg.com/vi/jRHQPG1xd9o/default.jpg"},
      {id:2, title:"video2", thumbnail:"https://i.ytimg.com/vi/zua831utwMM/default.jpg"},
      {id:3, title:"video3", thumbnail:"https://i.ytimg.com/vi/LrKNCs60yVQ/default.jpg"}
    ]
  })
  it('should render without error', ()=>{
    const wrapper = shallow(<VideoList videos={testVideos}/>);
    expect(wrapper).to.be.ok;
  });
  it('should find component using its class name', ()=>{
    const wrapper = shallow(<VideoList videos={testVideos}/>);
    expect(wrapper.find(".videoList").length).to.equal(1);
  });
  it('should render out 3 videos', ()=>{
    const wrapper = mount(<VideoList videos={testVideos}/>);
    expect(wrapper.find('.video').length).to.equal(3);
  });
  it('should display video2 in the 2nd box', ()=>{
    const wrapper = mount(<VideoList videos={testVideos}/>);
    expect(wrapper.find(Video).at(1).find('div > div > div > div > div').at(3).find('a').html()).to.equal('<a>video2</a>');
  });
  // it('should call fn when 2nd box is clicked', ()=>{
  //   const stub = sinon.stub();
  //   const wrapper = mount(<List click={stub} header="Students" items={students}/>);
  //   wrapper.find(Box).at(1).find('div > div').simulate('click');
  //   expect(stub.callCount).to.equal(1);
  // });
});
