import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/Login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },
  {
    path: '/index',
    name:'Index',
    component: ()=> import('../views/Index.vue'),
    children:[
      {
        path:'CourseSelection',
        name:'CourseSelection',
        component: () => import('../components/CourseSelection')
      },
      {
        path:'MyClasses',
        name:'MyClasses',
        component: ()=> import('../components/MyClasses')
      }
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
