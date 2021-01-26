import axios from 'axios'

const state = {
    friends: null,
}

const getters = {
    getFriends: (state) => {
        return state.friends
    },
}

const mutations = {
    setFriends: (state, value) => {
        state.friends = value
    },
}

const actions = {

    GET_FRIENDS: (context, token) => {
        return new Promise((resolve, reject) => {
            var url = '/api/friends';
            axios
                .get(url, {
                    headers: {
                        Authorization: 'Bearer ' + token
                    }
                }).then(response => {
                console.log(response);
                context.commit('setFriends', response.data)
                resolve({success: true})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    GET_INCOMING_FRIEND_REQUESTS: (context, token) => {
        return new Promise((resolve, reject) => {
            var url = '/api/friends/incoming';
            axios
                .get(url, {
                    headers: {
                        Authorization: 'Bearer ' + token
                    }
                }).then(response => {
                console.log(response);
                context.commit('setFriends', response.data)
                resolve({success: true})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    GET_OUTGOING_FRIEND_REQUESTS: (context, token) => {
        return new Promise((resolve, reject) => {
            var url = '/api/friends/outgoing';
            axios
                .get(url, {
                    headers: {
                        Authorization: 'Bearer ' + token
                    }
                }).then(response => {
                console.log(response);
                context.commit('setFriends', response.data)
                resolve({success: true})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    ADD_FRIEND: (context, data) => {
        console.log("ADD_FRIEND invoke " + data);
        return new Promise((resolve, reject) => {
            var url = "/api/friend/add";
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
                resolve({response: response})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    CONFIRM_FRIEND: (context, data) => {
        console.log("CONFIRM_FRIEND invoke " + data);
        return new Promise((resolve, reject) => {
            var url = "/api/friend/confirm";
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
                resolve({response: response})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    DELETE_FRIEND: (context, data) => {
        console.log("DELETE_FRIEND invoke " + data);
        return new Promise((resolve, reject) => {
            var url = "/api/friend/delete";
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
                resolve({response: response})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    CANCEL_FRIEND: (context, data) => {
        console.log("CANCEL_FRIEND invoke " + data);
        return new Promise((resolve, reject) => {
            var url = "/api/friend/cancel";
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
                resolve({response: response})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    DECLINE_FRIEND: (context, data) => {
        console.log("DECLINE_FRIEND invoke " + data);
        return new Promise((resolve, reject) => {
            var url = "/api/friend/decline";
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
                resolve({response: response})
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
