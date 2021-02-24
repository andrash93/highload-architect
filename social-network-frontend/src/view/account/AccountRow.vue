<template>
  <div class="shadow-sm p-3 mb-5 bg-white rounded">
    <div class="row">
      <div class="col-md-2 mb-3">
       {{ name }} {{ surname }}
      </div>
      <div class="col-md-1 mb-3">
        <button type="button" class="btn btn-primary" @click="onViewClick()">Подробнее</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'UserRow',
  props: ['accountId', 'name', 'surname'],
  methods: {
    onViewClick() {
      this.$emit('viewAccountInfo', this.accountId)
    },
  },
};
</script>
