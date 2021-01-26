<template>
  <component :is="layuot">
    <router-view/>
  </component>
</template>

<script>
import MainComponent from "./components/MainComponent.vue";
import EmptyComponent from "./components/EmptyComponent.vue";
import Router from './router'
import Vuex from 'vuex'
import Vue from 'vue'
import store from "@/store/store";

Vue.use(Vuex)

export default {
  name: 'App',
  router: Router,
  store: store,
  components: {
    MainComponent,
    EmptyComponent,
  },

  data() {
    return {
      layuot: "",
      isAuth: false,
    }
  },

  created() {
    const self = this

    this.$store.watch(
        state => {
          return self.$store.getters.getLayout
        },
        (newValue, oldValue) => {
          this.layuot = newValue
        },
        {deep: true}
    )

    this.$store.watch(
        state => {
          return self.$store.getters.getJwtToken
        },
        (token, oldValue) => {
          // localStorage.removeItem("jwtToken");
          localStorage.jwtToken = token
        },
        {deep: true}
    )

    if (localStorage.jwtToken) {
      console.log("jwtToken exist, try refresh token")
      store.dispatch('REFRESH_TOKEN', localStorage.jwtToken)
          .then(() => {
            console.log("refresh token success  " + self.$store.getters.getJwtToken)
            store.commit('setLayout', "MainComponent")
          }).catch(error => {
        console.log("refresh token error")
        store.commit('setLayout', "EmptyComponent")
        Router.push({path: 'login'})
      }).finally(() => {
      })
    } else {
      console.log("jwtToken not exist, try login")
      store.commit('setLayout', "EmptyComponent")
      Router.push({path: 'login'})
    }
  },
}

</script>

<style>
</style>
