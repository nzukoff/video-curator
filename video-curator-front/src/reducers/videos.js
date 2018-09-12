const initialState = {
  isFetching: true,
  videoData: []
}

const reducer = (state = initialState, action) => {
  switch (action.type) {
    case 'DO_INITIAL_FETCH_FULFILLED':
      return {...state, isFetching: false, videoData: action.payload};
    case 'EMBED_VIDEO':
      let id = action.id
      const index = action.videos.findIndex(v => v.id==id);
      const isEmbed = action.videos[index].css === "empty";
      const newcss = isEmbed ? "embedded" : "empty";
      const embedded = isEmbed ? true : false;
      return {...state, videoData: action.videos.map(v => ({...v, css: v.id == id ? newcss : v.css, embedded: v.id == id ? embedded : v.embedded}))};
    case 'CAST_VOTE_FULFILLED':
    console.log("ACTION IS ", action)
      id = action.meta.id
      const votes = action.payload
      return {...state, videoData: action.meta.videos.map(v => ({...v, votes: v.id == id ? votes : v.votes}))};
  default:
      return state
  }
  
}

export default reducer;
