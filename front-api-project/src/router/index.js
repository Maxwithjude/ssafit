import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
// import BoardView from '@/views/BoardView.vue'

// import BoardList from '@/components/board/BoardList.vue'
// import BoardCreate from '@/components/board/BoardCreate.vue'
// import BoardDetail from '@/components/board/BoardDetail.vue'
// import BoardUpdate from '@/components/board/BoardUpdate.vue'

// import UserLogin from '@/components/user/UserLogin.vue'

// import YoutubeView from '@/views/YoutubeView.vue'
// import YoutubeLikesView from '@/views/YoutubeLikesView.vue'
// import SignUpView from '@/views/SignUpView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/board',
      name: 'board',
      component: () => import('@/views/BoardView.vue'),
      children: [
        {
          path: '',
          name: 'boardList',
          component: () => import('@/components/board/BoardList.vue'),
        },
        {
          path: 'create',
          name: 'boardCreate',
          component: () => import('@/components/board/BoardCreate.vue'),
        },
        {
          path: ':id',
          name: 'boardDetail',
          component: () => import('@/components/board/BoardDetail.vue'),
        },
        {
          path: 'update',
          name: 'boardUpdate',
          component: () => import('@/components/board/BoardUpdate.vue'),
        },
      ],
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/components/user/UserLogin.vue'),
    },
    {
      path: '/signup',
      name: 'Signup',
      component: () => import('@/views/SignUpView.vue'),
    },
    {
      path: '/youtube',
      name: 'youtube',
      component: () => import('@/views/YoutubeView.vue'),
    },
    {
      path: '/youtubeLikes',
      name: 'youtubeLikes',
      component: () => import('@/views/YoutubeLikesView.vue'),
    },
    // {
    //   path: "/chat",
    //   name: "chat",
    //   component: YoutubeView
    // }
  ],
})

export default router