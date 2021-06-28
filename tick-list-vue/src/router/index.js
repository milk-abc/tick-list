import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      redirect:{ name: "Dashboard" }
    },
    {
      path: "/login",
      name: "Login",
      component: () => import("../views/login/index.vue")
    },
    {
      path: "/dashboard",
      name: "Dashboard",
      component: () => import("../views/dashboard/index.vue")
    },
  ]
})
