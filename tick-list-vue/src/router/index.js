import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      redirect: "Login"
    },
    {
      path: "/login",
      name: "Login",
      component: () => import("@/views/login/index.vue")
    },
    {
      path: "/layout",
      name: "Layout",
      component: () => import("@/views/layout/index.vue"),
      redirect: {
        name: "Dashboard"
      },
      children: [
        {
          path: "/dashboard",
          name: "Dashboard",
          component: () => import("@/views/dashboard/index.vue"),
          meta: { title: "Dashboard", icon: "dashboard" }
        }
      ]
    }
  ]
});
