<template>
  <div class="shadow-sm p-3 mb-5 bg-white rounded">
    <div class="row">
      <div class="col-md-2 mb-3">
        {{ name }} {{ surname }}
      </div>
      <div class="col-md-1 mb-3">
        <button type="button" class="btn btn-primary" @click="onViewClick()">{{ detailButtonCaption }}</button>
      </div>
      <div class="col-md-1 mb-3" v-if="deleteButtonVisible">
        <button type="button" class="btn btn-primary" @click="onDeleteClick()">{{ deleteButtonCaption }}</button>
      </div>
      <div class="col-md-1 mb-3" v-if="cancelButtonVisible">
        <button type="button" class="btn btn-primary" @click="onCancelClick()">{{ cancelButtonCaption }}</button>
      </div>
      <div class="col-md-1 mb-3" v-if="confirmButtonVisible">
        <button type="button" class="btn btn-primary" @click="onConfirmClick()">{{ confirmButtonCaption }}</button>
      </div>
      <div class="col-md-1 mb-3" v-if="declineButtonVisible">
        <button type="button" class="btn btn-primary" @click="onDeclineClick()">{{ declineButtonCaption }}</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FriendRow',
  props: ['accountId', 'name', 'surname'],
  data() {
    return {
      detailButtonCaption: "Подробнее",

      deleteButtonCaption: "Удалить",
      deleteButtonVisible: false,

      cancelButtonCaption: "Отменить заявку",
      cancelButtonVisible: false,

      confirmButtonCaption: "Подтвердить",
      confirmButtonVisible: false,

      declineButtonCaption: "Отказать",
      declineButtonVisible: false,

      route: "",
    }
  },
  methods: {
    onViewClick(event) {
      this.$emit('onViewClick', this.accountId)
    },
    onDeleteClick() {
      this.$emit('onDeleteClick', this.accountId)
    },
    onCancelClick() {
      this.$emit('onCancelClick', this.accountId)
    },
    onConfirmClick() {
      this.$emit('onConfirmClick', this.accountId)
    },
    onDeclineClick() {
      this.$emit('onDeclineClick', this.accountId)
    },

    onRoute(route) {
      this.route = route;
      this.deleteButtonVisible = false;
      this.cancelButtonVisible = false;
      this.confirmButtonVisible = false;
      this.declineButtonVisible = false;
      if (route === "all") {
        this.deleteButtonVisible = true;
      } else if (route === "outgoing") {
        this.cancelButtonVisible = true;
      } else if (route === "incoming") {
        this.confirmButtonVisible = true;
        this.declineButtonVisible = true;
      }
    }
  },
  created() {
    console.log("FriendRow component created " + this.$route.params.type)
    this.onRoute(this.$route.params.type)
  },
  watch: {
    $route(to, from) {
      console.log("FriendRow watch router " + to.params.type)
      this.onRoute(to.params.type)
    },
  },
};
</script>
