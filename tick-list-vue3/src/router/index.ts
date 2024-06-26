import { createRouter, createWebHistory } from 'vue-router'
import { createAsyncComponent } from '@/utils/factory/createAsyncComponent'
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      redirect: 'Login'
    },
    {
      path: '/login',
      name: 'Login',
      component: createAsyncComponent(() => import('@/views/login/index.vue'))
      // hidden: true
    }
    // {
    //   path: '/register',
    //   name: 'Register',
    //   component: () => import('@/views/register/index.vue'),
    //   hidden: true
    // },
    // {
    //   path: '/forgetword',
    //   name: 'Forget',
    //   component: () => import('@/views/forgetword/index.vue'),
    //   hidden: true
    // },
    // {
    //   path: '/404',
    //   component: () => import('@/views/404'),
    //   hidden: true
    // },
    // {
    //   path: '/layout',
    //   name: 'Layout',
    //   component: Layout,
    //   redirect: {
    //     name: 'Dashboard'
    //   },
    //   children: [
    //     {
    //       path: '/dashboard',
    //       name: 'Dashboard',
    //       component: () => import('@/views/dashboard/index.vue'),
    //       meta: { title: '首页', icon: 'dashboard' }
    //     }
    //   ]
    // },
    // {
    //   path: '/add',
    //   component: Layout,
    //   redirect: '/addTask',
    //   children: [
    //     {
    //       path: 'addTask',
    //       name: 'AddTask',
    //       component: () => import('@/views/task/add'),
    //       meta: { title: '添加清单', icon: 'addTask' }
    //     }
    //   ]
    // },

    // {
    //   path: '/show',
    //   component: Layout,
    //   redirect: '/show/task',
    //   name: 'Show',
    //   meta: { title: '列表', icon: 'list' },
    //   children: [
    //     {
    //       path: 'task',
    //       name: 'Task',
    //       component: () => import('@/views/task/index'),
    //       meta: { title: '清单列表', icon: 'task' }
    //     },
    //     {
    //       path: 'category',
    //       name: 'Category',
    //       component: () => import('@/views/category/index'),
    //       meta: { title: '分类列表', icon: 'category' }
    //     },
    //     {
    //       path: 'label',
    //       name: 'Label',
    //       component: () => import('@/views/label/index'),
    //       meta: { title: '标签列表', icon: 'label' }
    //     }
    //   ]
    // },
    // {
    //   path: '/help',
    //   component: Layout,
    //   redirect: '/help/feedback',
    //   name: 'help',
    //   meta: { title: '帮助中心', icon: 'el-icon-s-help' },
    //   children: [
    //     {
    //       path: 'feedback',
    //       name: 'feedback',
    //       component: () => import('@/views/feedback/index'),
    //       meta: { title: '提交反馈', icon: 'feedback' }
    //     }
    //   ]
    // }
  ]
})

export default router
