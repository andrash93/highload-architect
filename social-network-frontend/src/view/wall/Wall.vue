<template>
  <main role="main" class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4">
    <div
        class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pb-2 mb-3 border-bottom">
      <h1 class="h2">Лента новостей</h1>
    </div>

    <form>
      <div class="row">
        <div class="col-md-4 mb-3">
          <label for="wallPostText">Текст</label>
          <input v-model="wallPostText" type="text" class="form-control" id="wallPostText" placeholder="" value="">
        </div>
        <div class="col-md-4 mb-3">
          <br>
          <button type="button" class="btn btn-primary btn-lg btn-block" @click="onWallPostPublishClick()">
            Опубликовать
          </button>
        </div>
      </div>
    </form>
    <hr>
    <wall-post
        v-for="(item, index) in wallPosts"
        v-bind:item="item"
        v-bind:index="index"
        v-bind:key="item.id"
        v-bind:fullUserName="item.fullUserName"
        v-bind:dateCreated="item.dateCreated"
        v-bind:text="item.text"
    ></wall-post>
  </main>
</template>

<script>
import store from "@/store/store";
import Router from "@/router";
import WallPost from "@/view/wall/WallPost";

export default {
  name: 'Wall',
  data() {
    return {
      wallPosts: [],
      wallPostText: ""
    }
  },
  methods: {
    onWallPostPublishClick() {
      const self = this
      let data = {
        text: self.wallPostText,
      }
      console.log("onWallPostPublishClick invoke ")
      store.dispatch('WALL_POST_PUBLISH', data)
          .then(() => {
            console.log("WALL_POST_PUBLISH success ")
          }).finally(() => {
      })
    }
  },

  created() {
    console.log("Wall component created ")
    store.dispatch('GET_WALL', localStorage.jwtToken)
        .then(() => {
          console.log("GET_WALL success ")
          this.wallPosts = this.$store.getters.getWallPosts
        }).finally(() => {
    })
  },
  components: {
    WallPost,
  },
};
</script>
