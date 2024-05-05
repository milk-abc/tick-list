import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import './axios.js' // 请求拦截
import ElementPlus from 'element-plus'
// import 'element-plus/theme-chalk/index.css'
import 'element-plus/dist/index.css'
import 'normalize.css/normalize.css' // A modern alternative to CSS resets
import '@/styles/index.scss' // global css
import VCharts from 'v-charts'
import Antd from 'ant-design-vue'
const app = createApp(App)

app.use(router)
app.use(Antd)
app.use(VCharts)
app.use(ElementPlus)

app.config.globalProperties.$axios = axios
app.mount('#app')

app.config.errorHandler = (err) => {
  /* 处理错误 */
  console.log('error', err)
}
