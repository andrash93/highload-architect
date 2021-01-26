<template>
  <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0" href="#">Социальная сеть</a>
    <!--      <input class="form-control form-control-dark w-100" type="text" placeholder="Search" aria-label="Search">-->
    <ul class="navbar-nav px-3">
      <li class="nav-item text-nowrap">
        <a class="nav-link" @click="onLogoutClick()">Выйти</a>
      </li>
    </ul>
  </nav>
</template>

<script>
import Router from "@/router";
import store from "@/store/store";

export default {
  name: 'Header',
  methods: {
    onLogoutClick() {
      console.log("onLogoutClick")
      localStorage.removeItem("jwtToken");
      store.commit('setLayout', "EmptyComponent")
      Router.push({ path: 'login' })
    },
  }
};
</script>
