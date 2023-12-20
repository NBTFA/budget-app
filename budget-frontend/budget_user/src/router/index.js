import VueRouter from "vue-router";
import Vue from "vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import Home from "../views/Home.vue";
import Todo from "../views/Todo.vue";
import History from "../views/History.vue";
import Rank from "../views/Rank.vue";
import Report from "../views/Report.vue";
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
        },
        {
            path: '/todo',
            component: Todo
        },
        {
            path: '/history',
            component: History
        },
        {
            path: '/rank',
            component: Rank
        },
        {
            path: '/report',
            component: Report
        }

    ]
});

export default routes;