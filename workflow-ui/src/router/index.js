import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: '/',
    component: () => import(/* webpackChunkName: "home" */ '../layouts/Layout.vue')
  },
  {
    path: '/home',
    name: 'Home',
    component: () => import(/* webpackChunkName: "home" */ '../layouts/Layout.vue'),
    children: [
      {
        path: 'template',
        name: 'template',
        meta: {
          icon: 'dashboard',
          label: '模版管理',
          show: true,
        },
        component: () => import(/* webpackChunkName: "common" */ '@/views/template/Template.vue'),
      },
      {
        path: 'todo',
        name: 'todo',
        meta: {
          icon: 'person',
          label: '我的代办',
          show: true,
        },
        component: () => import(/* webpackChunkName: "account" */ '@/views/todo/Todo.vue'),
      },
      {
        path: 'example',
        name: 'example',
        meta: {
          icon: 'work',
          label: '制单示例',
          show: true,
        },
        component: () => import(/* webpackChunkName: "example" */ '@/views/example/example.vue'),
      },
      {
        path: 'logs',
        name: 'logs',
        meta: {
          icon: 'inbox',
          label: '操作日志',
          show: true,
        },
        component: () => import(/* webpackChunkName: "common" */ '@/views/log/Logs.vue'),
      }
    ],
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})


export default router
