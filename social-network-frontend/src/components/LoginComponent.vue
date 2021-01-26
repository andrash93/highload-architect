<template>
  <div class="text-center">
    <form class="form-signin">
      <h1 class="h3 mb-3 font-weight-normal">Авторизация</h1>
      <label for="inputEmail" class="sr-only">Email</label>
      <input v-model="login" type="email" id="inputEmail" class="form-control" placeholder="Email" required autofocus>
      <label for="inputPassword" class="sr-only">Password</label>
      <input v-model="password" type="password" id="inputPassword" class="form-control" placeholder="Password" required>
      <button class="btn btn-lg btn-primary btn-block" @click="onLoginClick()">Войти</button>
      <button class="btn btn-lg btn-primary btn-block" @click="onRegisterClick()">Регистрация</button>
      <p class="mt-5 mb-3 text-muted">&copy; 2020</p>
    </form>
  </div>
</template>

<script>
import store from "@/store/store";
import Router from "@/router";

export default {
  name: 'LoginComponent',

  data() {
    return {
      login: "",
      password: "",
    }
  },

  methods: {
    onLoginClick() {
      const self = this
      let auth = {
        name: self.login,
        password: self.password
      }
      store.dispatch('AUTH', auth)
          .then(() => {
            console.log("login success  " + self.$store.getters.getJwtToken)
            store.commit('setLayout', "MainComponent")
            Router.push({path: '/'})
          }).finally(() => {
      })
    },

    onRegisterClick() {
      store.commit('setLayout', "EmptyComponent")
      Router.push({path: '/register'})
    }
  }

};
</script>

<style>

.form-signin {
  width: 100%;
  max-width: 330px;
  padding: 15px;
  margin: 0 auto;
}

.form-signin .checkbox {
  font-weight: 400;
}

.form-signin .form-control {
  position: relative;
  box-sizing: border-box;
  height: auto;
  padding: 10px;
  font-size: 16px;
}

.form-signin .form-control:focus {
  z-index: 2;
}

.form-signin input[type="email"] {
  margin-bottom: -1px;
  border-bottom-right-radius: 0;
  border-bottom-left-radius: 0;
}

.form-signin input[type="password"] {
  margin-bottom: 10px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}

</style>
