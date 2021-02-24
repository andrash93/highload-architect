<template>
  <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
    <div
        class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
      <h1 class="h2">Друзья</h1>
    </div>
    <div>
      <ul class="nav nav-tabs">
        <li class="nav-item">
          <router-link to="/friends/all"><a class="nav-link" v-bind:class="{ active: isAllActive }" href="#">
            Все
          </a></router-link>
        </li>
        <li class="nav-item">
          <router-link to="/friends/incoming"><a class="nav-link" v-bind:class="{ active: isIncomingActive }" href="#">
            Входящие
          </a></router-link>
        </li>
        <li class="nav-item">
          <router-link to="/friends/outgoing"><a class="nav-link" v-bind:class="{ active: isOutgoingActive }" href="#">
            Исходящие
          </a></router-link>
        </li>
      </ul>
    </div>

    <friend-row
        v-for="(item, index) in userList"
        v-bind:item="item"
        v-bind:index="index"
        v-bind:key="item.id"
        v-bind:account-id="item.id"
        v-bind:name="item.name"
        v-bind:surname="item.surname"
        @onViewClick="onViewAccountInfo($event)"
        @onDeleteClick="onDeleteFriend($event)"
        @onCancelClick="onCancelFriend($event)"
        @onConfirmClick="onConfirmFriend($event)"
        @onDeclineClick="onDeclineFriend($event)"
    ></friend-row>

  </main>
</template>

<script>
import FriendRow from "@/view/friend/FriendRow";
import store from "@/store/store";
import Router from "@/router";

export default {
  name: 'Friends',
  data() {
    return {
      userList: [],
      isAllActive: false,
      isIncomingActive: false,
      isOutgoingActive: false,
      route: "",
    }
  },
  methods: {
    onViewAccountInfo(event) {
      console.log("onViewAccountInfo " + event)
      if (this.$store.getters.getAuthAccountId !== event) {
        Router.push({name: 'AccountInfo', params: {accountId: event}})
      } else {
        Router.push({path: '/'})
      }
    },

    onDeleteFriend(event) {
      console.log("onDeleteFriend " + event)
      let data = {
        friendId: event
      }
      store.dispatch('DELETE_FRIEND', data)
          .then((response) => {
            console.log("onDeleteFriendClick success")
            this.getAllFriends()
          }).finally(() => {
      })
    },

    onCancelFriend(event) {
      let data = {
        friendId: event
      }
      store.dispatch('CANCEL_FRIEND', data)
          .then((response) => {
            console.log("onCancelFriendClick success")
            this.getOutgoingFriends()
          }).finally(() => {
      })
    },

    onConfirmFriend(event) {
      let data = {
        friendId: event
      }
      store.dispatch('CONFIRM_FRIEND', data)
          .then((response) => {
            console.log("onConfirmFriendClick success")
            this.getIncomingFriends()
          }).finally(() => {
      })
    },

    onDeclineFriend(event) {
      let data = {
        friendId: event
      }
      store.dispatch('DECLINE_FRIEND', data)
          .then((response) => {
            console.log("onDeclineFriendClick success")
            this.getIncomingFriends()
          }).finally(() => {
      })
    },


    onRoute(route) {
      this.route = route;
      this.isAllActive = false;
      this.isIncomingActive = false;
      this.isOutgoingActive = false;
      if (route === "all") {
        this.isAllActive = true;
        this.getAllFriends();
      } else if (route === "incoming") {
        this.isIncomingActive = true;
        this.getIncomingFriends()
      } else if (route === "outgoing") {
        this.isOutgoingActive = true;
        this.getOutgoingFriends()
      }
    },

    getAllFriends() {
      store.dispatch('GET_FRIENDS', localStorage.jwtToken)
          .then(() => {
            console.log("GET_FRIENDS success ")
            this.userList = this.$store.getters.getFriends
          }).finally(() => {
      })
    },

    getIncomingFriends() {
      store.dispatch('GET_INCOMING_FRIEND_REQUESTS', localStorage.jwtToken)
          .then(() => {
            console.log("GET_INCOMING_FRIEND_REQUESTS success ")
            this.userList = this.$store.getters.getFriends
          }).finally(() => {
      })
    },

    getOutgoingFriends() {
      store.dispatch('GET_OUTGOING_FRIEND_REQUESTS', localStorage.jwtToken)
          .then(() => {
            console.log("GET_OUTGOING_FRIEND_REQUESTS success ")
            this.userList = this.$store.getters.getFriends
          }).finally(() => {
      })
    },
  },
  created() {
    console.log("Friends component created " + this.$route.params.type)
    this.onRoute(this.$route.params.type)
  },

  watch: {
    $route(to, from) {
      console.log("watch router " + to.params.type)
      this.onRoute(to.params.type)
    },
  },

  components: {
    FriendRow
  },

}
</script>
