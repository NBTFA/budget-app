import VueRouter from "vue-router";
import Vue from "vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import Home from "../views/Home.vue";
// import 组件 from 路径 ../components/组件.vue

Vue.use(VueRouter);

const routes = new VueRouter({
    routes: [
        // 重定向
        {
            path: '/',
            redirect: '/login',
        },
        {
            path: '/login',
            component: Login
        },
        {
            path: '/register',
            component: Register
        },
        {
            path: '/home',
            component: Home
        }
    ]
});

export default routes;