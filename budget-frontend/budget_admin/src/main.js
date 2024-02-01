import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import axios from 'axios';
import routes from './routers/index.js'
import store from './store/index.js'

Vue.config.productionTip = false

Vue.use(ElementUI);

axios.defaults.baseURL = 'http://localhost:8088';
Vue.prototype.$http = axios;
axios.interceptors.request.use(config => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = token;
  }
  console.log('request', config);
  return config;
});

store.dispatch('loadToken');
console.log('store', store);

routes.beforeEach((to, from, next) => {
  if (to.path === '/login' || to.path === '/register') {
      next();
  } else {
      const token = localStorage.getItem('token');
      if (token) {
          next();
      } else {
          next('/login');
      }
  }
});

new Vue({
  render: h => h(App),
  router: routes,
  store
}).$mount('#app')
