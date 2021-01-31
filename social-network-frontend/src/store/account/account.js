import axios from 'axios'

const state = {
    accounts: null,
}

const getters = {
    getAccounts: (state) => {
        return state.accounts
    },
}

const mutations = {
    setAccounts: (state, value) => {
        state.accounts = value
    },
}

const actions = {
    ACCOUNT_REGISTER: (context, data) => {
        console.log("ACCOUNT_REGISTER invoke " + data);
        return new Promise((resolve, reject) => {
            //     axios.post('/api/account/register', JSON.stringify(data))
            //         .then(response => {
            //             console.log(response);
            //             localStorage.jwtToken = response.data.jwtToken
            //             context.commit('setJwtToken', response.data.jwtToken)
            //             resolve({success: true})
            //         })
            // }).catch(error => {
            //     console.log('register ERROR ' + error)
            // })
            var url = "/api/account/register";
            axios({
                method: 'post',
                url: url,
                data: JSON.stringify(data),
                headers: {'Content-Type': 'application/json'},
                async: false,
            }).then(response => {
                console.log(response);
                localStorage.jwtToken = response.data.jwtToken
                context.commit('setJwtToken', response.data.jwtToken)
                context.commit('setAuthAccountId', response.data.id)
                resolve({success: true})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    GET_ALL_ACCOUNTS: (context, token) => {
        return new Promise((resolve, reject) => {
            var url = '/api/account/all';
            axios
                .get(url, {
                    headers: {
                        Authorization: 'Bearer ' + token
                    }
                }).then(response => {
                console.log(response);
                context.commit('setAccounts', response.data)
                resolve({success: true})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    SEARCH_ACCOUNTS: (context, data) => {
        return new Promise((resolve, reject) => {
            var url = '/api/account/search?';
            if(data.firstname!==""){
                url+="firstName="+data.firstname+"&";
            }
            if(data.surname!==""){
                url+="lastName="+data.surname;
            }
            axios
                .get(url, {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.jwtToken
                    }
                }).then(response => {
                console.log(response);
                context.commit('setAccounts', response.data)
                resolve({success: true})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    GET_ACCOUNT: (context, id) => {
        return new Promise((resolve, reject) => {
            var url = '/api/account/info/' + id;
            axios
                .get(url, {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.jwtToken
                    }
                }).then(response => {
                console.log(response);
                context.commit('setAccounts', response.data)
                resolve({success: true})
            }).catch(error => {
                console.log(error)
                reject({errorMessage: error})
            })
        })
    },

    GET_CURRENT_ACCOUNT: (context) => {
        return new Promise((resolve, reject) => {
            var url = '/api/account/current';
            axios
                .get(url, {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.jwtToken
                    }
                }).then(response => {
                console.log(response);
                context.commit('setAccounts', response.data)
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
