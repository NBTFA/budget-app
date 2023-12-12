import VueRouter from "vue-router";
import Vue from "vue";
// import 组件 from 路径 ../components/组件.vue

Vue.use(VueRouter);

const routes = new VueRouter({
    routes: [
        // 重定向
        {
            path: '/',
            redirect: '/login',
            //component: //组件
        },
    ]
});

export default routes;