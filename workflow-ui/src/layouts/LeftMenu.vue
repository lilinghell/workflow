<template>
  <q-list>
    <template v-for="(menuItem, index) in menuList">
      <q-item :key="index" v-if="!menuItem.children && menuItem.meta.show === true" clickable v-ripple :to="'/home/'+menuItem.path">
        <q-item-section avatar color="primary">
          <q-icon color="primary" :name="menuItem.meta.icon" />
        </q-item-section>
        <q-item-section>{{ menuItem.meta.label }}</q-item-section>
      </q-item>
      <q-expansion-item :key="index" group="appMenu" v-else-if="menuItem.children && menuItem.meta.show === true">
        <template v-slot:header>
          <q-item-section avatar>
            <q-icon color="primary" :name="menuItem.meta.icon" />
          </q-item-section>
          <q-item-section>
            {{ menuItem.meta.label }}
          </q-item-section>
        </template>
        <template v-for="(subMenuItem, subIndex) in menuItem.children">
          <q-item :key="'sub'+subIndex" v-show="subMenuItem.meta.show === true" clickable v-ripple style="padding-left:50px;" :to="'/home/'+menuItem.path+'/'+subMenuItem.path">
            <q-item-section avatar>
              <q-icon color="primary" :name="subMenuItem.meta.icon" />
            </q-item-section>
            <q-item-section>{{ subMenuItem.meta.label }}</q-item-section>
          </q-item>
        </template>
      </q-expansion-item>
    </template>
  </q-list>
</template>

<script>
export default {
  name: 'LeftMenu',
  data() {
    return {
      menuList: [],
      menuList_1: [
        {
          path: 'template',
          name: 'template',
          meta: {
            icon: 'dashboard',
            label: '模版管理',
            show: true,
          },
        },
        {
          path: 'todo',
          name: 'todo',
          meta: {
            icon: 'person',
            label: '我的代办',
            show: true,
          },
        },
        {
          path: 'example',
          name: 'example',
          meta: {
            icon: 'work',
            label: '制单示例',
            show: true,
          },
        },
      ],
      menuList_2: [
        {
          path: 'logs',
          name: 'logs',
          meta: {
            icon: 'inbox',
            label: '操作日志',
            show: true,
          },
        },
      ],
    }
  },
  methods: {
    updateLeftMenu(headerMenu) {
      if (headerMenu === 'menu_1') {
        this.menuList = this.menuList_1
      }
      if (headerMenu === 'menu_2') {
        this.menuList = this.menuList_2
      }
    },
  },
  created() {
    this.menuList = this.menuList_1
    this.$root.$on('_updateLeftMenu', this.updateLeftMenu)
  },
}
</script>
