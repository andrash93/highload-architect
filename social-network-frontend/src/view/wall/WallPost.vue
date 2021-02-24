<template>
  <div class="shadow-sm p-3 mb-5 bg-white rounded">
    <div class="row">
      <div class="col-md-2 mb-3">
        {{ fullUserName }}
        <br>
        {{ text }}
        <br>
        {{ dateCreated }}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'WallPost',
  props: ['fullUserName', 'dateCreated', 'text'],
  methods: {},
};
</script>
