import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import ElementUI from "element-ui";

Vue.use(Vuex);
Vue.prototype.$http = axios;
Vue.use(ElementUI);

const store = new Vuex.Store({
  state: {
    taken: '',
    budgets: [],
    isLoadingBudgets: false,
  },
  mutations: {
    SET_LOADING_STATE(state, isLoading) {
      state.isLoadingBudgets = isLoading;
    },
    setToken(state, token) {
      state.taken = token;
    },
    setBudgets(state, budgets) {
      state.budgets = budgets;
    },
    ADD_BUDGET(state, budget) {
      state.budgets.push(budget);
    },
    DELETE_BUDGET(state, budget) {
      state.budgets.splice(state.budgets.indexOf(budget), 1);
    },
    SET_BUDGETS(state, budgets) {
      state.budgets = budgets;
    }
  },
  actions: {
    setBudgets({ commit }, budgets) {
      commit('SET_BUDGETS', budgets);
    },
    addBudget({ commit }, budget) {
      commit('ADD_BUDGET', budget);
    },
    deleteBudget({ commit }, budget) {
      commit('DELETE_BUDGET', budget);
      this.$http.delete("/user/budget", {
        params: {
          id: this.deleteRow.id,
        },
      }).then((res) => {
        console.log("删除预算：", res);
        if (res.data.status === 20000) {
          this.$message.success("删除成功");
        } else {
          this.$message.error(res.data.message);
        }
      });
    },
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