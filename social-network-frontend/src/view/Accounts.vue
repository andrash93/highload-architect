<template>
  <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
    <div
        class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
      <h1 class="h2">Пользователи</h1>
    </div>

    <form>
      <div class="row">
        <div class="col-md-4 mb-3">
          <label for="firstName">Имя</label>
          <input v-model="firstname" type="text" class="form-control" id="firstName" placeholder="" value="">
        </div>
        <div class="col-md-4 mb-3">
          <label for="lastName">Фамилия</label>
          <input v-model="surname" type="text" class="form-control" id="lastName" placeholder="" value="">
        </div>
        <div class="col-md-4 mb-3">
          <br>
          <button type="button" class="btn btn-primary btn-lg btn-block" @click="onSearchClick()">Поиск</button>
        </div>
      </div>
    </form>
    <hr>

    <account-row
        v-for="(item, index) in userList"
        v-bind:item="item"
        v-bind:index="index"
        v-bind:key="item.id"
        v-bind:account-id="item.id"
        v-bind:name="item.name"
        v-bind:surname="item.surname"
        @viewAccountInfo="onViewAccountInfo($event)"
    ></account-row>

  </main>
</template>

<script>
import AccountRow from "@/view/AccountRow";
import store from "@/store/store";
import Router from "@/router";

export default {
  name: 'Accounts',
  data() {
    return {
      userList: [],
      firstname: "",
      surname: "",
    }
  },
  methods: {
    onSearchClick() {
      const self = this
      let data = {
        firstname: self.firstname,
        surname: self.surname,
      }
      console.log("onSearchClick invoke ")
      store.dispatch('SEARCH_ACCOUNTS', data)
          .then(() => {
            console.log("SEARCH_ACCOUNTS success ")
            this.userList = this.$store.getters.getAccounts
          }).finally(() => {
      })
    },

    onViewAccountInfo(event) {
      console.log("onViewAccountInfo " + event)
      if (this.$store.getters.getAuthAccountId !== event) {
        Router.push({name: 'AccountInfo', params: {accountId: event}})
      } else {
        Router.push({path: '/'})
      }
    },
  },

  created() {
    console.log("Accounts component created ")
    store.dispatch('GET_ALL_ACCOUNTS', localStorage.jwtToken)
        .then(() => {
          console.log("GET_ALL_ACCOUNTS success ")
          this.userList = this.$store.getters.getAccounts
        }).finally(() => {
    })
  },
  components: {
    AccountRow
  },
};
</script>
