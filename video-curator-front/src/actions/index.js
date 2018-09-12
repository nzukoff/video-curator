import React from 'react';
// import ReactDOM from 'react-dom';
import axios from 'axios';
import moment from 'moment';
import store from '../index'

//attempting async/await

// async function fetch(host) {
//     const url = host + '/videos';
//     let response;
//     try {
//         response = await axios.get(url)
//     } catch (e) {
//         console.log(e)
//     }
//     return await updateVideoData(response.data)
// }

// async function updateVideoData(videoData) {
//     return videoData.map(async function(v, i) {
//         const key = "AIzaSyC6dS-h4pEP6P8FLmuNFs3NyDv7zqxTEEw";
//         const id = v.link.match(/(?:youtube\.com\/(?:[^\/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?\/ ]{11})/)[1];
//         const url = `https://www.googleapis.com/youtube/v3/videos?id=${id}&part=contentDetails&key=${key}`
//         let ytData;
//         try {
//             ytData = await axios.get(url);
//         } catch (e) {
//             console.log(e)
//         }
//         const durationY = ytData.data.items[0].contentDetails.duration;
//         const duration = findDuration(durationY);
//         const embedLink = `https://www.youtube.com/embed/${id}`
//         const timeSince = findTimeSince(v);
//         const thumbnail = findThumbnail(v, id);
//         const videoUpdate = updateVideo(v, thumbnail, timeSince, duration, embedLink);
//         return videoUpdate;
//     })
// }

function fetch(host) {
    const url = host + '/videos';

    return axios
        .get(url)
        .then(response => updateVideoData(response.data))
}

function updateVideoData(videoData) {
    var promises = videoData.map((v) => {
        const key = "AIzaSyC6dS-h4pEP6P8FLmuNFs3NyDv7zqxTEEw";
        const id = v.link.match(/(?:youtube\.com\/(?:[^\/]+\/.+\/|(?:v|e(?:mbed)?)\/|.*[?&]v=)|youtu\.be\/)([^"&?\/ ]{11})/)[1];
        const url = `https://www.googleapis.com/youtube/v3/videos?id=${id}&part=contentDetails&key=${key}`
        return axios
            .get(url)
            .then(r => {
                const durationY = r.data.items[0].contentDetails.duration;
                const duration = findDuration(durationY);
                const embedLink = `https://www.youtube.com/embed/${id}`
                const timeSince = findTimeSince(v);
                const thumbnail = findThumbnail(v, id);
                let updatedVideo = updateVideo(v, thumbnail, timeSince, duration, embedLink);
                return updatedVideo;
            })
    })
    return Promise.all(promises).then(results => results)
}

function castAVote(vote, id, host) {
    //   let videoUpdate = this.props.videos.map(v => ({...v, votes: v.id == id ? video.votes : v.votes}));
    //   let videos = videoUpdate.sort(function(v1, v2) {
    //       return parseInt(v2.votes) - parseInt(v1.votes);
    //   });
    // let votes 
    const url = `${host}/videos/${id}/${vote}`
    console.log("URL IS ", url)
    const authorization = localStorage.getItem('token');
    return axios.put(url, null, { headers: { authorization: authorization } })
    .then(r => {
        console.log("R IS ", r)
        return r.data.votes
    })
}

function findDuration(duration) {
    const durationM = moment.duration(duration);
    let hours = durationM._data.hours;
    if (hours < 10 && hours > 0) {
        hours = `0${hours}`
    }
    let minutes = durationM._data.minutes;
    if (minutes < 10) {
        minutes = `0${minutes}`
    }
    let seconds = durationM._data.seconds;
    if (seconds < 10) {
        seconds = `0${seconds}`
    }
    return hours ? `${hours}:${minutes}:${seconds}` : `${minutes}:${seconds}`;
}

function findTimeSince(v) {
    const hours = Math.ceil(Math.abs(new Date() - new Date(v.created)) / (60 * 60 * 1000));
    const hoursSince = hours < 2 ? `${hours} hour ` : `${hours} hours `;
    const days = Math.floor(hours / 24);
    const daysSince = days > 1 ? `${days} days` : `${days} day`
    return hours <= 24 ? hoursSince : daysSince;
}

function findThumbnail(v, id) {
    return `https://i.ytimg.com/vi/${id}/default.jpg`;
}

function updateVideo(v, thumbnail, timeSince, duration, embedLink, place) {
    return { ...v, thumbnail: thumbnail, timeSince: timeSince, duration: duration, embedLink: embedLink, css: "empty", embedded: false };
}

function sortVideos(videos) {
    return videos.sort(function (v1, v2) {
        return parseInt(v2.votes) - parseInt(v1.votes);
    })
}


export const doInitialFetch = (host) => {
    return {
        type: 'DO_INITIAL_FETCH',
        payload: fetch(host)
    }
}

export const embeds = (id) => {
    return {
        type: 'EMBED_VIDEO',
        videos: store.getState().videos.videoData,
        id: id
    }
}

export const castVote = (vote, id, host) => {
    return {
        type: 'CAST_VOTE',
        payload:  castAVote(vote, id, host),
        meta: {
            videos: store.getState().videos.videoData,
            id: id
        }
        
    }
}

// export async function doInitialFetch(host) {
//     const data = await fetch(host).then(res => res)
//     console.log("DATA IS ", data)
//     return {
//         type: 'DO_INITIAL_FETCH',
//         data
//     }
// }