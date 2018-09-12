// import store from '../index'

// // const initialState = store.getState();
  


// const reducer = (state = [], action) => {
//     switch (action.type) {
//         case 'EMBED_VIDEO':
//             const id = action.id
//             const index = action.videos.findIndex(v => v.id==id);
//             const isEmbed = action.videos[index].css === "empty";
//             const newcss = isEmbed ? "embedded" : "empty";
//             const embedded = isEmbed ? true : false;
//             return action.videos.map(v => ({...v, css: v.id == id ? newcss : v.css, embedded: v.id == id ? embedded : v.embedded}));
//             // return { ...state.videos, videoData: action.videos };
//         default:
//             return state
//     }
// }

// export default reducer;
