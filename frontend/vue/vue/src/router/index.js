import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Layout from '../views/Layout.vue'
import Register from '../views/Register.vue'


Vue.use(VueRouter)

const routes = [
  {
    path:'/',
    name:'login',
    component: Login
  },
  {
    path:'/register',
    name:'register',
    component: Register
  },
  {
    path:'/Layout',
    name:'Layout',
    component: Layout,
    children:[
      {
        path: '/Admin',
        name: 'Admin',
        component: () => import( '../views/Admin.vue')
      },
      {
        path: '/data',
        name: 'data',
        component: () => import( '../views/data.vue')
      },
      {
        path: '/band',
        name: 'band',
        component: () => import( '../views/band.vue')
      },
      {
        path: '/song',
        name: 'song',
        component: () => import( '../views/song.vue')
      },
      {
        path: '/concert',
        name: 'concert',
        component: () => import( '../views/concert.vue')
      }, {
        path: '/member',
        name: 'member',
        component: () => import( '../views/member.vue')
      },
      {
        path: '/album',
        name: 'album',
        component: () => import( '../views/album.vue')
      },
      {
        path: '/joins',
        name: 'joins',
        component: () => import( '../views/joins.vue')
      },
      {
        path: '/performs',
        name: 'performs',
        component: () => import( '../views/performs.vue')
      },


    ]
  }
 
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})
router.beforeEach((to ,from, next) => {
  if (to.path ==='/') {
    next();
  }
  const user = localStorage.getItem("user");
  if (!user && to.path !== '/'&&to.path !=='/register'&&to.path !=='/forget') {
    return next("/");
  }
  next();
})

export default router
