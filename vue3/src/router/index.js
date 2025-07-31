import {createRouter, createWebHistory} from "vue-router";

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {path: '/', redirect: '/login'},
        {path: '/login', component: () => import('@/views/Login.vue')},
        {
            path: '/manager',
            name: 'Manager',
            component: () => import('@/views/Manager.vue'),
            redirect: '/manager/home',
            children: [
                {path: 'admin', meta: {name: 'AdminInfo'}, component: () => import('@/views/manager/Admin.vue')},
                { path: 'home', meta: { name: 'HomePage' }, component: () => import('@/views/manager/Home.vue')},

            ]
        }

    ]
})

export default router