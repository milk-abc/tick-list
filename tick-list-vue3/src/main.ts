import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(router)

app.mount('#app')

app.config.errorHandler = (err) => {
  /* 处理错误 */
  console.log('error', err)
}
