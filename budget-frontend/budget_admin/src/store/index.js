import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import ElementUI from "element-ui";
import routes from "@/routers";

Vue.use(Vuex);
Vue.prototype.$http = axios;
Vue.use(ElementUI);

const store = new Vuex.Store({
  state: {
    taken: '',
  },
  mutations: {
    setToken(state, token) {
      state.taken = token;
    },
    deleteToken(state) {
      state.taken = '';
    },
  },
  actions: {
    loadToken({ commit }) {
      const token = localStorage.getItem('token');
      if (token) {
        commit('setToken', token);
        console.log('token is ', token);
      }
      else {
        console.log('no token');
        commit('setToken', '');
        routes.push('/login');
      }
    },
  },
})
export default store;