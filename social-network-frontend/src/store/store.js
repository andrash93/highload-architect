import Vue from 'vue'
import Vuex from 'vuex'

import auth from './auth/auth'
import layuot from './layuot/layuot'
import account from './account/account'
import friends from './friends/friends'

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {},
  getters: {},
  mutations: {},
  actions: {},
  modules: {
    auth,
    layuot,
    account,
    friends,
  }
})

export default store
