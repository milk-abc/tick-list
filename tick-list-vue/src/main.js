// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from "vue";
import App from "./App";
import store from "./store";
import router from "./router";
import axios from "axios";
import "./axios.js"; // 请求拦截
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import "normalize.css/normalize.css"; // A modern alternative to CSS resets
import "@/styles/index.scss"; // global css
import "@/icons"; // icon
import global_ from "@/views/global/index";
Vue.prototype.global = global_;
Vue.use(ElementUI);

Vue.config.productionTip = false;
Vue.prototype.$axios = axios; //
import VCharts from "v-charts";
Vue.use(VCharts);
/* eslint-disable no-new */
new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
