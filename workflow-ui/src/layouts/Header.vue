<template>
  <div class="q-pa-md">
    <q-btn @click="updateLeftMenu('menu_1')" flat label="菜单一" icon="menu" title="菜单一" />
    <q-btn @click="updateLeftMenu('menu_2')" flat label="菜单二" icon="menu" title="菜单二" />
  </div>
</template>
<script>
export default {
  name: 'Header',
  setup() {
    return {}
  },
  data() {
    return {}
  },
  methods: {
    updateLeftMenu(headerMenu) {
      this.$root.$emit('_updateLeftMenu', headerMenu)
    },
  },
  created() {},
}
</script>