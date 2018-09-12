import React from 'react';
import {shallow, mount, render} from 'enzyme';
import {expect} from 'chai';
import sinon from 'sinon';

import VideoCurator from './VideoCurator';
import VideoList from '../VideoList/VideoList';
import Video from '../Video/Video';

describe('VideoCurator', () => {
  it('should render without error', ()=>{
    const wrapper = shallow(<VideoCurator/>);
    expect(wrapper).to.be.ok;
  });
});
