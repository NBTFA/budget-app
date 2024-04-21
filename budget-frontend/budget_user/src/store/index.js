import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import ElementUI from "element-ui";
import routes from "@/router";

Vue.use(Vuex);
Vue.prototype.$http = axios;
Vue.use(ElementUI);

const store = new Vuex.Store({
  state: {
    taken: '',
    budgets: [],
    isLoadingBudgets: false,
    avatar: '',
    notificationNum: 0,
    notifications: [],
    continueNum: 0,
    rankUsers: [],
    todoLists: [],
    isLoadingTodoLists: false,
    totalBudget: 0,
    pieChartData: [],
    progress: 0,
    userName: '',
  },
  mutations: {
    SET_USER_NAME(state, userName) {
      state.userName = userName;
    },
    CLEAR_NOTIFICATION_NUM(state) {
      state.notificationNum = 0;
    },
    SET_LOADING_STATE(state, isLoading) {
      state.isLoadingBudgets = isLoading;
    },
    setToken(state, token) {
      state.taken = token;
    },
    deleteToken(state) {
      state.taken = '';
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
    },
    SET_AVATAR(state, avatar) {
      state.avatar = avatar;
    },
    SET_NOTIFICATION_NUM(state, notificationNum) {
      state.notificationNum = notificationNum;
    },
    SET_NOTIFICATIONS(state, notifications) {
      state.notifications = notifications;
    },
    SET_CONTINUE_NUM(state, continueNum) {
      state.continueNum = continueNum;
    },
    SET_RANK_USERS(state, rankUsers) {
      state.rankUsers = rankUsers;
    },
    SET_TODO_LISTS(state, todoLists) {
      state.todoLists = todoLists;
    },
    SET_LOADING_STATE(state, isLoading) {
      state.isLoadingTodoLists = isLoading;
    },
    SET_TOTAL_BUDGET(state, totalBudget) {
      state.totalBudget = totalBudget;
    },
    SET_PIE_CHART_DATA(state, pieChartData) {
      state.pieChartData = pieChartData;
    },
    SET_BUDGETS(state, budgets) {
      state.budgets = budgets;
    },
    SET_PROGRESS(state, progress) {
      state.progress = progress;
    },
  },
  actions: {
    setUserName({ commit }, userName) {
      commit('SET_USER_NAME', userName);
    },
    clearNotificationNum({ commit }) {
      commit('CLEAR_NOTIFICATION_NUM');
    },
    deleteToken({ commit }) {
      commit('deleteToken');
    },
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
        routes.push('/login');
      }
    },
    setAvatar({ commit }, avatar) {
      commit('SET_AVATAR', avatar);
    },
    setNotificationNum({ commit }, notificationNum) {
      commit('SET_NOTIFICATION_NUM', notificationNum);
    },
    setNotifications({ commit }, notifications) {
      commit('SET_NOTIFICATIONS', notifications);
    },
    setContinueNum({ commit }, continueNum) {
      commit('SET_CONTINUE_NUM', continueNum);
    },
    setRankUsers({ commit }, rankUsers) {
      commit('SET_RANK_USERS', rankUsers);
    },
    setTodoLists({ commit }, todoLists) {
      commit('SET_TODO_LISTS', todoLists);
    },
    setTotalBudget({ commit }, totalBudget) {
      commit('SET_TOTAL_BUDGET', totalBudget);
    },
    setPieChartData({ commit }, pieChartData) {
      commit('SET_PIE_CHART_DATA', pieChartData);
    },
    setBudgets({ commit }, budgets) {
      commit('SET_BUDGETS', budgets);
    },
    setProgress({ commit }, progress) {
      commit('SET_PROGRESS', progress);
    },
  },
})
export default store;