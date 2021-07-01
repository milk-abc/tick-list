import Vue from "vue";
import Vuex from "vuex";
import getters from "./getters";
import app from "./modules/app";
import settings from "./modules/settings";
Vue.use(Vuex);
const store = new Vuex.Store({
  modules: {
    app,
    settings
  },
  state: {
    token: localStorage.getItem("token"),
    userInfo: JSON.parse(sessionStorage.getItem("userInfo"))
  },
  mutations: {
    //set
    SET_TOKEN: (state, token) => {
      state.token = token;
      localStorage.setItem("token", token);
    },
    SET_USERINFO: (state, userInfo) => {
      state.userInfo = userInfo;
      sessionStorage.setItem("userInfo", JSON.stringify(userInfo));
    },
    REMOVE_INFO: state => {
      state.token = "";
      state.userInfo = {};
      localStorage.setItem("token", "");
      sessionStorage.setItem("userInfo", JSON.stringify(""));
    }
  },
  getters,
  actions: {}
});
export default store;
