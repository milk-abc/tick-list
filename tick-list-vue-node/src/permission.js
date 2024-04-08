import router from "./router";

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requireAuth)) {
    const token = localStorage.getItem("token");
    if (token) {
      if (to.path === "/login") {
      } else {
        next();
      }
    } else {
      next({
        path: "/login"
      });
    }
  } else {
    next();
  }
});
