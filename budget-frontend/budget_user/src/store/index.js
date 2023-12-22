import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    taken: ''
  },
  mutations: {
    setToken(state, token) {
      state.taken = token;
    }
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
        this.$router.push('/login');
      }
    }
  },
})
export default store;