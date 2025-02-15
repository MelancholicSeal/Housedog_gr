import { createRouter, createWebHistory } from 'vue-router';
// import HomeView from '../views/HomeView.vue';
import { useApplicationStore } from '@/stores/application.js';

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            name: 'home',
            // component: HomeView,
            component: () => import('../views/PropertiesView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/profile',
            name: 'profile',
            component: () => import('../views/ProfileView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/users/new',
            name: 'user-new',
            component: () => import('../views/CreateUserView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/users/:id',
            name: 'user',
            meta: { requiresAuth: true },
            children: [
                {
                    path: 'property',
                    name: 'user-properties',
                    component: () => import('../views/UserPropertiesView.vue'),
                    meta: { requiresAuth: true }
                },
                {
                    path: 'rent',
                    name: 'user-rents',
                    component: () => import('../views/UserRentsView.vue'),
                    meta: { requiresAuth: true }
                }
            ]
        },
        {
            path: '/property/:id',
            name: 'property',
            component: () => import('../views/PropertyDetailsView.vue'),
            meta: { requiresAuth: true },
            children: [
                {
                    path: '',
                    name: 'property-details',
                    component: () => import('../views/PropertyDetailsView.vue'),
                    meta: { requiresAuth: true }
                }
            ]
        },
        {
            path: '/property/new',
            name: 'property-new',
            component: () => import('../views/CreatePropertyView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/properties',
            name: 'properties',
            component: () => import('../views/PropertiesView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/login',
            name: 'login',
            component: () => import('../views/LoginView.vue')
        },
        {
            path: '/logout',
            name: 'logout',
            component: () => import('../views/LogoutView.vue'),
            meta: { requiresAuth: true }
        },
        {
            path: '/signup',
            name: 'signup',
            component: () => import('../views/SignUpView.vue'),
        },
        {
            path: '/requests',
            name: 'requests',
            component: () => import('../views/RequestsView.vue'),
        },
        {
            path: '/userList',
            name: 'userList',
            component: () => import('../views/AdminUsersView.vue')
        }
    ]
});

router.beforeEach((to, from, next) => {
    const { isAuthenticated } = useApplicationStore();
    const requiresAuth = to.matched.some((record) => record.meta.requiresAuth);

    if (requiresAuth && !isAuthenticated) {
        console.log('user not authenticated. redirecting to /login');
        next('/login');
    } else {
        next();
    }
});

export default router;
