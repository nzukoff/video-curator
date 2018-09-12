import React from 'react';
import {shallow, mount, render} from 'enzyme';
import {expect} from 'chai';
import sinon from 'sinon';

import Counter from './Counter';

describe('Counter', () => {
  it('should render without error', () => {
    const wrapper = shallow(<Counter />);
    expect(wrapper).to.be.ok;
  });

  it('should find component using its class name', () => {
    const wrapper = shallow(<Counter />);
    expect(wrapper.find(".counter").length).to.equal(1);
  });

  it('should get the votes from component', () => {
    const wrapper = shallow(<Counter votes="1" />);
    expect(wrapper.find(".counter").text()).to.equal('++1--');
  });

  it('should call the parents function when clicked', () => {
    const stub = sinon.stub();
    const wrapper = shallow(<Counter vote={stub} />);
    wrapper.find('.btn').at(0).simulate('click');
    expect(stub.callCount).to.equal(1);
  });

  it('should render out full component', () => {
    const stub = sinon.stub();
    const wrapper = shallow(<Counter vote={stub} votes="3"/>);
    const html = wrapper.html();
    expect(html).to.equal('<div class="counter"><button type="button" class="btn btn-outline-primary btn-sm" data-votetype="upvote">++</button><div>3</div><button type="button" class="btn btn-outline-primary btn-sm" data-votetype="downvote">--</button></div>');
  });



});
