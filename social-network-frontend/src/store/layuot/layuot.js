const state = {
    layout: '',
}

const getters = {
    getLayout: (state) => {
        return state.layout
    },
}

const mutations = {
    setLayout: (state, value) => {
        state.layout = value
    },
}

const actions = {
}

export default {
    state,
    getters,
    mutations,
    actions,
}
