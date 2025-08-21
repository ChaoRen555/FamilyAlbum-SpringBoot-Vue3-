import {createRouter, createWebHistory} from "vue-router";


const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {path: '/', redirect: '/login'},
        {path: '/login', component: () => import('@/views/Login.vue')},
        {path: '/register', component: () => import('@/views/Register.vue')},
        {
            path: '/manager',
            name: 'Manager',
            component: () => import('@/views/Manager.vue'),
            redirect: '/manager/home',
            children: [
                {path: 'admin', meta: {name: 'AdminInfo'}, component: () => import('@/views/manager/Admin.vue')},
                {path: 'home', meta: {name: 'HomePage'}, component: () => import('@/views/manager/Home.vue')},
                {path: 'picture', meta: {name: 'PictureInfo'}, component: () => import('@/views/manager/Picture.vue')},

            ]
        }

    ]
})


export default router