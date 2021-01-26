import Vue from 'vue';
import Router from 'vue-router';

import Users from './view/Accounts.vue';
import Friends from './view/Friends.vue';
import AccountInfo from './view/AccountInfo.vue';
import Login from './components/LoginComponent.vue';
import Register from './components/RegisterComponent.vue';

Vue.use(Router);

export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/',
            name: 'MyAccountInfo',
            component: AccountInfo,
        },
        {
            path: '/account/:accountId',
            name: 'AccountInfo',
            component: AccountInfo,
            props: true
        },
        {
            path: '/users',
            name: 'Users',
            component: Users,
        },
        {
            path: '/friends/:type',
            name: 'Friends',
            component: Friends,
            props: true
        },
        {
            path: '/login',
            name: 'Login',
            component: Login,
            meta: { layout: "empty" },
        },
        {
            path: '/register',
            name: 'Register',
            component: Register,
            meta: { layout: "empty" },
        },
    ],
});
