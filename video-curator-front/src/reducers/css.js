//css reducer.

const css = (state = "empty", action) => {
  switch (action.type) {
    case 'SET_CSS':
      return action.css;
    default:
      return state
  }
}

export default css
