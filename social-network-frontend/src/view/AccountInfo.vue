<template>
  <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
    <div
        class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
      <h1 class="h2">{{ caption }}</h1>
    </div>
    <h4 class="mb-3">{{ name }} {{ surname }}</h4>
    Город: {{ city }}
    <br>
    Возраст: {{ age }}
    <br>
    Пол: {{ gender }}
    <br>
    <hr>
    <div v-if="!myAccount">
      <div class="mb-3" v-if="addButtonVisible">
        <button type="button" class="btn btn-primary" @click="onAddFriendClick()">{{ addButtonCaption }}</button>
      </div>
      <div class="mb-3" v-if="deleteButtonVisible">
        <button type="button" class="btn btn-primary" @click="onDeleteFriendClick()">{{ deleteButtonCaption }}</button>
      </div>
      <div class="mb-3" v-if="cancelButtonVisible">
        <button type="button" class="btn btn-primary" @click="onCancelFriendClick()">{{ cancelButtonCaption }}</button>
      </div>
      <div class="mb-3" v-if="declineButtonVisible">
        <button type="button" class="btn btn-primary" @click="onDeclineFriendClick()">{{ declineButtonCaption }}
        </button>
      </div>
      <div class="mb-3" v-if="confirmButtonVisible">
        <button type="button" class="btn btn-primary" @click="onConfirmFriendClick()">{{ confirmButtonCaption }}
        </button>
      </div>
    </div>
  </main>

</template>

<script>
import store from "@/store/store";

export default {
  name: 'AccountInfo',

  data() {
    return {
      addButtonCaption: "Добавить в друзья",
      addButtonVisible: false,
      cancelButtonCaption: "Отменить заявку",
      cancelButtonVisible: false,
      deleteButtonCaption: "Удалить из друзей",
      deleteButtonVisible: false,
      confirmButtonCaption: "Подтвердить",
      confirmButtonVisible: false,
      declineButtonCaption: "Отказать",
      declineButtonVisible: false,

      myAccount: true,

      caption: "",
      accountId: 0,
      login: "",
      name: "",
      surname: "",
      gender: "",
      city: "",
      age: 0
    }
  },
  methods: {

    updateAccountFields() {
      this.accountId = this.$store.getters.getAccounts.id
      this.login = this.$store.getters.getAccounts.login
      this.name = this.$store.getters.getAccounts.name
      this.surname = this.$store.getters.getAccounts.surname
      this.gender = this.$store.getters.getAccounts.gender
      this.city = this.$store.getters.getAccounts.city
      this.age = this.$store.getters.getAccounts.age
    },

    updateFriendButtonGroup(friendStatus) {
      this.hideAllButton();
      if (friendStatus === null || friendStatus.status === "NONE") {
        this.addButtonVisible = true
      }
      if (friendStatus.status === "REQUEST") {
        if (this.$store.getters.getAuthAccountId === friendStatus.senderPersonId) {
          this.cancelButtonVisible = true
        }
        if (this.$store.getters.getAuthAccountId === friendStatus.receiverPersonId) {
          this.confirmButtonVisible = true
          this.declineButtonVisible = true
        }
      }
      if (friendStatus.status === "FRIEND") {
        this.deleteButtonVisible = true
      }
    },

    hideAllButton() {
      this.addButtonVisible = false
      this.cancelButtonVisible = false
      this.deleteButtonVisible = false
      this.confirmButtonVisible = false
      this.declineButtonVisible = false
    },

    loadAccountById(id) {
      store.dispatch('GET_ACCOUNT', id)
          .then(() => {
            console.log("get account " + id + "success")
            this.caption = this.$store.getters.getAccounts.name + " " + this.$store.getters.getAccounts.surname
            this.myAccount = false;
            this.updateAccountFields();
            this.updateFriendButtonGroup(this.$store.getters.getAccounts.accountFriendStatus);
          }).finally(() => {
      })
    },

    loadCurrentAccountInfo() {
      store.dispatch('GET_CURRENT_ACCOUNT')
          .then(() => {
            console.log("get my account success")
            this.caption = "Моя страница"
            this.myAccount = true;
            this.updateAccountFields();
          }).finally(() => {
      })
    },

    onAddFriendClick() {
      let data = {
        friendId: this.accountId
      }
      store.dispatch('ADD_FRIEND', data)
          .then((response) => {
            console.log("onAddFiendClick success")
            this.updateFriendButtonGroup(response.response.data.accountFriend)
          }).finally(() => {
      })
    },

    onDeleteFriendClick() {
      let data = {
        friendId: this.accountId
      }
      store.dispatch('DELETE_FRIEND', data)
          .then((response) => {
            console.log("onDeleteFriendClick success")
            this.updateFriendButtonGroup(response.response.data.accountFriend)
          }).finally(() => {
      })
    },

    onCancelFriendClick() {
      let data = {
        friendId: this.accountId
      }
      store.dispatch('CANCEL_FRIEND', data)
          .then((response) => {
            console.log("onCancelFriendClick success")
            this.updateFriendButtonGroup(response.response.data.accountFriend)
          }).finally(() => {
      })
    },

    onConfirmFriendClick() {
      let data = {
        friendId: this.accountId
      }
      store.dispatch('CONFIRM_FRIEND', data)
          .then((response) => {
            console.log("onConfirmFriendClick success")
            this.updateFriendButtonGroup(response.response.data.accountFriend)
          }).finally(() => {
      })
    },

    onDeclineFriendClick() {
      let data = {
        friendId: this.accountId
      }
      store.dispatch('DECLINE_FRIEND', data)
          .then((response) => {
            console.log("onDeclineFriendClick success")
            this.updateFriendButtonGroup(response.response.data.accountFriend)
          }).finally(() => {
      })
    }
  },

  watch: {
    $route(to, from) {
      console.log("watch router " + to.params.accountId)
      console.log("watch router path " + to.path)
      if (to.path === "/") {
        console.log("try load my account info")
        this.loadCurrentAccountInfo();
      } else {
        console.log("try load account info by account id " + to.params.accountId)
        this.loadAccountById(to.params.accountId);
      }
    },
  },

  created() {
    console.log(" account info component created route path " + this.$route.path)
    console.log(" account info component created " + this.$route.params.accountId)
    if (this.$route.path === "/") {
      console.log("try load my account info")
      this.loadCurrentAccountInfo();
    } else {
      console.log("try load account info by account id " + this.$route.params.accountId)
      this.loadAccountById(this.$route.params.accountId);
    }
  }
};
</script>
