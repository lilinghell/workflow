<template>
  <q-layout view="hHh lpR fFf" container style="height: 100%; min-height: 700px;">
    <q-header elevated class="bg-primary text-white">
      <Header />
    </q-header>

    <q-drawer :mini="miniState" v-model="drawer" show-if-above :width="250" :breakpoint="500" bordered>
      <q-scroll-area class="fit bg-grey-1">
        <LeftMenu />
      </q-scroll-area>
    </q-drawer>

    <q-page-container class="page-container">
      <q-page>
        <div class="flex-center">
          <router-view />
        </div>
      </q-page>
    </q-page-container>
  </q-layout>
</template>

<script>
import Header from './Header.vue'
import LeftMenu from './LeftMenu.vue'

export default {
  name: 'MainLayout',
  components: {
    Header,
    LeftMenu,
  },
  data() {
    return {
      drawer: false,
      miniState: false,
    }
  },
}
</script>