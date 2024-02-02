import VueRouter from "vue-router";
import Vue from "vue";
import Login from "../views/Login.vue";
import Register from "../views/Register.vue";
import Home from "../views/Home.vue";
import User from "../views/User.vue";
import Admin from "../views/Admin.vue";
import Budget from "../views/Budget.vue";
import Category from "../views/Category.vue";
import Continuous from "../views/Continuous.vue";
import Notification from "../views/Notification.vue";
import Todo from "../views/Todo.vue";
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
            component: Home,
        },
        {
            path: '/user',
            component: User,
        },
        {
            path: '/admin',
            component: Admin,
        },
        {
            path: '/budget',
            component: Budget,
        },
        {
            path: '/category',
            component: Category,
        },
        {
            path: '/continuous',
            component: Continuous,
        },
        {
            path: '/notification',
            component: Notification,
        },
        {
            path: '/todo',
            component: Todo,
        },

    ]
});

export default routes;