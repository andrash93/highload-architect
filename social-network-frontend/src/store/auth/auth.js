import axios from 'axios'

const state = {
    authAccountId : '',
    jwtToken: '',
}

const getters = {
    getJwtToken: (state) => {
        return state.jwtToken
    },
    getAuthAccountId: (state) => {
        return state.authAccountId
    },
}

const mutations = {
    setJwtToken: (state, value) => {
        state.jwtToken = value
    },
    setAuthAccountId: (state, value) => {
        state.authAccountId = value
    },
}

const actions = {

    REFRESH_TOKEN: (context, token) => {
        return new Promise((resolve, reject) => {
            var url = '/api/authenticate/refresh';
            axios
                .get(url, {
                    headers: {
                        Authorization: 'Bearer ' + token
                    }
                }).then(response => {
                context.commit('setJwtToken', response.data.jwtToken)
                context.commit('setAuthAccountId', response.data.accountId)
                resolve({success: true})
            }).catch(error => {
                    console.log(error)
                    reject({errorMessage: error})
                })
        })
    },

    AUTH: (context, data) => {
        return new Promise((resolve, reject) => {
            axios.post('/api/authenticate', data)
                .then(response => {
                    console.log(response);
                    context.commit('setJwtToken', response.data.jwtToken)
                    context.commit('setAuthAccountId', response.data.accountId)
                    resolve({success: true})
                }).catch(error => {
                console.log('auth ERROR ' + error)
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
