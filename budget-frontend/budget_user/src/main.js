import Vue from 'vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App.vue'
import 'font-awesome/css/font-awesome.min.css'
import axios from 'axios';

Vue.config.productionTip = false

Vue.use(ElementUI);

axios.defaults.baseURL = 'http://localhost:8088';
Vue.prototype.$http = axios;

new Vue({
  render: h => h(App),
}).$mount('#app')
