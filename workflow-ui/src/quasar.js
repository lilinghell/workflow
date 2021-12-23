import Vue from 'vue'

import lang from 'quasar/lang/zh-hans.js';
import './styles/quasar.scss'
import 'quasar/dist/quasar.ie.polyfills'
import '@quasar/extras/material-icons/material-icons.css'
import '@quasar/extras/mdi-v3/mdi-v3.css'
import { Quasar, Notify, LoadingBar } from 'quasar'

Vue.use(Quasar, {

  lang: lang,
  plugins: {
    Notify,
    LoadingBar
  },
  config: {
    notify: {},
    loadingBar: {}
  }

})