import axios from 'axios'

const state = {
    wallPosts: null,
}

const getters = {
    getWallPosts: (state) => {
        return state.wallPosts
    },
}

const mutations = {
    setWallPosts: (state, value) => {
        state.wallPosts = value
    },
}

const actions = {

    GET_WALL: (context, token) => {
        return new Promise((resolve, reject) => {
            var url = '/api/wallpost';
            axios
                .get(url, {
                    headers: {
                        Authorization: 'Bearer ' + token
                    }
                }).then(response => {
                console.log(response);
                context.commit('setWallPosts', response.data)
                resolve({success: true})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    WALL_POST_PUBLISH: (context, data) => {
        console.log("WALL_POST_PUBLISH invoke " + data);
        return new Promise((resolve, reject) => {
            var url = "/api/wallpost";
            axios({
                method: 'post',
                url: url,
                data: JSON.stringify(data),
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + localStorage.jwtToken
                },
                async: false,
            }).then(response => {
                console.log(response);
                resolve({success: true})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },
}

export default {
    state,
    getters,
    mutations,
    actions,
}
