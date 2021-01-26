<template>
<!--    <div class="modal">-->
<!--      <form>-->
<!--        <div class="form-group">-->
<!--          <label for="login">login</label>-->
<!--          <input type="text" class="form-control" id="login" placeholder="login" v-model="login">-->
<!--        </div>-->
<!--        <div class="form-group">-->
<!--          <label for="password">password</label>-->
<!--          <input type="text" class="form-control" id="password" placeholder="password" v-model="password">-->
<!--        </div>-->
<!--        <div class="form-group">-->
<!--          <label for="name">name</label>-->
<!--          <input type="text" class="form-control" id="name" placeholder="name" v-model="name">-->
<!--        </div>-->
<!--        <div class="form-group">-->
<!--          <label for="surname">surname</label>-->
<!--          <input type="text" class="form-control" id="surname" placeholder="surname" v-model="surname">-->
<!--        </div>-->
<!--        <div class="form-group">-->
<!--          <label for="gender">gender</label>-->
<!--          <input type="text" class="form-control" id="gender" placeholder="gender" v-model="gender">-->
<!--        </div>-->
<!--        <div class="form-group">-->
<!--          <label for="city">city</label>-->
<!--          <input type="text" class="form-control" id="city" placeholder="city" v-model="city">-->
<!--        </div>-->
<!--        <div class="form-group">-->
<!--          <label for="surname">surname</label>-->
<!--          <input type="text" class="form-control" id="age" placeholder="age" v-model="age">-->
<!--        </div>-->
<!--        <button type="button" class="btn btn-primary" @click="onRegisterClick()">Регитсрация</button>-->
<!--      </form>-->
<!--    </div>-->

  <div class="container">

    <div class="col-md-10 order-md-1">
      <h4 class="mb-3">Регистрация</h4>
      <hr class="mb-4">
      <form>
        <div class="row">
          <div class="col-md-6 mb-3">
            <label for="firstName">Имя</label>
            <input v-model="name" type="text" class="form-control" id="firstName" placeholder="" value="">
          </div>
          <div class="col-md-6 mb-3">
            <label for="lastName">Фамилия</label>
            <input v-model="surname" type="text" class="form-control" id="lastName" placeholder="" value="">
          </div>
        </div>

        <div class="mb-3">
          <label for="email">Email</label>
          <input v-model="login" type="text" class="form-control" id="email" placeholder="">
        </div>

        <div class="mb-3">
          <label for="pass">Пароль</label>
          <input v-model="password" type="password" class="form-control" id="pass" placeholder="">
        </div>

        <div class="mb-3">
          <label for="city">Город</label>
          <input v-model="city" type="text" class="form-control" id="city" placeholder="">
        </div>

        <div class="row">
          <div class="col-md-2 mb-3">
            <div class="mb-1">
              <label for="gender">Пол</label>
              <input v-model="gender" type="text" class="form-control" id="gender" placeholder="">
            </div>
          </div>
          <div class="col-md-2 mb-3">
            <div class="mb-3">
              <label for="age">Возраст</label>
              <input v-model="age" type="text" class="form-control" id="age" placeholder="">
            </div>
          </div>
        </div>
        <hr class="mb-4">
        <button type="button" class="btn btn-primary btn-lg btn-block" @click="onRegisterClick()">Регистрация</button>
      </form>
    </div>
  </div>
</template>

<script>
import store from "@/store/store";
import Router from "@/router";

export default {
  name: 'RegisterComponent',

  data() {
    return {
      login: "",
      password: "",
      name: "",
      surname: "",
      city: "",
      gender: "",
      age: 0,
    }
  },

  methods: {
    onRegisterClick() {
      const self = this
      let data = {
        login: self.login,
        password: self.password,
        name: self.name,
        surname: self.surname,
        city: self.city,
        gender: self.gender,
        age: self.age,
      }
      store.dispatch('ACCOUNT_REGISTER', data)
          .then(() => {
            console.log("register success  " + self.$store.getters.getJwtToken)
            store.commit('setLayout', "MainComponent")
            Router.push({path: '/'})
          }).finally(() => {
      })
    },

  }
};
</script>

<style>
.modal {
  display: flex;
  align-items: center;
  justify-content: center;
}

/*.container {*/
/*  max-width: 960px;*/
/*}*/

/*.border-top {*/
/*  border-top: 1px solid #e5e5e5;*/
/*}*/

/*.border-bottom {*/
/*  border-bottom: 1px solid #e5e5e5;*/
/*}*/

/*.border-top-gray {*/
/*  border-top-color: #adb5bd;*/
/*}*/

/*.box-shadow {*/
/*  box-shadow: 0 .25rem .75rem rgba(0, 0, 0, .05);*/
/*}*/

/*.lh-condensed {*/
/*  line-height: 1.25;*/
/*}*/

</style>
